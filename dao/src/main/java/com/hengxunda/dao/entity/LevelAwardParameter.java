package com.hengxunda.dao.entity;

import java.math.BigDecimal;

public class LevelAwardParameter {
    private Integer id;

    private String levelName;

    private String levelCode;

    private BigDecimal minAmt;

    private BigDecimal maxAmt;

    private BigDecimal prizeAec;

    private BigDecimal prizeMsc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
    }

    public BigDecimal getMinAmt() {
        return minAmt;
    }

    public void setMinAmt(BigDecimal minAmt) {
        this.minAmt = minAmt;
    }

    public BigDecimal getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(BigDecimal maxAmt) {
        this.maxAmt = maxAmt;
    }

    public BigDecimal getPrizeAec() {
        return prizeAec;
    }

    public void setPrizeAec(BigDecimal prizeAec) {
        this.prizeAec = prizeAec;
    }

    public BigDecimal getPrizeMsc() {
        return prizeMsc;
    }

    public void setPrizeMsc(BigDecimal prizeMsc) {
        this.prizeMsc = prizeMsc;
    }
}