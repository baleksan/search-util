package com.baleksan.search;

import org.apache.lucene.document.Document;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class IndexDiff {
    Document doc1;
    Document doc2;
    DocumentDiff diff;

    public IndexDiff(Document doc1, Document doc2, DocumentDiff diff) {
        this.doc1 = doc1;
        this.doc2 = doc2;
        this.diff = diff;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(diff.toString());

        return builder.toString();
    }
}
