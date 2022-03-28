package com.vincent.external.user.model.response.info;

import lombok.Data;
import lombok.ToString;

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
@ToString
public class UserInfoResponse {

    /** 账号id */
    private String accountId;

    /** 国家 */
    private String country;

    /** 用户名 */
    private String onlineId;

    /** firstName */
    private String firstName;

    /** lastName */
    private String lastName;

    /** 是否会员 */
    private Boolean isPsPlus;

    /** 是否正式验证？ */
    private Boolean isOfficiallyVerified;

    /** 头像地址 */
    private String avatarUrl;

    /** 自定义头像地址 */
    private String profilePicUrl;

    /** verifiedUserName */
    private String verifiedUserName;

    /** relationshipState */
    private String relationshipState;

}
