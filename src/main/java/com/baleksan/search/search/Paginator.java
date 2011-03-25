package com.baleksan.search.search;

/**
 *
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class Paginator {
    public Page calculatePage(int totalHits, int pageNumber, int pageSize) {
        Page page = new Page();
        if (pageNumber < 1 || pageSize < 1) {
            throw new IllegalArgumentException("Page number and page size should be > 0");
        }

        int start = 1 + (pageNumber - 1) * pageSize;
        int end = Math.min(pageNumber * pageSize, totalHits);
        if (start > end) {
            start = Math.max(1, end - pageSize);
        }

        page.start = start;
        page.end = end;

        return page;
    }


    public class Page {
        public int start;
        public int end;
    }

}
