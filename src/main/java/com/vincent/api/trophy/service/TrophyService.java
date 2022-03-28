package com.vincent.api.trophy.service;

import com.vincent.api.trophy.model.TrophySummaryDTO;
import com.vincent.external.trophy.api.TrophyApi;
import com.vincent.external.trophy.model.TrophySummaryResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C) 2022 广东百慧科技有限公司
 *
 * <p>在这里说明当前类/接口/枚举的业务用途</p>
 *
 * @author VincentHo
 * @version 1.0.0
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2022-03-26    VincentHo       v1.0.0        create
 * @date 2022-03-26
 */
@Service
public class TrophyService {

    @Autowired
    private TrophyApi trophyApi;

    /**
     * <p>获取奖杯汇总信息</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param accountId
     * @return com.vincent.api.trophy.model.TrophySummaryDTO
     */
    public TrophySummaryDTO getTrophySummary(String accountId) {

        TrophySummaryResponse trophySummary = trophyApi.getTrophySummary(accountId);

        TrophySummaryDTO result = new TrophySummaryDTO();
        BeanUtils.copyProperties(trophySummary, result);
        BeanUtils.copyProperties(trophySummary.getEarnedTrophies(), result);

        return result;

    }

}
