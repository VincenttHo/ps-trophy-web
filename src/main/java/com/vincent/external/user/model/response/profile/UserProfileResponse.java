package com.vincent.external.user.model.response.profile;

import lombok.Data;
import lombok.ToString;

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
 * 2022-03-26    VincentHo       v1.0.0        create
 * @date 2022-03-26
 */
@Data
@ToString
public class UserProfileResponse {

    /** 用户名 */
    private String onlineId;

    /** 简介 */
    private String aboutMe;

    /** 头像（各种尺寸） */
    private List<UserAvatar> avatars;

    /** 所用语言 */
    private List<String> languages;

    /** isPlus? */
    private Boolean isPlus;

    /** isOfficiallyVerified */
    private Boolean isOfficiallyVerified;

    /** 是不是自己 */
    private Boolean isMe;

}
