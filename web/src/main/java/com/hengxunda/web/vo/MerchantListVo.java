package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.MerchantPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/7
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MerchantListVo extends PageVo {

    private List<MerchantVo> merchantVos;

    public static MerchantListVo format(List<MerchantPo> merchantVos){
        MerchantListVo merchantListVo = new MerchantListVo();
        List<MerchantVo> merchantVoList = new ArrayList<>();
        for(MerchantPo m:merchantVos){
            MerchantVo merchantVo = new MerchantVo();
            merchantVo.setId(m.getId()).setName(m.getName()).setNickName(m.getNickName()).setPhone(m.getPhone())
                    .setCreateTime(m.getCreateTime()).setStatus(m.getStatus()).setUserId(m.getUid()).setBond(m.getBond()).setBalance(m.getBalance());
            merchantVoList.add(merchantVo);
        }
        return merchantListVo.setMerchantVos(merchantVoList);
    }
}
