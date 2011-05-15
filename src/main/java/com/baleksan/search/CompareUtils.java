package com.baleksan.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class CompareUtils {

    public static Diff<Fieldable, DocumentDiff> diff(String name, Document doc1, Document doc2) {
        Diff<Fieldable, DocumentDiff> result = new Diff<Fieldable, DocumentDiff>();
        result.setName(name);

        for (Fieldable field1 : doc1.getFields()) {
            Fieldable field2 = doc2.getField(field1.name());
            if (field2 == null) {
                result.addAdded(field1);
            } else if (!field1.stringValue().equals(field2.stringValue())) {
                result.addDiff(new DocumentDiff(field1, field2, "'" + field1.name() + ":" + field1.stringValue()
                        + "' vs. '" + field2.name() + ":" + field2.stringValue() + "'"));
            }
        }

        for (Fieldable field2 : doc2.getFields()) {
            Fieldable field1 = doc1.getField(field2.name());
            if (field1 == null) {
                result.addRemoved(field2);
            }
        }

        return result;
    }
}
