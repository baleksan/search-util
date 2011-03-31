package com.baleksan.search.search;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;

import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import java.io.IOException;
import java.util.*;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class SearchUtils {
    private static final Logger LOG = Logger.getLogger(SearchUtils.class.getName());

    public static List<Integer> searchForDocIds(IndexReader reader, Analyzer analyzer, Version version,
                                                String queryString, String defaultField, int maxResults)
            throws IOException, ParseException {
        List<Integer> docIds = new ArrayList<Integer>();
        Searcher searcher = null;
        try {
            searcher = new IndexSearcher(reader);

            QueryParser queryParser = new QueryParser(version, defaultField, analyzer);
            queryParser.setDefaultOperator(QueryParser.Operator.OR);

            Query query;
            if (queryString == null || queryString.length() == 0) {
                query = new MatchAllDocsQuery();
            } else {
                query = queryParser.parse(queryString);
            }

            TopDocs docs = searcher.search(query, null, maxResults);

            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                docIds.add(scoreDoc.doc);
            }

            return docIds;
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    public static Map<String, List<String>> convert(Document doc) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        if (doc != null) {
            List<Fieldable> fields = doc.getFields();
            for (Fieldable field : fields) {
                String fieldname = field.name();
                map.put(fieldname, Arrays.asList(doc.getValues(fieldname)));
            }
        }
        return map;
    }

    public static Map<String, List<String>> highlight(List<String> fieldNames,
                                                      Document doc, Query query,
                                                      Analyzer analyzer,
                                                      String spanName) throws IOException {
        Map<String, List<String>> highlightedFields = new HashMap<String, List<String>>();
        for (String fieldName : fieldNames) {
            String content = doc.get(fieldName);
            if (content != null) {
                org.apache.lucene.search.highlight.Scorer qs = new QueryScorer(query);
                SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class=\"" + spanName + "\">", "</span>");
                Highlighter hl = new Highlighter(formatter, qs);
                try {
                    String[] fragments = hl.getBestFragments(analyzer, fieldName, content, 3);
                    if (fragments.length > 0) {
                        highlightedFields.put("highlighted-" + fieldName, Arrays.asList(fragments));
                    }
                } catch (InvalidTokenOffsetsException e) {
                    throw new IOException(e);
                }
            }
        }

        return highlightedFields;
    }
}
