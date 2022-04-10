package com.vincent.external.trophy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vincent.api.trophy.model.Trophy;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
 * 2022-04-09    VincentHo       v1.0.0        create
 * @date 2022-04-09
 */
@Data
public class TrophyDetailResponse {

    private String trophySetVersion;

    private Boolean hasTrophyGroups;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date lastUpdatedDateTime;

    private List<Trophy> trophies;

}
