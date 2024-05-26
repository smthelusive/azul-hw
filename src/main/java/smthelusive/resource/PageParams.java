package smthelusive.resource;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PageParams {
    @QueryParam("pages")
    @DefaultValue("0")
    private int pages; // todo rename to page
    @QueryParam("pageSize")
    @DefaultValue("10")
    private int pageSize;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PageParams() {
    }
}