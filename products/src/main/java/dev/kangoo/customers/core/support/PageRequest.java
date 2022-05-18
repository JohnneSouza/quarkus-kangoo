package dev.kangoo.customers.core.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@Getter
@Setter
public class PageRequest {

    @QueryParam("pageIndex")
    @DefaultValue("0")
    private int pageIndex;

    @QueryParam("pageSize")
    @DefaultValue("50")
    private int pageSize;
}
