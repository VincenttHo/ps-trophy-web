package com.vincent.external.trophy.model;

import lombok.Data;

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
@Data
public class TrophySummaryResponse {

    /** 账号id */
    private String accountId;

    /** 奖杯等级 */
    private Integer trophyLevel;

    /** progress? */
    private Integer progress;

    /** tier? */
    private Integer tier;

    /** 获取的奖杯数统计 */
    private TrophyNum earnedTrophies;

}
