package com.hengxunda.task.model;

import lombok.Getter;

@Getter
public enum SettingsField {
    SYNC_ETH_BLOCK_NO("SYNC_ETH_BLOCK_NO", "同步以太坊最新区块号", null),
    SYNC_BTC_BLOCK_NO("SYNC_BTC_BLOCK_NO", "同步比特币最新区块号", null),
    SYNC_LTC_BLOCK_NO("SYNC_LTC_BLOCK_NO", "同步莱特币最新区块号", null),
    SYNC_AEC_BLOCK_NO("SYNC_AEC_BLOCK_NO", "同步AEC最新区块号", null),
    SYNC_MSC_BLOCK_NO("SYNC_MSC_BLOCK_NO", "同步MSC最新区块号", null);

    private String code;
    private String name;
    private String defaultValue;

    SettingsField(String code, String name, String defaultValue) {
        this.code = code;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public static SettingsField getByCode(String code) {
        for (SettingsField field : SettingsField.values()) {
            if(field.code.equals(code)) {
                return field;
            }
        }

        return null;
    }
}
