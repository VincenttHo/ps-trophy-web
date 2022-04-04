package com.vincent.api.user.service;

import com.vincent.api.user.dao.UserDao;
import com.vincent.api.user.dao.UserProfileDao;
import com.vincent.api.user.model.UserInfoDTO;
import com.vincent.api.user.model.UserProfileDTO;
import com.vincent.external.user.api.UserInfoApi;
import com.vincent.external.user.model.response.info.UserInfoResponse;
import com.vincent.external.user.model.response.profile.UserProfileResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

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
@Service
public class UserService {

    @Autowired
    private UserInfoApi userInfoApi;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    /**
     * <p>获取用户信息</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param psnId
     * @return com.vincent.api.user.model.UserInfoDTO
     */
    public UserInfoDTO queryUserInfo(String psnId) {

        return userDao.findByOnlineId(psnId).orElseGet(() -> getUserInfoByApi(psnId));

    }

    /**
     * <p>通过api获取用户信息</p>
     * @author VincentHo
     * @date 2022/4/4
     * @param psnId
     * @return com.vincent.api.user.model.UserInfoDTO
     */
    private UserInfoDTO getUserInfoByApi(String psnId) {
        UserInfoResponse userInfoResponse = userInfoApi.getUserInfo(psnId);

        UserInfoDTO result = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoResponse, result);

        userDao.save(result);
        return result;
    }

    /**
     * <p>查询用户简介信息</p>
     * @author VincentHo
     * @date 2022/4/4
     * @param accountId
     * @return com.vincent.api.user.model.UserProfileDTO
     */
    public UserProfileDTO queryUserProfile(String accountId) {

        return userProfileDao.findById(accountId).orElseGet(() -> this.getUserProfileByApi(accountId));

    }

    /**
     * <p>通过api获取用户简介信息</p>
     * @author VincentHo
     * @date 2022/4/4
     * @param accountId
     * @return com.vincent.api.user.model.UserProfileDTO
     */
    private UserProfileDTO getUserProfileByApi(String accountId) {
        UserProfileResponse userProfileResponse = userInfoApi.getUserProfile(accountId);
        UserProfileDTO result = new UserProfileDTO();
        result.setAccountId(accountId);
        BeanUtils.copyProperties(userProfileResponse, result);
        userProfileDao.save(result);
        return result;
    }

}
