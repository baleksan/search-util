package com.baleksan.search;

import org.apache.lucene.document.Fieldable;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class DocumentDiff {
    Fieldable f1;
    Fieldable f2;
    String diff;

    public DocumentDiff(Fieldable f1, Fieldable f2, String diff) {
        this.f1 = f1;
        this.f2 = f2;
        this.diff = diff;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(diff);

        return builder.toString();
    }

    public boolean isEmpty() {
        return false;
    }
}
