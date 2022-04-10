package com.vincent.api.trophy.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document("trophySummary")
@Schema(title = "奖杯统计信息", description = "奖杯统计信息")
public class TrophySummaryDTO {

    @Id
    private String psnId;

    /** 账号id */
    private String accountId;

    /** 奖杯等级 */
    @Schema(title = "trophyLevel")
    private Integer trophyLevel;

    /** 铜杯个数 */
    @Schema(title = "bronze")
    private Integer bronze;

    /** 银杯个数 */
    @Schema(title = "silver")
    private Integer silver;

    /** 金杯个数 */
    @Schema(title = "gold")
    private Integer gold;

    /** 白金杯个数 */
    @Schema(title = "platinum")
    private Integer platinum;

}
