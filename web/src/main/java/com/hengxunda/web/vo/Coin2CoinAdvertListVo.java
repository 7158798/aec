package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.Coin2CoinAdvertPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Description:
 *
 * @Author: QiuJY
 * @Date: Created in 17:51 2018/7/9
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Coin2CoinAdvertListVo extends PageVo{

    private List<Coin2CoinAdvertPo> coin2CoinAdvertPos;
}
