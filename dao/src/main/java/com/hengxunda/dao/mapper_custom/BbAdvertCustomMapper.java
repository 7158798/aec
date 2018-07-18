package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.BbAdvert;
import com.hengxunda.dao.po.app.BbAdvertPo;
import com.hengxunda.dao.po.app.BbAdvertVo;
import com.hengxunda.dao.po.app.BbTransPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BbAdvertCustomMapper {

    /**
     * 查询所有币币交易广告列表
     *
     * @param userId 用户id，用于区分是否为用户广告
     * @param type   买卖类型
     * @return
     */
    List<BbAdvertVo> queryBbAdvertList(@Param("userId") String userId, @Param("type") Integer type);

    /**
     * 扣减币币交易剩余数量
     * @param bbAdvert
     * @return
     */
    int reduceRestAmountById(BbAdvert bbAdvert);

    /**
     * 修改挂单状态
     * @param bbAdvert
     * @return
     */
    int updateStatusById(BbAdvert bbAdvert);

    /**
     * 查询我的挂单
     * @param userId
     * @return
     */
    List<BbAdvertVo> queryMineAdvertList(String userId);

    /**
     * 统计同一价格挂单数量
     * @param bbAdvertPo
     * @return
     */
    int countByUserIdAndPrice(BbAdvertPo bbAdvertPo);

}
