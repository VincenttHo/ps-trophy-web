package com.vincent.api.user.service;

import com.vincent.api.user.dao.UserDao;
import com.vincent.api.user.model.UserInfoDTO;
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

    /**
     * <p>获取用户信息</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param psnId
     * @return com.vincent.api.user.model.UserInfoDTO
     */
    public UserInfoDTO queryUserInfo(@PathVariable("psnId") String psnId) {

        // TODO 优先数据库获取
        Optional<UserInfoDTO> userInfoOptional = userDao.findById(psnId);
        UserInfoDTO result = null;
        if(userInfoOptional.isPresent()) {
            result = userInfoOptional.get();
        }

        if(result == null) {
            UserInfoResponse userInfoResponse = userInfoApi.getUserInfo(psnId);
            UserProfileResponse userProfileResponse = userInfoApi.getUserProfile(userInfoResponse.getAccountId());

            result = new UserInfoDTO();
            BeanUtils.copyProperties(userProfileResponse, result);
            BeanUtils.copyProperties(userInfoResponse, result);

            userDao.save(result);

        }

        return result;
    }

}
