package com.vincent.external.auth.api;

import com.vincent.external.auth.model.Token;
import com.vincent.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.vip.vjtools.vjkit.time.DateUtil;
import org.springframework.util.ObjectUtils;

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
public class AuthApi {

    @Value("${psn.api.auth.url.auth-code}")
    private String authCodeApiUrl;

    @Value("${psn.api.auth.url.token}")
    private String tokenApiUrl;

    @Value("${psn.api.auth.npsso}")
    private String npsso;

    /**
     * <p>获取token</p>
     * @author VincentHo
     * @date 2022/3/26
     * @return java.lang.String
     */
    public String getToken() {

        log.info("开始获取token...");

        String token = null;

        try {

            // 先从缓存获取token，过期再请求接口
            token = getTokenFromCache();

            if(ObjectUtils.isEmpty(token)) {
                String authCode = getAuthCode();

                token = callTokenApi(authCode);
                Token.accessToken = token;
                Token.tokenExpiryTime = DateUtil.addMinutes(new Date(), 50);
            }

            log.info("获取到token：{}", token);

        } catch (Exception e) {
            log.error("授权失败", e);
            throw new RuntimeException("授权失败，请刷新重试！", e);
        }

        return token;
    }

    /**
     * <p>获取token并返回header</p>
     * @author VincentHo
     * @date 2022/3/26
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String, String> getTokenAndFillHeaders() {
        String token = getToken();
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "Bearer " + token);
        return headers;
    }

    /**
     * <p>通过缓存获取token</p>
     * @author VincentHo
     * @date 2022/3/26
     * @return java.lang.String
     */
    private String getTokenFromCache() {

        String accessToken = Token.accessToken;
        Date tokenExpiryTime = Token.tokenExpiryTime;

        if(tokenExpiryTime != null) {

            long expiryTimeStamp = tokenExpiryTime.getTime();
            long nowTimeStamp = new Date().getTime();
            boolean isExpiry = nowTimeStamp >= expiryTimeStamp;

            if(!isExpiry) {
                return accessToken;
            }

        }

        return null;
    }

    /**
     * <p>调用token获取接口</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param authCode
     * @return java.lang.String
     */
    private String callTokenApi(String authCode) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic YWM4ZDE2MWEtZDk2Ni00NzI4LWIwZWEtZmZlYzIyZjY5ZWRjOkRFaXhFcVhYQ2RYZHdqMHY=");

        Map<String, String> params = new HashMap<>();
        params.put("redirect_uri", "com.playstation.PlayStationApp://redirect");
        params.put("grant_type", "authorization_code");
        params.put("token_format", "jwt");
        params.put("code", authCode);

        Map<String, Object> resultMap = HttpUtils.postUrlencoded(tokenApiUrl, headers, params);

        return (String) resultMap.get("access_token");

    }

    /**
     * <p>获取授权code</p>
     * @author VincentHo
     * @date 2022/3/26
     * @return java.lang.String
     */
    private String getAuthCode() {

        log.info("开始获取授权码...");

        Map<String, String > headers = new HashMap<>();
        headers.put("Cookie", "npsso=" + npsso);

        Map<String, String> responseHeaders = HttpUtils.connectAndGetHeaders(authCodeApiUrl, headers);

        String authCode = filterCodeByResponseHeader(responseHeaders);

        log.info("获取到授权码：{}", authCode);

        return authCode;

    }

    /**
     * <p>通过response header过滤出code</p>
     * code的值放在response header的Location里面
     * 示例：Location：com.playstation.PlayStationApp://redirect/?code=v3.eg9Q9d&cid=ee4733ee-8388-4378-9217-71786db09731
     * 问号后面的参数code即是我们所需要的code
     * @author VincentHo
     * @date 2022/3/26
     * @param responseHeaders
     * @return java.lang.String
     */
    private String filterCodeByResponseHeader(Map<String, String> responseHeaders) {
        String locationValue = responseHeaders.get("Location");
        String[] locationSplits = locationValue.split("code=");
        String[] codeSplits = locationSplits[1].split("&");
        String code = codeSplits[0];
        return code;
    }

}
