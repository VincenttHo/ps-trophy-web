package com.vincent.external.user.api;

import com.vincent.external.auth.api.AuthApi;
import com.vincent.external.user.model.request.UserInfoRequest;
import com.vincent.external.user.model.response.info.UserInfoResponse;
import com.vincent.external.user.model.response.info.UserSearchResponse;
import com.vincent.external.user.model.response.profile.UserProfileResponse;
import com.vincent.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
@Slf4j
@Service
public class UserInfoApi {

    @Value("${psn.api.url.search-user:https://m.np.playstation.net/api/search/v1/universalSearch}")
    private String searchUserApiUrl;

    @Value("${psn.api.url.search-user:https://m.np.playstation.net/api/userProfile/v1/internal/users/%s/profiles}")
    private String userProfileApiUrl;

    @Autowired
    private AuthApi authApi;

    public UserInfoResponse getUserInfo(String psnId) {

        String requestJson = UserInfoRequest.builder().psnId(psnId).build().buildRequestJson();

        Map<String, String> headers = authApi.getTokenAndFillHeaders();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        log.info("获取用户信息，入参：{}", requestJson);

        UserSearchResponse userSearchResponse = HttpUtils.doPost(searchUserApiUrl, headers, requestJson, UserSearchResponse.class);
        log.info("获取用户信息，出参：{}", userSearchResponse.toString());
        UserInfoResponse result = null;
        if(userSearchResponse != null) {
            result = userSearchResponse.getDomainResponses().get(0).getResults().get(0).getSocialMetadata();
        }

        return result;

    }

    /**
     * <p>在这里说明当前方法的业务用途</p>
     * @author VincentHo
     * @date 2022/3/26  
     * @return com.vincent.external.user.model.response.profile.UserProfileResponse
     */
    public UserProfileResponse getUserProfile(String accountId) {

        log.info("开始获取用户简介信息...");

        String url = String.format(userProfileApiUrl, accountId);

        Map<String, String> headers = authApi.getTokenAndFillHeaders();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        UserProfileResponse userProfileResponse = HttpUtils.doGet(url, headers, UserProfileResponse.class);

        log.info("获取用户简介信息成功：{}", userProfileResponse);

        return userProfileResponse;

    }

}
