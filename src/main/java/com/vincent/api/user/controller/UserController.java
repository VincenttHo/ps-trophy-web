package com.vincent.api.user.controller;

import com.vincent.api.user.model.*;
import com.vincent.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Tag(name = "用户接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{psnId}")
    @Operation(summary = "查询用户信息")
    public UserInfoDTO queryUserInfo(@PathVariable("psnId") String psnId) {
        return userService.queryUserInfo(psnId);
    }

    @GetMapping(value = "/profile/{psnId}")
    @Operation(summary = "查询用户简介信息")
    public UserProfileDTO queryUserProfile(@PathVariable("psnId") String psnId) {
        return userService.queryUserProfile(psnId);
    }

}

