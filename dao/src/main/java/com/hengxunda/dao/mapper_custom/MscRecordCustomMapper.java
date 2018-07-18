package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.MscRecord;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.po.general.UserMscAmountPo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MscRecordCustomMapper {
    /**
     * 查询所有股权msc总数
     *
     * @return
     */
    BigDecimal sumAllUserMsc();

    /**
     * 查询每个用户的股权msc总数
     *
     * @return
     */
    List<MscRecord> queryUserMsc();

    /**
     * 查询用户的业绩，剔除当前业绩
     *
     * @param userId
     * @return
     */
    UserMscAmountPo sumAchievementByUserIdExcludeByMscId(@Param("userId")String userId,@Param("mscRecordId")String mscRecordId);

    /**
     * 查询用户的业绩
     *
     * @param userId
     * @return
     */
    UserMscAmountPo sumAchievementByUserId(@Param("userId")String userId);


    /**
     * 查询用户子代的业绩，剔除当前业绩
     *
     * @param userId
     * @return
     */
    List<UserMscAmountPo> sumChildAchievementByUserIdExcludeByMscId(@Param("userId")String userId,@Param("mscRecordId")String mscRecordId);

    /**
     * 查询用户子代的业绩
     *
     * @param userId
     * @return
     */
    List<UserMscAmountPo> sumChildAchievementByUserId(@Param("userId")String userId);

    /**
     * 查询自己及所有上级用户自购业绩
     *
     * @param userList
     * @return
     */
    List<UserMscAmountPo> sumAchievementForSelfAndParents(List<User> userList);

    /**
     * 查询自己及子一代自购业绩合格个数
     *
     * @param userId       用户id
     * @param qualifiedNum 业绩合格aec基数
     * @return
     */
    int countQualifiedNum(@Param("userId") String userId, @Param("qualifiedNum") BigDecimal qualifiedNum);

    /**
     * 批量保存购买股权记录
     * @param mscRecordList
     * @return
     */
    int batchInsert(List<MscRecord> mscRecordList);

    /**
     * 批量修改剩余股权数
     * @param mscRecordList
     * @return
     */
    int batchUpdateRestAmount(List<MscRecord> mscRecordList);

    /**
     * 统计AEC购买MSC的总数
     * @return
     */
    BigDecimal sumBuyMscAmount();
}