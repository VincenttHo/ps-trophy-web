package com.vincent.external.user.model.response.profile;

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
public class UserAvatarResponse {

    /** 尺寸（s-小，m-中，l-大，xl-超大） */
    private String size;

    /** 图片地址 */
    private String url;

}
