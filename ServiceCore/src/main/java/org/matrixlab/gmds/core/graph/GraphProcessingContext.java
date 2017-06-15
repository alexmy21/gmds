/*
 * Copyright 2017 Alex Mylnikov

 * This program is a free software: you can redistribute it and/or modify
 * it under the terms of the Apache License, Version 2.0 (the "License");
 * 
 * You may obtain a copy of the Apache 2 License at

 *      http://www.apache.org/licenses/LICENSE-2.0

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache 2 License for more details.
 *
 */

package org.matrixlab.gmds.core.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.flink.api.java.tuple.Tuple2;
import org.matrixlab.gmds.core.Processor;

/**
 *
 * @author alexmy
 * @param <T>
 */
public abstract class GraphProcessingContext<T> {

    Processor processor;

    // In case of Db meta data Graph nodeResults map serves as a temporary storage for PK of the corresponding Table.
    // The initial can capacity be set to the Graph nodeWhite collection size. However this map will hold only PK for
    // "white", "grey" and "red" nodes. Black nodes can be removed from the map.
    // This map should be persisted in order to restore Subsetting process from the failure.
    // Keys in this map are nodeWhite's labels, values are lists of PK's for the each nodeWhite in the subsetting use case.
    //
    // In the case of a different type of graphs use approapriet data type to hold nodeWhite's result collection.
    //
    // In case of very large result sets nodeResults map can hold references to the external data store.
    //   
    Map<String, Object> env;
    Map<String, Node> nodeMap;
    Map<String, Tuple2<Integer, T>> nodeResults;
    Map<String, ConcurrentLinkedQueue<Node>> colorBuckets;
    Map<String, Set<Edge>> forwardRels;
    Map<String, Set<Edge>> backwardRels;

    private Graph graph;

    /**
     * Defines processing for the Start node or nodes. The Start node(s) should be provided by the graph object.
     * Eventually this execute method will call the next abstract method - processNode( . . .). At least this is a
     * recommended way of implementing these methods.
     *
     * @param processor
     * @param forward
     */
    public abstract void processStartNode(Processor processor, boolean forward);

    /**
     * This is the place where the real data processing happened. Processing is changing state of the node: - BACK_LOG,
     * if node needs some additional work to be completed; - COMPLETE, if node processing is done and complete.
     *
     * @param node
     * @param processor
     * @param forward
     */
    public abstract void processNode(Node node, Processor processor, boolean forward);

    /**
     * Provides initialization of a new and restoration of the interrupted processing
     */
    private void init() {
//        env = new HashMap<>();
        getGraph().getNodes().stream().forEach((node) -> {
            if (node.getMetadata().get(GraphVocabulary.COLOR).equalsIgnoreCase(GraphVocabulary.GREY)) {
                // We are moving all grey nodes to white to start process over. The "processNode(. . . )" method 
                // has to be an idempotent, so it should not create any duplicates in the target dataset, 
                // if we run grey node again.
                getColorBuckets().get(GraphVocabulary.WHITE).add(node);
            } else if (node.getMetadata().get(GraphVocabulary.COLOR).equalsIgnoreCase(GraphVocabulary.WHITE)) {
                getColorBuckets().get(GraphVocabulary.WHITE).add(node);
            } else if (node.getMetadata().get(GraphVocabulary.COLOR).equalsIgnoreCase(GraphVocabulary.RED)) {
                getColorBuckets().get(GraphVocabulary.RED).add(node);
            } else if (getGraph().getMetadata().get(GraphVocabulary.START_NODE) != null
                    && getGraph().getMetadata().get(GraphVocabulary.START_NODE).equalsIgnoreCase(node.getLabel())) {
                // Check if the node is a START_NODE. If we got here, it means that we are just started,
                // in this case run processStartNode method.
                // This method will extract sample data from provided data source (in Env map)
                // by applying a specified sampling strategy
                markNode(node, GraphVocabulary.GREY);
                processStartNode(processor, true);
                markNodeCompleteBlack(node);
            } else if (node.getMetadata().get(GraphVocabulary.COLOR).equalsIgnoreCase(GraphVocabulary.UNTOUCHED)) {
                getColorBuckets().get(GraphVocabulary.UNTOUCHED).add(node);
            }

        });
    }

    public void execute() throws InterruptedException {
        // Run over the nodes and do it until white, blue and red queues would be empty
        ConcurrentLinkedQueue<Node> whiteBucket = getColorBuckets().get(GraphVocabulary.WHITE);
        ConcurrentLinkedQueue<Node> blueBucket = getColorBuckets().get(GraphVocabulary.BLUE);
        ConcurrentLinkedQueue<Node> redBucket = getColorBuckets().get(GraphVocabulary.RED);

        while (!(whiteBucket.isEmpty() && redBucket.isEmpty() && blueBucket.isEmpty())) {
            // Iterate over white queue and process each nodeWhite that gets there 
            while (!whiteBucket.isEmpty()) {
                Node nodeWhite = (Node) whiteBucket.poll();
                if (nodeWhite != null) {
                    String nodeColor = nodeWhite.getMetadata().get(GraphVocabulary.COLOR);
                    // Check for the node color - it maybe changed while staying in the queue
                    if (GraphVocabulary.WHITE.equalsIgnoreCase(nodeColor)) {
                        // TODO: move markNode to the processNode body
//                        markNode(nodeWhite, GraphVocabulary.GREY);
                        processNode(nodeWhite, processor, true);
                        int status = getNodeResults().get(nodeWhite.getLabel()).getField(0);

                        if (status == GraphVocabulary.COMPLETE) {
                            markNodeCompleteBlack(nodeWhite);
                        } else {
                            markNode(nodeWhite, GraphVocabulary.RED);
                        }
                    }
                }
            }
            while (!blueBucket.isEmpty()) {
                Node nodeBlue = (Node) blueBucket.poll();
                if (nodeBlue != null) {
                    String nodeColor = nodeBlue.getMetadata().get(GraphVocabulary.COLOR);
                    // Check for the node color - it could be chaged while staying in the queue
                    if (GraphVocabulary.BLUE.equalsIgnoreCase(nodeColor)) {
                        // TODO: move markNode to the processNode body
//                        markNode(nodeBlue, GraphVocabulary.GREY);
                        processNode(nodeBlue, processor, false);
                        int status = getNodeResults().get(nodeBlue.getLabel()).getField(0);
                        if (status == GraphVocabulary.COMPLETE) {
                            markNodeCompleteBlack(nodeBlue);
                        } else {
                            markNode(nodeBlue, GraphVocabulary.RED);
                        }
                    }
                }
            }
            // When we'll finish with direct processing we have to check, 
            // if we have any unfinished work in "red" queue.
            // "Red" becomes a new "white" and we are running processNode one more time, 
            // but backward. 
            while (!redBucket.isEmpty()) {
                Node node = (Node) redBucket.poll();
                if (node != null) {
                    String nodeColor = node.getMetadata().get(GraphVocabulary.COLOR);
                    // Check for the node color - it maybe changed while staying in the queue
                    if (GraphVocabulary.RED.equalsIgnoreCase(nodeColor)) {
                        Set<Edge> inEdges = this.getBackwardRel().get(node.getLabel());
                        if (inEdges != null) {
                            Iterator<Edge> iterator = inEdges.iterator();
                            while (iterator.hasNext()) {
                                Edge edge = iterator.next();
                                String[] source = edge.getSource().split("\\.");
                                Node _node = getNodeMap().get(source[0]);
                                // At this point nodes can be only: BLACK (complete), 
                                // RED (unfinished) or UNTOUCHED. Mark all untouched nodes to BLUE
                                if (GraphVocabulary.UNTOUCHED
                                        .equalsIgnoreCase(_node.getMetadata().get(GraphVocabulary.COLOR))) {
                                    markNode(_node, GraphVocabulary.BLUE);
                                }
                            }
                        }
                        if (!(hasUntouchedInNodes(node) && hasUntouchedOutNodes(node))) {
                            markNodeCompleteBlack(node);
                        }
                    }
                }
            }
        }
        System.out.println("Processing Results: " + getNodeResults());
    }

    /**
     * Marks node to the specified color. Important. Node should reference to the nodeWhite instance in the graph.
     *
     * @param node
     * @param color
     */
    public void markNode(Node node, String color) {
        // 1. Move nodeWhite to the "color" bucket
        moveNode(node, color);
        // 2. Update nodeWhite color in the graph
        node.getMetadata().put(GraphVocabulary.COLOR, color);
    }

    public void markNodeCompleteBlack(Node node) {
        // 1. Mark all connected out-nodes to the "white", if they are not "black"        
        Set<Edge> set = getForwardRel().get(node.getLabel());
        if (set != null) {
            set.stream().forEach((edge) -> {
                String[] target = edge.getTarget().split("\\.");
                Node _node = getNodeMap().get(target[0]);
                if (GraphVocabulary.UNTOUCHED.equalsIgnoreCase(_node.getMetadata().get(GraphVocabulary.COLOR))) {
                    markNode(_node, GraphVocabulary.WHITE);
                }
            });
        }

        // 2. Update color of current nodeWhite. If there is no "untouched" among in-nodes - change to black,
        //    otherwise - change to red.
        if (hasUntouchedInNodes(node)) {
            moveNode(node, GraphVocabulary.RED);
            node.getMetadata().put(GraphVocabulary.COLOR, GraphVocabulary.RED);
        } else {
            moveNode(node, GraphVocabulary.BLACK);
            node.getMetadata().put(GraphVocabulary.COLOR, GraphVocabulary.BLACK);
        }
    }

    private void markNodeCompleteRed(Node node) {
        // 1. Mark all connected out-nodes to the "white"        
        Set<Edge> set = getForwardRel().get(node.getLabel());
        if (set != null) {
            set.stream().forEach((edge) -> {
                String[] target = edge.getTarget().split("\\.");
                Node _node = getNodeMap().get(target[0]);
                markNode(_node, GraphVocabulary.WHITE);
            });
        }

        // 2. Update color of current nodeWhite. If there is no "untouched" among in-nodes - change to black,
        //    otherwise - change to red.
        if (hasUntouchedInNodes(node)) {
            moveNode(node, GraphVocabulary.RED);
            node.getMetadata().put(GraphVocabulary.COLOR, GraphVocabulary.RED);
        } else {
            moveNode(node, GraphVocabulary.BLACK);
            node.getMetadata().put(GraphVocabulary.COLOR, GraphVocabulary.BLACK);
        }
    }

    private void moveNode(Node node, String color) {
        // 1. Remove nodeWhite from current bucket
        String curColor = node.getMetadata().get(GraphVocabulary.COLOR);
        getColorBuckets().get(curColor).remove(node);
        // 2. Add it to the new color bucket
        getColorBuckets().get(color).add(node);
    }

    public GraphProcessingContext(Graph graph, Processor processor) {
        this.processor = processor;
        this.graph = graph;
        init();
    }

    public GraphProcessingContext(String json, Processor processor) {
        this.processor = processor;
        this.graph = new Graph().fromJson(json);
        init();
    }

    public GraphProcessingContext(Graph graph, Processor processor, Map<String, Object> env) {
        this.env = new HashMap<>(env);
        this.processor = processor;
        this.graph = graph;
        init();
    }

    public GraphProcessingContext(String json, Processor processor, Map<String, Object> env) {
        this.env = new HashMap<>(env);
        this.processor = processor;
        this.graph = new Graph().fromJson(json);
        init();
    }

    public Map<String, ConcurrentLinkedQueue<Node>> getColorBuckets() {
        if (this.colorBuckets == null) {
            this.colorBuckets = new HashMap<>();
            colorBuckets.put(GraphVocabulary.UNTOUCHED, new ConcurrentLinkedQueue<>());
            colorBuckets.put(GraphVocabulary.WHITE, new ConcurrentLinkedQueue<>());
            colorBuckets.put(GraphVocabulary.BLUE, new ConcurrentLinkedQueue<>());
            colorBuckets.put(GraphVocabulary.GREY, new ConcurrentLinkedQueue<>());
            colorBuckets.put(GraphVocabulary.BLACK, new ConcurrentLinkedQueue<>());
            colorBuckets.put(GraphVocabulary.RED, new ConcurrentLinkedQueue<>());
        }
        return colorBuckets;
    }

    /**
     * @return the nodeMap
     */
    public Map<String, Node> getNodeMap() {
        if (nodeMap == null) {
            this.nodeMap = new HashMap<>();
            getGraph().getNodes().stream().forEach((node) -> {
                nodeMap.put(node.getLabel(), node);
            });
        }
        return nodeMap;
    }

    /**
     * @return the nodeResults
     */
    public Map<String, Tuple2<Integer, T>> getNodeResults() {
        return nodeResults == null ? nodeResults = new HashMap<>() : nodeResults;
    }

    /**
     * @param nodeResults the nodeResults to set
     */
    public void setNodeResults(ConcurrentHashMap<String, Tuple2<Integer, T>> nodeResults) {
        this.nodeResults = nodeResults;
    }

    /**
     * @return the env
     */
    public Map<String, Object> getEnv() {
        return env == null ? new HashMap<>() : env;
    }

    /**
     * @param env the env to set
     */
    public void setEnv(Map<String, Object> env) {
        this.env = env;
    }

    /**
     * @return the graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @param graph the graph to set
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Map<String, Set<Edge>> getForwardRel() {
        if (forwardRels == null) {
            List<Edge> edges = getGraph().getEdges();
            this.forwardRels = new HashMap<>();
            edges.stream().forEach((edge) -> {
                String[] source = edge.getSource().split("\\.");
                if (forwardRels.get(source[0]) == null) {
                    forwardRels.put(source[0], new HashSet<>());
                }
                forwardRels.get(source[0]).add(edge);
            });
        }
        return forwardRels;
    }

    public Map<String, Set<Edge>> getBackwardRel() {
        if (backwardRels == null) {
            List<Edge> edges = getGraph().getEdges();
            this.backwardRels = new HashMap<>();
            edges.stream().forEach((edge) -> {
                String[] source = edge.getTarget().split("\\.");
                if (backwardRels.get(source[0]) == null) {
                    backwardRels.put(source[0], new HashSet<>());
                }
                backwardRels.get(source[0]).add(edge);
            });
        }
        return backwardRels;
    }

    public boolean hasUntouchedInNodes(Node node) {
        String label = node.getLabel();
        Set<Edge> set = getBackwardRel().get(label);

        return checkUntouched(set);
    }

    public boolean hasUntouchedOutNodes(Node node) {
        String label = node.getLabel();
        Set<Edge> set = getForwardRel().get(label);

        return checkUntouched(set);
    }

    public boolean checkUntouched(Set<Edge> set) {
        boolean bool = false;
        if (set != null) {
            List<Edge> iterator = new ArrayList<>(set);
            for (Edge edge : iterator) {
                String[] source = edge.getSource().split("\\.");
                Node _node = getNodeMap().get(source[0]);
                if (GraphVocabulary.UNTOUCHED.equalsIgnoreCase(_node.getMetadata().get(GraphVocabulary.COLOR))) {
                    bool = true;
                    break;
                }
            }
        }
        return bool;
    }
}
