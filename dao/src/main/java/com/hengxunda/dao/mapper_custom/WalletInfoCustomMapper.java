package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


public interface WalletInfoCustomMapper {

    WalletInfo getByAddress(@Param("address") String address, @Param("type") String type);

    void  updateUserBalance(@Param("address") String  address,
                            @Param("type") String  type,
                            @Param("change_balance") BigDecimal change_balance);
    int batchReleaseMscByUserId(List<WalletInfo> walletInfoList);

    WalletInfo getByUserIdOrStatusOrType(@Param("userId") String userId, @Param("status") Integer status, @Param("type") String type);

    List<WalletInfo> getByUserId(@Param("userId") String userId);

    WalletInfo  getByUserIdAndType(@Param("userId") String userId,@Param("type") String type);

    WalletInfo getByUserIdAndTypeForUpdate(@Param("userId") String userId,@Param("type") String type);

    int updateBalanceAndFrozenBlanceById(@Param("amount") BigDecimal amount, @Param("walletId") String walletId);

    int updateBalanceAndBondByUserId(@Param("amount") BigDecimal amount, @Param("userId") String userId);

    int updateLessBondByUserId(@Param("amount") BigDecimal amount, @Param("userId") String userId);

    int addMoneyForUser(WalletInfo walletInfo);

    int batchAddMoneyForUser(List<WalletInfo> walletInfoList);

    int updateWalletInfoByUserIdAndType(WalletInfo walletInfo);

    int updateBond(@Param("amount") BigDecimal amount, @Param("userId") String userId, @Param("type") String type, @Param("status") Integer status);

    int updateBalance(@Param("operator") String operator,@Param("amount") BigDecimal amount, @Param("userId") String userId,@Param("type") String type,@Param("flag") Boolean flag);


    BigDecimal sumAllUserBalanceByType(String type);

    List<WalletInfo> sumUserBalanceByType(String type);

    void bindWalletInfoAddress(@Param("userId") String userId);

    int updateSameCoinTradeBalance(WalletRecord walletRecord);

    int updateMsc2AecBalance(WalletRecord walletRecord);

    int updateAec2BtcAndLtcBalance(WalletRecord walletRecord);

    int updateAppealOrderSilverBalance(WalletRecord walletRecord);

    int updateAppealOrderBalance(WalletRecord walletRecord);


    int reduceBalance(@Param("amount") BigDecimal amount, @Param("userId") String userId,@Param("type") String type);

    int frozeByUserIdAndType(WalletInfo walletInfo);

    int reduceFrozeBalanceByUserIdAndType(WalletInfo walletInfo);

    int reduceBalanceByUserIdAndType(WalletInfo walletInfo);

    int releaseBalanceByUserIdAndType(WalletInfo walletInfo);
}


