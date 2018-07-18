package com.hengxunda.common.utils;

import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.entity.RateEntity;
import com.hengxunda.common.entity.RateList;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询税率
 */
public class TaxRateUtil {

    private static String host = "https://ali-waihui.showapi.com";
    private static String path = "/waihui-transform";
    private static String method = "GET";
    private static String appcode = "684b33fb9b334e63b5f056f35d62c992";

    private static String send(String fromCode,String toCode){
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("fromCode", fromCode);
        querys.put("money", "1");
        querys.put("toCode", toCode);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 人民币兑换美元
     * @return
     */
    public static RateEntity cny2usd(){
        String result = send(WalletTypeEnum.CNY.getCode(),WalletTypeEnum.USD.getCode());
        if(StringUtils.isBlank(result)){
            return null;
        }
        RateList rateList = GsonUtils.getGson().fromJson(result,RateList.class);
        return rateList.getShowapi_res_body();
    }

    /**
     * 人民币兑换港币
     * @return
     */
    public static RateEntity cny2hkd(){
        String result = send(WalletTypeEnum.CNY.getCode(),WalletTypeEnum.HKD.getCode());
        if(StringUtils.isBlank(result)){
            return null;
        }
        RateList rateList = GsonUtils.getGson().fromJson(result,RateList.class);
        return rateList.getShowapi_res_body();
    }

    /**
     * 人民币兑换欧元
     * @return
     */
    public static RateEntity cny2eur(){
        String result = send(WalletTypeEnum.CNY.getCode(),WalletTypeEnum.EUR.getCode());
        if(StringUtils.isBlank(result)){
            return null;
        }
        RateList rateList = GsonUtils.getGson().fromJson(result,RateList.class);
        return rateList.getShowapi_res_body();
    }
}
