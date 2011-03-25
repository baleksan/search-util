package com.baleksan.search.analyzer;

import java.util.Set;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public interface StopWords {
    boolean isStopWord(String word);
    Set<String> getStopWords();
}
