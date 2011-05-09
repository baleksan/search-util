package com.baleksan.search.analyzer;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class TokenizationException extends Exception {
    public TokenizationException() {
    }

    public TokenizationException(String message) {
        super(message);
    }

    public TokenizationException(Throwable cause) {
        super(cause);
    }

    public TokenizationException(String message, Throwable cause) {
        super(message, cause);
    }
}

