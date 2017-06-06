/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.matrixlab.gmds.dbmd.api;

import java.util.List;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

/**
 *
 * @author alexmylnikov
 */
public interface Diffs {
    
    List<DiffEntry> listDiffs(Repository repo);
    List<DiffEntry> listDiffs(Repository repo, ObjectId previousHead, ObjectId currentHead);
    
}
