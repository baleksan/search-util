package com.baleksan.search.analyzer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class StandardAnalyzerStopWords  implements StopWords {
    private static Set<String> stopWords;

    static {
        stopWords = new HashSet<String>();
        for (Object word : StandardAnalyzer.STOP_WORDS_SET) {
            stopWords.add((String) word);
        }
    }

    @Override
    public boolean isStopWord(String word) {
        return stopWords.contains(word.toLowerCase());
    }

    @Override
    public Set<String> getStopWords() {
        return stopWords;
    }
}
