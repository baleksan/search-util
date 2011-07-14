package com.baleksan.search.analyzer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:boris@flipboard.com" boris/>
 */
public class ArrayBuildStopWords implements StopWords {
    private Set<String> stopWords;

    public ArrayBuildStopWords(String[] words) {
        stopWords = new HashSet<String>();
        stopWords.addAll(Arrays.asList(words));
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
