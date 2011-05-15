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
    public Diff<Document, Diff<Fieldable, DocumentDiff>> compare(String indexDir1, String indexDir2, String keyFieldName)
            throws IOException, ParseException {
        FSDirectory dir1 = FSDirectory.open(new File(indexDir1));
        IndexReader reader1 = IndexReader.open(dir1);

        FSDirectory dir2 = FSDirectory.open(new File(indexDir2));
        IndexReader reader2 = IndexReader.open(dir2);

        return compare(reader1, reader2, keyFieldName);
    }

    protected Diff<Document, Diff<Fieldable, DocumentDiff>> compare(IndexReader reader1, IndexReader reader2, String keyFieldName)
            throws IOException, ParseException {
        Diff<Document, Diff<Fieldable, DocumentDiff>> result = new Diff<Document, Diff<Fieldable, DocumentDiff>>();
        for (int docId = 0; docId < reader1.numDocs(); docId++) {
            if (!reader1.isDeleted(docId)) {
                Document doc1 = reader1.document(docId);
                Field keyField = doc1.getField(keyFieldName);
                if (keyField == null) {
                    throw new IllegalArgumentException("Key field " + keyFieldName
                            + " should be defined in all docs in the index");
                }

                Document doc2 = findByKey(reader2, keyField);
                if (doc2 == null) {
                    result.addAdded(doc1);
                } else {
                    Diff<Fieldable, DocumentDiff> diff = CompareUtils.diff(keyField.stringValue(), doc1, doc2);
                    if (!diff.isEquals()) {
                        result.addDiff(diff);
                    }
                }
            }
        }

        for (int docId = 0; docId < reader2.numDocs(); docId++) {
            if (!reader2.isDeleted(docId)) {
                Document doc2 = reader2.document(docId);
                Field keyField = doc2.getField(keyFieldName);
                if (keyField == null) {
                    throw new IllegalArgumentException("Key field '" + keyFieldName
                            + "' should be defined in all docs in the index");
                }

                Document doc1 = findByKey(reader1, keyField);
                if (doc1 == null) {
                    result.addRemoved(doc2);
                }
            }
        }

        return result;
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
