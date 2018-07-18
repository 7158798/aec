package com.hengxunda.common.utils;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.math.NumberUtils;
/**
 * @Author: lsl
 * @Date: create in 2018/6/6
 */
@Setter
@Getter
@Accessors(chain = true)
public class Page {

    /** 分页默认页 */
    public static final int DEFAULT_PAGE_NO = 1;
    /** 分页默认的每页条数 */
    public static final int DEFAULT_LIMIT = 15;
    /** 前台传递过来的分页参数名 */
    public static final String GLOBAL_PAGE = "pageNo";
    /** 前台传递过来的每页条数名 */
    public static final String GLOBAL_LIMIT = "limit";


    @ApiParam("当前页数, pc 端调用时不传则默认是 " + DEFAULT_PAGE_NO)
    private int pageNo;

    @ApiParam("每页条数, 不传则默认是 " + DEFAULT_LIMIT)
    private int limit;

    @ApiParam("系统内部使用，前端开发不必考虑该参数")
    private int beginNo;

    public Page() {
        this.pageNo = 1;
        this.limit = DEFAULT_LIMIT;
        this.beginNo = 0;
    }
    public Page(String pageNo, String limit) {
        int pageNum = NumberUtils.toInt(pageNo);
        if (pageNum <= 0) {
            pageNum = DEFAULT_PAGE_NO;
        }
        this.pageNo = pageNum;

        int limitNum = NumberUtils.toInt(limit);
        if (limitNum <= 0 || limitNum > 1000) {
            limitNum = DEFAULT_LIMIT;
        }
        this.limit = limitNum;

        this.beginNo = (pageNum-1)*limitNum-1;
    }

    public Page(int pageNo, int limit) {
        if (pageNo <= 0) {
            pageNo = DEFAULT_PAGE_NO;
        }
        this.pageNo = pageNo;

        if (limit <= 0 || limit > 1000) {
            limit = DEFAULT_LIMIT;
        }
        this.limit = limit;

        int beginNum = pageNo == 1 ? 0 : (pageNo-1)*limit;
        this.beginNo = beginNum;
    }



}
