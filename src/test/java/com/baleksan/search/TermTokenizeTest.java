package com.baleksan.search;

import com.baleksan.search.analyzer.TermTokenizer;
import com.baleksan.search.analyzer.TokenizationException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class TermTokenizeTest {

    @Test
    public void testTokenizer() throws TokenizationException {
        List<String> tokens = TermTokenizer.tokenize("What are you working on?");
        Assert.assertEquals(tokens.size(), 5);
    }
}
