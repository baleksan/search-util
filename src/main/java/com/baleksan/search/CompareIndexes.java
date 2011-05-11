package com.baleksan.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class CompareIndexes {
    private Version luceneVersion;
    private Analyzer queryAnalyzer;

    public CompareIndexes(Version luceneVersion, Analyzer queryAnalyzer) {
        this.luceneVersion = luceneVersion;
        this.queryAnalyzer = queryAnalyzer;
    }

    /**
     * Compares indexes at different location based on the key field which should contain the unique value.
     *
     * @param indexDir1    indexDir1
     * @param indexDir2    indexDir2
     * @param keyFieldName keyFieldName
     * @return result of the compare
     * @throws IOException    problems accessing indexes
     * @throws ParseException problems parsing query
     */
    public boolean compare(String indexDir1, String indexDir2, String keyFieldName) throws IOException, ParseException {
        FSDirectory dir1 = FSDirectory.open(new File(indexDir1));
        IndexReader reader1 = IndexReader.open(dir1);

        FSDirectory dir2 = FSDirectory.open(new File(indexDir2));
        IndexReader reader2 = IndexReader.open(dir2);

        return compare(reader1, reader2, keyFieldName);
    }

    protected boolean compare(IndexReader reader1, IndexReader reader2, String keyFieldName) throws IOException, ParseException {
        if (reader1.numDocs() != reader2.numDocs()) {
            return false;
        }

        for (int docId = 0; docId < reader1.numDocs(); docId++) {
            if (!reader1.isDeleted(docId)) {
                Document doc1 = reader1.document(docId);
                Field keyField = doc1.getField(keyFieldName);
                if (keyField == null) {
                    throw new IllegalArgumentException("Key field " + keyFieldName
                            + " should be defined in all docs in the index");
                }

                Document doc2 = findByKey(reader2, keyField);
                if (doc2 == null || !documentEquals(doc1, doc2)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean documentEquals(Document doc1, Document doc2) {
        for (Fieldable field1 : doc1.getFields()) {
            Fieldable field2 = doc2.getField(field1.name());
            if (field2 == null) {
                return false;
            }

            if (!field1.stringValue().equals(field2.stringValue())) {
                return false;
            }
        }

        return true;
    }

    private Document findByKey(IndexReader reader, Field keyField) throws ParseException, IOException {
        Searcher searcher = new IndexSearcher(reader);
        QueryParser queryParser = new QueryParser(luceneVersion, keyField.name(), queryAnalyzer);
        queryParser.setDefaultOperator(QueryParser.Operator.AND);

        String queryString = keyField.name() + ":" + keyField.stringValue();
        Query query = queryParser.parse(queryString);

        TopDocs docs = searcher.search(query, 10000);
        ScoreDoc[] scoreDocs = docs.scoreDocs;
        if (scoreDocs.length != 1) {
            return null;
        }

        ScoreDoc doc = scoreDocs[0];
        return searcher.doc(doc.doc);
    }
}
