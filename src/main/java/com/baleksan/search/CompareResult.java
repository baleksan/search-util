package com.baleksan.search;

import com.baleksan.util.Util;
import org.apache.lucene.document.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class CompareResult {
    private String resultPrefix;
    private List<Document> added;
    private List<Document> removed;
    private List<DocumentPair> diffs;

    public CompareResult() {
        added = new ArrayList<Document>();
        removed = new ArrayList<Document>();
        diffs = new ArrayList<DocumentPair>();
    }

    public void setResultPrefix(String resultPrefix) {
        this.resultPrefix = resultPrefix;
    }

    public void addAdded(Document doc) {
        added.add(doc);
    }

    public void addRemoved(Document doc) {
        removed.add(doc);
    }

    public void addDiff(Document doc1, Document doc2) {
        diffs.add(new DocumentPair(doc1, doc2));
    }

    public List<Document> getAdded() {
        return added;
    }

    public List<Document> getRemoved() {
        return removed;
    }

    public List<DocumentPair> getDiffs() {
        return diffs;
    }

    public boolean isEquals() {
        return added.size() == 0 && removed.size() == 0 && diffs.size() == 0;
    }

    public String describe() {
        StringBuilder builder = new StringBuilder();
        builder.append("Compare results for ");
        builder.append(resultPrefix);
        builder.append(Util.EOL);

        builder.append("Added {");
        for (Document doc : added) {
            builder.append(doc.toString());
            builder.append(", ");
        }
        builder.append("}");
        builder.append(Util.EOL);

        builder.append("Removed {");
        for (Document doc : removed) {
            builder.append(doc.toString());
            builder.append(", ");
        }
        builder.append("}");
        builder.append(Util.EOL);

        builder.append("Diffs {");
        for (DocumentPair diff : diffs) {
            builder.append(diff.toString());
            builder.append(", ");
        }
        builder.append("}");
        builder.append(Util.EOL);

        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(resultPrefix);
        builder.append(": Added ");
        builder.append(added.size());
        builder.append(":, Removed ");
        builder.append(removed.size());
        builder.append(", Diffs ");
        builder.append(diffs.size());

        return builder.toString();
    }

    public class DocumentPair {
        Document doc1;
        Document doc2;

        public DocumentPair(Document doc1, Document doc2) {
            this.doc1 = doc1;
            this.doc2 = doc2;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append(doc1.toString());
            builder.append(" vs. ");
            builder.append(doc2.toString());

            return builder.toString();
        }
    }
}
