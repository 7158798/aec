package com.hengxunda.wapp.vo;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpVo {

    private String title;

    private List<Children> children;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Children {

        private String title;

        private String url;
    }

    public static class R {

        private String domain;

        R(String domain) {
            this.domain = domain;

            {
                security = Lists.newArrayList(
                        new Children("如何保障账号安全？", getUrl("help_safe_01.html")),
                        new Children("如何更换手机号码？", getUrl("help_safe_02.html")),
                        new Children("如何修改姓名？", getUrl("help_safe_03.html")),
                        new Children("收款方式是什么？如何添加？", getUrl("help_safe_04.html"))
                );

                wallet = Lists.newArrayList(
                        new Children("如何充值？", getUrl("help_wallet_01.html")),
                        new Children("如何提币？", getUrl("help_wallet_02.html")),
                        new Children("如何一键转币？", getUrl("help_wallet_03.html")),
                        new Children("充币到钱包，如何查询到账？", getUrl("help_wallet_04.html")),
                        new Children("转出到其他钱包，如何查询是否到账？", getUrl("help_wallet_05.html"))
                );

                trading = Lists.newArrayList(
                        new Children("如何申诉？", getUrl("help_trader_01.html")),
                        new Children("如何识别欺诈行为？", getUrl("help_trader_02.html")),
                        new Children("如何安全的出售？", getUrl("help_trader_03.html")),
                        new Children("如何安全的购买？", getUrl("help_trader_04.html")),
                        new Children("如何出售AEC币？", getUrl("help_trader_05.html")),
                        new Children("如何购买AEC币？", getUrl("help_trader_06.html"))
                );

                advert = Lists.newArrayList(
                        new Children("如何发布广告？", getUrl("help_adver_01.html")),
                        new Children("如何编辑广告？", getUrl("help_adver_02.html")),
                        new Children("为什么不能发布广告？", getUrl("help_adver_03.html")),
                        new Children("为什么看不到我的广告？", getUrl("help_adver_04.html"))
                );

                silver = Lists.newArrayList(
                        new Children("如何退出银商？", getUrl("help_silverout_01.html")),
                        new Children("保证金是什么？", getUrl("help_silverout_02.html")),
                        new Children("如何提高保证金？", getUrl("help_silverout_03.html")),
                        new Children("如何降低保证金？", getUrl("help_silverout_04.html")),
                        new Children("如何提取保证金？", getUrl("help_silverout_05.html"))
                );

            }
        }

        static List<Children> security;
        static List<Children> wallet;
        static List<Children> trading;
        static List<Children> advert;
        static List<Children> silver;

        private String getUrl(String name) {

            return new StringJoiner("/").add(domain).add(name).toString();
        }

    }

    public static List<HelpVo> builder(String domain) {
        new R(domain);
        return Lists.newArrayList(
                new HelpVo("账号安全", R.security),
                new HelpVo("钱包相关", R.wallet),
                new HelpVo("安全交易", R.trading),
                new HelpVo("发布广告", R.advert),
                new HelpVo("银商相关", R.silver)
        );
    }

}