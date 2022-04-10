package com.vincent.api.trophy.model;

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
 * 2022-04-09    VincentHo       v1.0.0        create
 * @date 2022-04-09
 */
@Data
public class Trophy {

    private String trophyId;

    private Boolean trophyHidden;

    private String trophyType;

    private String trophyName;

    private String trophyDetail;

    private String trophyIconUrl;

    private String trophyGroupId;

    private Boolean earned;

    private Integer trophyRare;

    private String trophyEarnedRate;

}
