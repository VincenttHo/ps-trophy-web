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
public class TrophyNum {

    /** 铜杯个数 */
    private Integer bronze;

    /** 银杯个数 */
    private Integer silver;

    /** 金杯个数 */
    private Integer gold;

    /** 白金杯个数 */
    private Integer platinum;

}
