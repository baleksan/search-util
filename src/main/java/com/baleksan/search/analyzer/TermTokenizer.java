package com.baleksan.search.analyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class TermTokenizer {
    public static List<String> tokenize(String content) throws TokenizationException {
        return tokenize(content, true);
    }

    private static final Pattern TRAILING_PUNCT = Pattern.compile("[\\p{Punct}&&[^-]]+");

    public static List<String> tokenizeKeepIsolatedPunctuation(String content, boolean toLowerCase) {
        String[] firstSplit = content.split("\\p{Space}+");
        List<String> tokens = new ArrayList<String>();

        for (String tokenFromSplit : firstSplit) {
            Matcher matcher = TRAILING_PUNCT.matcher(tokenFromSplit);
            String additionalToken = null;
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();

                if (start != 0 && end == tokenFromSplit.length()) {
                    additionalToken = matcher.group();
                    tokenFromSplit = tokenFromSplit.substring(0, start);
                    break;
                }
            }

            tokens.add(toLowerCase ? tokenFromSplit.toLowerCase() : tokenFromSplit);
            if (null != additionalToken) {
                tokens.add(additionalToken);
            }
        }

        return tokens;
    }

    public static List<String> tokenize(String content, boolean toLowerCase) throws TokenizationException{
        List<String> tokens = new ArrayList<String>();
        try {
            Reader docReader = new BufferedReader(new StringReader(content));
            TokenStream result = new StandardTokenizer(Version.LUCENE_30, docReader);
            while(result.incrementToken()) {
                String token = result.getAttribute(TermAttribute.class).term();
                tokens.add(toLowerCase  ? token.toLowerCase() : token);
            }
        } catch (IOException ex) {
             throw new TokenizationException("Cannot tokenize '" + content + "'");
        }

        return tokens;
    }

    public static List<String> tokenizeNgrams(String content, int min, int max) throws TokenizationException {
        List<String> rawTokens = tokenize(content);
        List<String> tokens = new ArrayList<String>();
        for (int n = min; n <= max; n++) {
            tokens.addAll(tokenizeNgrams(rawTokens, n));
        }

        return tokens;
    }

    public static List<String> tokenizeNgrams(List<String> rawTokens, int n) {
        List<String> tokens = new ArrayList<String>();
        for (int i = 0; i < rawTokens.size() - n + 1; i++) {
            StringBuilder ngram = new StringBuilder();
            for (int j = 0; j < n; j++) {
                ngram.append(rawTokens.get(i + j));
                if (j < n - 1) {
                    ngram.append(" ");
                }
            }
            tokens.add(ngram.toString());
        }

        return tokens;
    }
}
