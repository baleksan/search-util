package com.baleksan.search;

import com.baleksan.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class Diff<F, D> {
    private String name;
    private List<F> added;
    private List<F> removed;
    private List<D> diffs;

    public Diff() {
        added = new ArrayList<F>();
        removed = new ArrayList<F>();
        diffs = new ArrayList<D>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAdded(F doc) {
        added.add(doc);
    }

    public void addRemoved(F doc) {
        removed.add(doc);
    }

    public void addDiff(D diff) {
        diffs.add(diff);
    }

    public List<F> getAdded() {
        return added;
    }

    public List<F> getRemoved() {
        return removed;
    }

    public List<D> getDiffs() {
        return diffs;
    }

    public boolean isEquals() {
        return added.size() == 0 && removed.size() == 0 && diffs.size() == 0;
    }

    public String describe() {
        StringBuilder builder = new StringBuilder();
        builder.append("Compare results for ");
        builder.append(name);
        builder.append(Util.EOL);

        builder.append("Added {");
        for (F doc : added) {
            builder.append(doc.toString());
            builder.append(", ");
        }
        builder.append("}");
        builder.append(Util.EOL);

        builder.append("Removed {");
        for (F doc : removed) {
            builder.append(doc.toString());
            builder.append(", ");
        }
        builder.append("}");
        builder.append(Util.EOL);

        builder.append("Diffs {");
        for (D diff : diffs) {
            builder.append(diff.toString());
            builder.append(", ");
        }
        builder.append("}");
        builder.append(Util.EOL);

        return builder.toString();
    }

    @Override
    public String toString() {
        return describe();
    }
}
