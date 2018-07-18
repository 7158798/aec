package com.hengxunda.wapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WAppPage {

    @ApiModelProperty(value = "当前页")
    private Integer page;
    @ApiModelProperty(value = "每页显示条数")
    private Integer rows;
    @ApiModelProperty(value = "页数")
    private Integer pageSize;
    @ApiModelProperty(value = "总条数")
    private Integer total;

    public WAppPage(Integer page, Integer rows, Integer total) {
        this.page = page;
        this.rows = rows;
        this.total = total;
        this.pageSize=getPageSize(total,rows);
    }


    private static int getPageSize(int total,int rows){
        if (total > 0){
            return (total % rows) == 0 ? (total / rows) : ((total/rows) + 1);
        }else{
            return 0;
        }
    }


    public static Integer startRow(Integer page, Integer rows){
        return  (page-1)*rows;
    }

}
