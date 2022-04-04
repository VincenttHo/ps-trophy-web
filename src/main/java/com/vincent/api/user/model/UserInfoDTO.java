package com.vincent.api.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
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
 * 2022-03-17    VincentHo       v1.0.0        create
 * @date 2022-03-17
 */
@Data
@Document("userInfo")
@Schema(title = "用户信息", description = "用户信息")
public class UserInfoDTO {

    /** 账号id */
    @Id
    @Schema(title = "accountId")
    private String accountId;

    /** 国家 */
    @Schema(title = "country")
    private String country;

    /** 用户名 */
    @Schema(title = "onlineId")
    private String onlineId;

    /** firstName */
    @Schema(title = "firstName")
    private String firstName;

    /** lastName */
    @Schema(title = "lastName")
    private String lastName;

    /** 是否会员 */
    @Schema(title = "isPsPlus")
    private Boolean isPsPlus;

    /** 是否正式验证？ */
    @Schema(title = "isOfficiallyVerified")
    private Boolean isOfficiallyVerified;

    /** 头像地址 */
    @Schema(title = "avatarUrl")
    private String avatarUrl;

    /** 自定义头像地址 */
    @Schema(title = "profilePicUrl")
    private String profilePicUrl;

    /** verifiedUserName */
    @Schema(title = "verifiedUserName")
    private String verifiedUserName;

    /** relationshipState */
    @Schema(title = "relationshipState")
    private String relationshipState;

}
