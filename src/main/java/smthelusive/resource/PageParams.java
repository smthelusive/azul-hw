package smthelusive.resource;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PageParams {
    @QueryParam("page")
    @DefaultValue("0")
    private int page;
    @QueryParam("pageSize")
    @DefaultValue("10")
    private int pageSize;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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