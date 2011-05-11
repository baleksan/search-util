package com.baleksan.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class CompareIndexesTest {

    @Test
    public void compareIndexesTestEquals() throws IOException, ParseException {
        Version luceneVersion = Version.LUCENE_30;
        Analyzer analyzer = new StandardAnalyzer(luceneVersion);

        CompareIndexes compare = new CompareIndexes(luceneVersion, analyzer);

        RAMDirectory dir1 = new RAMDirectory();
        IndexWriter writer1 = new IndexWriter(dir1, analyzer, IndexWriter.MaxFieldLength.UNLIMITED);

        Document doc1 = new Document();
        doc1.add(new Field("key", "1", Field.Store.YES, Field.Index.ANALYZED));
        doc1.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc1.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        Document doc2 = new Document();
        doc2.add(new Field("key", "2", Field.Store.YES, Field.Index.ANALYZED));
        doc2.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc2.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        Document doc3 = new Document();
        doc3.add(new Field("key", "3", Field.Store.YES, Field.Index.ANALYZED));
        doc3.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc3.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        writer1.addDocument(doc1);
        writer1.addDocument(doc2);
        writer1.addDocument(doc3);

        RAMDirectory dir2 = new RAMDirectory();
        IndexWriter writer2 = new IndexWriter(dir2, analyzer, IndexWriter.MaxFieldLength.UNLIMITED);

        writer2.addDocument(doc1);
        writer2.addDocument(doc2);
        writer2.addDocument(doc3);

        boolean result = compare.compare(writer1.getReader(), writer2.getReader(), "key");
        Assert.assertTrue(result);
    }

    @Test
    public void compareIndexesTestNotEquals() throws IOException, ParseException {
        Version luceneVersion = Version.LUCENE_30;
        Analyzer analyzer = new StandardAnalyzer(luceneVersion);

        CompareIndexes compare = new CompareIndexes(luceneVersion, analyzer);

        RAMDirectory dir1 = new RAMDirectory();
        IndexWriter writer1 = new IndexWriter(dir1, analyzer, IndexWriter.MaxFieldLength.UNLIMITED);

        Document doc1 = new Document();
        doc1.add(new Field("key", "1", Field.Store.YES, Field.Index.ANALYZED));
        doc1.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc1.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        Document doc2 = new Document();
        doc2.add(new Field("key", "2", Field.Store.YES, Field.Index.ANALYZED));
        doc2.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc2.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        Document doc3 = new Document();
        doc3.add(new Field("key", "3", Field.Store.YES, Field.Index.ANALYZED));
        doc3.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc3.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        writer1.addDocument(doc1);
        writer1.addDocument(doc2);
        writer1.addDocument(doc3);

        RAMDirectory dir2 = new RAMDirectory();
        IndexWriter writer2 = new IndexWriter(dir2, analyzer, IndexWriter.MaxFieldLength.UNLIMITED);

        writer2.addDocument(doc1);
        writer2.addDocument(doc2);

        boolean result = compare.compare(writer1.getReader(), writer2.getReader(), "key");
        Assert.assertFalse(result);
    }

    @Test
    public void compareIndexesTestNotEquals1() throws IOException, ParseException {
        Version luceneVersion = Version.LUCENE_30;
        Analyzer analyzer = new StandardAnalyzer(luceneVersion);

        CompareIndexes compare = new CompareIndexes(luceneVersion, analyzer);

        RAMDirectory dir1 = new RAMDirectory();
        IndexWriter writer1 = new IndexWriter(dir1, analyzer, IndexWriter.MaxFieldLength.UNLIMITED);

        Document doc1 = new Document();
        doc1.add(new Field("key", "1", Field.Store.YES, Field.Index.ANALYZED));
        doc1.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc1.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        Document doc2 = new Document();
        doc2.add(new Field("key", "2", Field.Store.YES, Field.Index.ANALYZED));
        doc2.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc2.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        Document doc3 = new Document();
        doc3.add(new Field("key", "3", Field.Store.YES, Field.Index.ANALYZED));
        doc3.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc3.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        writer1.addDocument(doc1);
        writer1.addDocument(doc2);
        writer1.addDocument(doc3);

        RAMDirectory dir2 = new RAMDirectory();
        IndexWriter writer2 = new IndexWriter(dir2, analyzer, IndexWriter.MaxFieldLength.UNLIMITED);

        writer2.addDocument(doc1);
        writer2.addDocument(doc2);

        Document doc4 = new Document();
        doc1.add(new Field("key", "4", Field.Store.YES, Field.Index.ANALYZED));
        doc1.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc1.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        Document doc5 = new Document();
        doc2.add(new Field("key", "5", Field.Store.YES, Field.Index.ANALYZED));
        doc2.add(new Field("content", "foo", Field.Store.YES, Field.Index.ANALYZED));
        doc2.add(new Field("tag", "bla", Field.Store.YES, Field.Index.ANALYZED));

        writer2.addDocument(doc4);
        writer2.addDocument(doc5);

        boolean result = compare.compare(writer1.getReader(), writer2.getReader(), "key");
        Assert.assertFalse(result);
    }
}
