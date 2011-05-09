package com.baleksan.search.analyzer;

import java.util.Collections;
import java.util.Set;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class EmptyStopWords implements StopWords {
    private static Set<String> stopWords;

    static {
        stopWords = Collections.emptySet();
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
