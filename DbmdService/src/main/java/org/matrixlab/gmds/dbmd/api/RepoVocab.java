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

package org.matrixlab.gmds.dbmd.api;

/**
 * List of terms is taken from 
 * 
 *  https://docs.oracle.com/javase/8/docs/api/java/sql/DatabaseMetaData.html
 * 
 * Naming convention: 
 *      1) "get"/"is" prefixes are removed from all getter function (methods) names;
 *      2) all other function's (method) names are taken without editing as is.
 * 
 * @author alexmy
 */
public interface RepoVocab {
    
    String REPO_NAME = "REPO_NAME";     // Repository name for the metadata of the given Database. It could be a URL or any
                                                                    // other attribute that uniquely identities DB.
    //==================================================================================================================
    // Following is the list of terms taken from Oracle Doc site
    //==================================================================================================================
    
    String allProceduresAreCallable = "allProceduresAreCallable";   //Retrieves whether the current user can call all the 
                                                                    // procedures returned by the method getProcedures.
    String allTablesAreSelectable = "allTablesAreSelectable";       // Retrieves whether the current user can use all the tables 
                                                                    // returned by the method getTables in a SELECT statement.
    String autoCommitFailureClosesAllResultSets = "autoCommitFailureClosesAllResultSets"; // Retrieves whether a SQLException while 
                                                                    // autoCommit is true inidcates that all open ResultSets are closed, 
                                                                    // even ones that are holdable.
    String dataDefinitionCausesTransactionCommit = "dataDefinitionCausesTransactionCommit"; // Retrieves whether a data definition 
                                                                    // statement within a transaction forces the transaction to commit.
    String dataDefinitionIgnoredInTransactions = "dataDefinitionIgnoredInTransactions"; // Retrieves whether this database ignores 
                                                                    // a data definition statement within a transaction.
    String deletesAreDetected = "deletesAreDetected"; // Retrieves whether or not a visible row delete can be detected by 
                                                                    // calling the method ResultSet.rowDeleted.
    String doesMaxRowSizeIncludeBlobs = "doesMaxRowSizeIncludeBlobs";   // Retrieves whether the return value for the method 
                                                                    // getMaxRowSize includes the SQL data types LONGVARCHAR and LONGVARBINARY.
    String generatedKeyAlwaysReturned = "generatedKeyAlwaysReturned"; //Retrieves whether a generated key will always be returned 
                                                                    // if the column name(s) or index(es) specified for the auto 
                                                                    // generated key column(s) are valid and the statement succeeds.
    String Attributes = "Attributes";   // Retrieves a description of the given attribute of the given type for a user-defined type (UDT) 
                                                                    // that is available in the given schema and catalog.
    String BestRowIdentifier = "BestRowIdentifier";      // Retrieves a description of a table's optimal set of columns 
                                                                    // that uniquely identifies a row.
    String Catalogs = "Catalogs";    // Retrieves the catalog names available in this database.
    String CatalogSeparator = "CatalogSeparator";   // Retrieves the String that this database uses as the separator between 
                                                                    // a catalog and table name.
    String CatalogTerm = "CatalogTerm"; // Retrieves the database vendor's preferred term for "catalog".
    String ClientInfoProperties = "ClientInfoProperties";   // Retrieves a list of the client info properties that the driver supports.
    String ColumnPrivileges = "ColumnPrivileges";   // Retrieves a description of the access rights for a table's columns.
    String Columns = "Columns";    // Retrieves a description of table columns available in the specified catalog.
    String Connection = "Connection";   // Retrieves the connection that produced this metadata object.
    String CrossReference = "CrossReference";   // Retrieves a description of the foreign key columns in the given foreign key 
                                                                    // table that reference the primary key or the columns 
                                                                    // representing a unique constraint of the parent table 
                                                                    // (could be the same or a different table).
    String DatabaseMajorVersion = "DatabaseMajorVersion";   // DatabaseMajorVersion Retrieves the major version number of the underlying database.
    String DatabaseMinorVersion = "DatabaseMinorVersion";   // Retrieves the minor version number of the underlying database.
    String DatabaseProductName = "DatabaseProductName";     // Retrieves the name of this database product.
    String DatabaseProductVersion = "DatabaseProductVersion"; // Retrieves the version number of this database product.
    String DefaultTransactionIsolation = "DefaultTransactionIsolation"; // Retrieves this database's default transaction isolation level.
    String DriverMajorVersion = "DriverMajorVersion";   // Retrieves this JDBC driver's major version number.
    String DriverMinorVersion = "DriverMinorVersion";   // Retrieves this JDBC driver's minor version number.
    String DriverName = "DriverName";   // Retrieves the name of this JDBC driver.
    String DriverVersion = "DriverVersion"; // Retrieves the version number of this JDBC driver as a String.
    String ExportedKeys = "ExportedKeys";   // Retrieves a description of the foreign key columns that reference the given table's 
                                                                    // primary key columns (the foreign keys exported by a table).
    String ExtraNameCharacters = "ExtraNameCharacters"; // Retrieves all the "extra" characters that can be used in unquoted identifier 
                                                                    // names (those beyond a-z, A-Z, 0-9 and _).
    String FunctionColumns = "FunctionColumns";     // FunctionColumns Retrieves a description of the given catalog's system 
                                                                    // or user function parameters and return type.
    String Functions = "Functions";         // Retrieves a description of the system and user functions available in the given catalog.
    String IdentifierQuoteString = "IdentifierQuoteString";     // Retrieves the string used to quote SQL identifiers.
    String ImportedKeys = "ImportedKeys";   // Retrieves a description of the primary key columns that are referenced by the given 
                                                                    // table's foreign key columns (the primary keys imported by a table).
    String IndexInfo = "IndexInfo";         // Retrieves a description of the given table's indices and statistics.
    String JDBCMajorVersion = "JDBCMajorVersion";   // Retrieves the major JDBC version number for this driver.
    String JDBCMinorVersion = "JDBCMinorVersion";   // Retrieves the minor JDBC version number for this driver.
    String MaxBinaryLiteralLength = "MaxBinaryLiteralLength";   // Retrieves the maximum number of hex characters this database 
                                                                    // allows in an inline binary literal.
    String MaxCatalogNameLength = "MaxCatalogNameLength";   // Retrieves the maximum number of characters that this database 
                                                                    // allows in a catalog name.
    String MaxCharLiteralLength = "MaxCharLiteralLength";   // Retrieves the maximum number of characters this database allows 
                                                                    // for a character literal.
    String MaxColumnNameLength = "MaxColumnNameLength"; // Retrieves the maximum number of characters this database allows for a column name.
    String MaxColumnsInGroupBy = "MaxColumnsInGroupBy"; // Retrieves the maximum number of columns this database allows in a GROUP BY clause.
    String MaxColumnsInIndex = "MaxColumnsInIndex";     // Retrieves the maximum number of columns this database allows in an index.
    String MaxColumnsInOrderBy = "MaxColumnsInOrderBy"; // Retrieves the maximum number of columns this database allows in an ORDER BY clause.
    String MaxColumnsInSelect = "MaxColumnsInSelect";   // Retrieves the maximum number of columns this database allows in a SELECT list.
    String MaxColumnsInTable = "MaxColumnsInTable";     // Retrieves the maximum number of columns this database allows in a table.
    String MaxConnections = "MaxConnections";   // Retrieves the maximum number of concurrent connections to this database that are possible.
    String MaxCursorNameLength = "MaxCursorNameLength";     // Retrieves the maximum number of characters that this database allows in a cursor name.
    String MaxIndexLength = "MaxIndexLength";   // Retrieves the maximum number of bytes this database allows for an index, 
                                                                    // including all of the parts of the index.
    String MaxProcedureNameLength = "MaxProcedureNameLength";   // Retrieves the maximum number of characters that this database allows in a procedure name.
    String MaxRowSize = "MaxRowSize";       // Retrieves the maximum number of bytes this database allows in a single row.
    String MaxSchemaNameLength = "MaxSchemaNameLength"; // Retrieves the maximum number of characters that this database allows in a schema name.
    String MaxStatementLength = "MaxStatementLength";   // Retrieves the maximum number of characters this database allows in an SQL statement.
    String MaxStatements = "MaxStatements"; // Retrieves the maximum number of active statements to this database that can be open at the same time.
    String MaxTableNameLength = "MaxTableNameLength";   // Retrieves the maximum number of characters this database allows in a table name.
    String MaxTablesInSelect = "MaxTablesInSelect";     // Retrieves the maximum number of tables this database allows in a SELECT statement.
    String MaxUserNameLength = "MaxUserNameLength";     // Retrieves the maximum number of characters this database allows in a user name.
    String NumericFunctions = "NumericFunctions";       // Retrieves a comma-separated list of math functions available with this database.
    String PrimaryKeys = "PrimaryKeys";     // Retrieves a description of the given table's primary key columns.
    String ProcedureColumns = "ProcedureColumns";       // Retrieves a description of the given catalog's stored procedure parameter 
                                                                    // and result columns.
    String Procedures = "Procedures";       // Retrieves a description of the stored procedures available in the given catalog.
    String ProcedureTerm = "ProcedureTerm"; // Retrieves the database vendor's preferred term for "procedure".
    String PseudoColumns = "PseudoColumns"; // Retrieves a description of the pseudo or hidden columns available in a given table 
                                                                    // within the specified catalog and schema.
    String ResultSetHoldability = "ResultSetHoldability";   // Retrieves this database's default holdability for ResultSet objects.
    String RowIdLifetime = "RowIdLifetime"; // Indicates whether or not this data source supports the SQL ROWID type, and if so the 
                                                                    // lifetime for which a RowId object remains valid.
    String Schemas = "Schemas"; // Retrieves the schema names available in this database.
//    String SchemasCatalogSchemaPattern = "SchemasCatalogSchemaPattern"; // Retrieves the schema names available in this database.
    String SchemaTerm = "SchemaTerm";   // Retrieves the database vendor's preferred term for "schema".
    String SearchStringEscape = "SearchStringEscape";   // Retrieves the string that can be used to escape wildcard characters.
    String SQLKeywords = "SQLKeywords"; // Retrieves a comma-separated list of all of this database's SQL keywords that are NOT 
                                                                    // also SQL:2003 keywords.
    String SQLStateType = "SQLStateType";   // Indicates whether the SQLSTATE returned by SQLException.getSQLState is 
                                                                    // X/Open (now known as Open Group) SQL CLI or SQL:2003.
    String StringFunctions = "StringFunctions"; // Retrieves a comma-separated list of string functions available with this database.
    String SuperTables = "SuperTables"; // Retrieves a description of the table hierarchies defined in a particular schema in this database.
    String SuperTypes = "SuperTypes";   // Retrieves a description of the user-defined type (UDT) hierarchies defined in a particular 
                                                                    // schema in this database.
    String SystemFunctions = "SystemFunctions"; // Retrieves a comma-separated list of system functions available with this database.
    String TablePrivileges = "TablePrivileges"; // Retrieves a description of the access rights for each table available in a catalog.
    String Tables = "Tables";           // Retrieves a description of the tables available in the given catalog.
    String TableTypes = "TableTypes";   // Retrieves the table types available in this database.
    String TimeDateFunctions = "TimeDateFunctions"; // Retrieves a comma-separated list of the time and date functions 
                                        // available with this database.
    String TypeInfo = "TypeInfo";       // Retrieves a description of all the data types supported by this database.
    String UDTs = "UDTs";       // Retrieves a description of the user-defined types (UDTs) defined in a particular schema.
    String URL = "URL";     // Retrieves the URL for this DBMS.
    String UserName = "UserName";       // Retrieves the user name as known to this database.
    String VersionColumns = "VersionColumns";   // Retrieves a description of a table's columns that are automatically updated 
                                                                    // when any value in a row is updated.
    String insertsAreDetected = "insertsAreDetected";   // Retrieves whether or not a visible row insert can be detected by 
                                                                    // calling the method ResultSet.rowInserted.
    String CatalogAtStart = "CatalogAtStart";   // Retrieves whether a catalog appears at the start of a fully qualified table name.
    String ReadOnly = "ReadOnly";   // Retrieves whether this database is in read-only mode.
    String locatorsUpdateCopy = "locatorsUpdateCopy";   // Indicates whether updates made to a LOB are made on a copy or directly to the LOB.
    String nullPlusNonNullIsNull = "nullPlusNonNullIsNull";     // Retrieves whether this database supports concatenations 
                                                                    // between NULL and non-NULL values being NULL.
    String nullsAreSortedAtEnd = "nullsAreSortedAtEnd"; // Retrieves whether NULL values are sorted at the end regardless of sort order.
    String nullsAreSortedAtStart = "nullsAreSortedAtStart";     // Retrieves whether NULL values are sorted at the start regardless of sort order.
    String nullsAreSortedHigh = "nullsAreSortedHigh";   // Retrieves whether NULL values are sorted high.
    String nullsAreSortedLow = "nullsAreSortedLow";     // Retrieves whether NULL values are sorted low.
    String othersDeletesAreVisible = "othersDeletesAreVisible"; // Retrieves whether deletes made by others are visible.
    String othersInsertsAreVisible = "othersInsertsAreVisible"; // Retrieves whether inserts made by others are visible.
    String othersUpdatesAreVisible = "othersUpdatesAreVisible"; // Retrieves whether updates made by others are visible.
    String ownDeletesAreVisible = "ownDeletesAreVisible";   // Retrieves whether a result set's own deletes are visible.
    String ownInsertsAreVisible = "ownInsertsAreVisible";   // Retrieves whether a result set's own inserts are visible.
    String ownUpdatesAreVisible = "ownUpdatesAreVisible";   // Retrieves whether for the given type of ResultSet object, 
                                                                    // the result set's own updates are visible.
    String storesLowerCaseIdentifiers = "storesLowerCaseIdentifiers";   // Retrieves whether this database treats mixed case unquoted 
                                                                    // SQL identifiers as case insensitive and stores them in lower case.
    String storesLowerCaseQuotedIdentifiers = "storesLowerCaseQuotedIdentifiers";   // Retrieves whether this database treats mixed case 
                                                                    // quoted SQL identifiers as case insensitive and stores them in lower case.
    String storesMixedCaseIdentifiers = "storesMixedCaseIdentifiers";   // Retrieves whether this database treats mixed case unquoted 
                                                                    // SQL identifiers as case insensitive and stores them in mixed case.
    String storesMixedCaseQuotedIdentifiers = "storesMixedCaseQuotedIdentifiers";   // Retrieves whether this database treats mixed case 
                                                                        // quoted SQL identifiers as case insensitive and stores them in mixed case.
    String storesUpperCaseIdentifiers = "storesUpperCaseIdentifiers";   // Retrieves whether this database treats mixed case unquoted 
                                                                    // SQL identifiers as case insensitive and stores them in upper case.
    String storesUpperCaseQuotedIdentifiers = "storesUpperCaseQuotedIdentifiers";   // Retrieves whether this database treats mixed case 
                                                                    // quoted SQL identifiers as case insensitive and stores them in upper case.
    String supportsAlterTableWithAddColumn = "supportsAlterTableWithAddColumn"; // Retrieves whether this database supports ALTER TABLE 
                                                                    // with add column.
    String supportsAlterTableWithDropColumn = "supportsAlterTableWithDropColumn";   // Retrieves whether this database supports ALTER TABLE 
                                                                    // with drop column.
    String supportsANSI92EntryLevelSQL = "supportsANSI92EntryLevelSQL"; // Retrieves whether this database supports the ANSI92 entry 
                                                                    // level SQL grammar.
    String supportsANSI92FullSQL = "supportsANSI92FullSQL"; // Retrieves whether this database supports the ANSI92 full SQL grammar supported.
    String supportsANSI92IntermediateSQL = "supportsANSI92IntermediateSQL";     // Retrieves whether this database supports the ANSI92 
                                                                    // intermediate SQL grammar supported.
    String supportsBatchUpdates = "supportsBatchUpdates";   // Retrieves whether this database supports batch updates.
    String supportsCatalogsInDataManipulation = "supportsCatalogsInDataManipulation";   // Retrieves whether a catalog name can be used 
                                                                    // in a data manipulation statement.
    String supportsCatalogsInIndexDefinitions = "supportsCatalogsInIndexDefinitions";   // Retrieves whether a catalog name can be used 
                                                                    // in an index definition statement.
    String supportsCatalogsInPrivilegeDefinitions = "supportsCatalogsInPrivilegeDefinitions";   // Retrieves whether a catalog name can be 
                                                                    // used in a privilege definition statement.
    String supportsCatalogsInProcedureCalls = "supportsCatalogsInProcedureCalls";   // Retrieves whether a catalog name can be used in a 
                                                                    // procedure call statement.
    String supportsCatalogsInTableDefinitions = "supportsCatalogsInTableDefinitions";   // Retrieves whether a catalog name can be used 
                                                                    // in a table definition statement.
    String supportsColumnAliasing = "supportsColumnAliasing";   // Retrieves whether this database supports column aliasing.
    String supportsConvert = "supportsConvert"; // Retrieves whether this database supports the JDBC scalar function CONVERT for the 
                                                                    // conversion of one JDBC type to another.
    String supportsConvertFromToType = "supportsConvert";   // Retrieves whether this database supports the JDBC scalar function CONVERT 
                                                                    //for conversions between the JDBC types fromType and toType.
    String supportsCoreSQLGrammar = "supportsCoreSQLGrammar";   // Retrieves whether this database supports the ODBC Core SQL grammar.
    String supportsCorrelatedSubqueries = "supportsCorrelatedSubqueries";   //Retrieves whether this database supports correlated subqueries.
    String supportsDataDefinitionAndDataManipulationTransactions = "supportsDataDefinitionAndDataManipulationTransactions"; // Retrieves whether 
                                                                    // this database supports both data definition and data manipulation 
                                                                    // statements within a transaction.
    String supportsDataManipulationTransactionsOnly = "supportsDataManipulationTransactionsOnly";   // Retrieves whether this database 
                                                                    // supports only data manipulation statements within a transaction.
    String supportsDifferentTableCorrelationNames = "supportsDifferentTableCorrelationNames";   // Retrieves whether, when table correlation 
                                                                    // names are supported, they are restricted to being different from 
                                                                    // the names of the tables.
    String supportsExpressionsInOrderBy = "supportsExpressionsInOrderBy";   // Retrieves whether this database supports expressions in 
                                                                    // ORDER BY lists.
    String supportsExtendedSQLGrammar = "supportsExtendedSQLGrammar";   // Retrieves whether this database supports the ODBC Extended SQL grammar.
    String supportsFullOuterJoins = "supportsFullOuterJoins";   // Retrieves whether this database supports full nested outer joins.
    String supportsGetGeneratedKeys = "supportsGetGeneratedKeys";   // Retrieves whether auto-generated keys can be retrieved after 
                                                                    // a statement has been executed
    String supportsGroupBy = "supportsGroupBy"; // Retrieves whether this database supports some form of GROUP BY clause.
    String supportsGroupByBeyondSelect = "supportsGroupByBeyondSelect"; // Retrieves whether this database supports using columns not 
                                                                    // included in the SELECT statement in a GROUP BY clause provided that 
                                                                    // all of the columns in the SELECT statement are included in 
                                                                    // the GROUP BY clause.
    String supportsGroupByUnrelated = "supportsGroupByUnrelated";   // Retrieves whether this database supports using a column that is 
                                                                    // not in the SELECT statement in a GROUP BY clause.
    String supportsIntegrityEnhancementFacility = "supportsIntegrityEnhancementFacility";   // Retrieves whether this database supports 
                                                                    // the SQL Integrity Enhancement Facility.
    String supportsLikeEscapeClause = "supportsLikeEscapeClause";   // supportsLikeEscapeClause whether this database supports 
                                                                    // specifying a LIKE escape clause.
    String supportsLimitedOuterJoins = "supportsLimitedOuterJoins"; // Retrieves whether this database provides limited support for outer joins.
    String supportsMinimumSQLGrammar = "supportsMinimumSQLGrammar"; // Retrieves whether this database supports the ODBC Minimum SQL grammar.
    String supportsMixedCaseIdentifiers = "supportsMixedCaseIdentifiers";   // Retrieves whether this database treats mixed case unquoted 
                                                                    // SQL identifiers as case sensitive and as a result stores them in mixed case.
    String supportsMixedCaseQuotedIdentifiers = "supportsMixedCaseQuotedIdentifiers";   // Retrieves whether this database treats mixed case 
                                                                    // quoted SQL identifiers as case sensitive and as a result stores 
                                                                    // them in mixed case.
    String supportsMultipleOpenResults = "supportsMultipleOpenResults"; // Retrieves whether it is possible to have multiple ResultSet 
                                                                    // objects returned from a CallableStatement object simultaneously.
    String supportsMultipleResultSets = "supportsMultipleResultSets"; // Retrieves whether this database supports getting multiple 
                                                                    // ResultSet objects from a single call to the method execute.
    String supportsMultipleTransactions = "supportsMultipleTransactions";   // Retrieves whether this database allows having multiple 
                                                                    // transactions open at once (on different connections).
    String supportsNamedParameters = "supportsNamedParameters"; // Retrieves whether this database supports named parameters to callable statements.
    String supportsNonNullableColumns = "supportsNonNullableColumns";   // Retrieves whether columns in this database may be defined as non-nullable.
    String supportsOpenCursorsAcrossCommit = "supportsOpenCursorsAcrossCommit"; // Retrieves whether this database supports keeping cursors 
                                                                    // open across commits.
    String supportsOpenCursorsAcrossRollback = "supportsOpenCursorsAcrossRollback"; // Retrieves whether this database supports keeping 
                                                                    // cursors open across rollbacks.
    String supportsOpenStatementsAcrossCommit = "supportsOpenStatementsAcrossCommit";   // Retrieves whether this database supports keeping 
                                                                    // statements open across commits.
    String supportsOpenStatementsAcrossRollback = "supportsOpenStatementsAcrossRollback";   // Retrieves whether this database supports 
                                                                    // keeping statements open across rollbacks.
    String supportsOrderByUnrelated = "supportsOrderByUnrelated";   // Retrieves whether this database supports using a column that 
                                                                    // is not in the SELECT statement in an ORDER BY clause.
    String supportsOuterJoins = "supportsOuterJoins";   // Retrieves whether this database supports some form of outer join.
    String supportsPositionedDelete = "supportsPositionedDelete";   // Retrieves whether this database supports positioned DELETE statements.
    String supportsPositionedUpdate = "supportsPositionedUpdate";   // Retrieves whether this database supports positioned UPDATE statements.
    String supportsResultSetConcurrency = "supportsResultSetConcurrency";   // Retrieves whether this database supports the given concurrency 
                                                                    // type in combination with the given result set type.
    String supportsResultSetHoldability = "supportsResultSetHoldability";   // Retrieves whether this database supports the given result set 
                                                                    // holdability.
    String supportsResultSetType = "supportsResultSetType"; // Retrieves whether this database supports the given result set type.
    String supportsSavepoints = "supportsSavepoints";   // Retrieves whether this database supports savepoints.
    String supportsSchemasInDataManipulation = "supportsSchemasInDataManipulation"; //Retrieves whether a schema name can be used in a 
                                                                    // data manipulation statement.
    String supportsSchemasInIndexDefinitions = "supportsSchemasInIndexDefinitions"; // Retrieves whether a schema name can be used 
                                                                    // in an index definition statement.
    String supportsSchemasInPrivilegeDefinitions = "supportsSchemasInPrivilegeDefinitions"; // Retrieves whether a schema name can 
                                                                    // be used in a privilege definition statement.
    String supportsSchemasInProcedureCalls = "supportsSchemasInProcedureCalls"; // Retrieves whether a schema name can be used in a 
                                                                    // procedure call statement.
    String supportsSchemasInTableDefinitions = "supportsSchemasInTableDefinitions";     // Retrieves whether a schema name can be used 
                                                                    // in a table definition statement.
    String supportsSelectForUpdate = "supportsSelectForUpdate";     // Retrieves whether this database supports SELECT FOR UPDATE statements.
    String supportsStatementPooling = "supportsStatementPooling";   // Retrieves whether this database supports statement pooling.
    String supportsStoredFunctionsUsingCallSyntax = "supportsStoredFunctionsUsingCallSyntax";   //Retrieves whether this database 
                                                                    // supports invoking user-defined or vendor functions using the 
                                                                    // stored procedure escape syntax.
    String supportsStoredProcedures = "supportsStoredProcedures";   // Retrieves whether this database supports stored procedure calls 
                                                                    // that use the stored procedure escape syntax.
    String supportsSubqueriesInComparisons = "supportsSubqueriesInComparisons"; // Retrieves whether this database supports subqueries 
                                                                    // in comparison expressions.
    String supportsSubqueriesInExists = "supportsSubqueriesInExists";   // Retrieves whether this database supports subqueries in 
                                                                    // EXISTS expressions.
    String supportsSubqueriesInIns = "supportsSubqueriesInIns"; // Retrieves whether this database supports subqueries in IN expressions.
    String supportsSubqueriesInQuantifieds = "supportsSubqueriesInQuantifieds"; // Retrieves whether this database supports subqueries 
                                                                    // in quantified expressions.
    String supportsTableCorrelationNames = "supportsTableCorrelationNames"; // Retrieves whether this database supports table correlation names.
    String supportsTransactionIsolationLevel = "supportsTransactionIsolationLevel"; // Retrieves whether this database supports 
                                                                    // the given transaction isolation level.
    String supportsTransactions = "supportsTransactions";   // Retrieves whether this database supports transactions.
    String supportsUnion = "supportsUnion"; // Retrieves whether this database supports SQL UNION.
    String supportsUnionAll = "supportsUnionAll";   // Retrieves whether this database supports SQL UNION ALL.
    String updatesAreDetected = "updatesAreDetected";   // Retrieves whether or not a visible row update can be detected by calling 
                                                                    // the method ResultSet.rowUpdated.
    String usesLocalFilePerTable = "usesLocalFilePerTable";     // Retrieves whether this database uses a file for each table.
    String usesLocalFiles = "usesLocalFiles";   // Retrieves whether this database stores tables in a local file.
}
