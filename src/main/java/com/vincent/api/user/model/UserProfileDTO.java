package com.vincent.api.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * <p>用户简介信息</p>
 *
 * @author VincentHo
 * @version 1.0.0
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2022-04-04    VincentHo       v1.0.0        create
 * @date 2022-04-04
 */
@Data
@Document("userProfile")
@Schema(title = "用户简介", description = "用户简介")
public class UserProfileDTO {

    /** 账号id */
    @Id
    private String accountId;

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
