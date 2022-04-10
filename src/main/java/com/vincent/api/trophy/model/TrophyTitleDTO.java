package com.vincent.api.trophy.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

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
@Document("trophyTitle")
public class TrophyTitleDTO {

    @Id
    private String id;

    private String psnId;

    private String accountId;

    /** npServiceName */
    private String npServiceName;

    /** 游戏的唯一识别号 */
    private String npCommunicationId;

    /** 奖杯当前版本 */
    private String trophySetVersion;

    /** 游戏名 */
    private String trophyTitleName;

    /** 游戏图标 */
    private String trophyTitleIconUrl;

    /** 游戏所在平台 */
    private String trophyTitlePlatform;

    /** 是否有奖杯组（如dlc奖杯等，会分组） */
    private String hasTrophyGroups;

    /** 所有奖杯统计数 */
    private TrophyNum definedTrophies;

    /** 完成度（百分比） */
    private String progress;

    /** 已获得奖杯统计数 */
    private TrophyNum earnedTrophies;

    /** hiddenFlag */
    private Boolean hiddenFlag;

    /** 最后上传时间 */
    private Date lastUpdatedDateTime;

}
