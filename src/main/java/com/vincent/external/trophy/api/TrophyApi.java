package com.vincent.external.trophy.api;

import com.vincent.external.auth.api.AuthApi;
import com.vincent.external.trophy.model.TrophyListResponse;
import com.vincent.external.trophy.model.TrophySummaryResponse;
import com.vincent.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

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
@Service
public class TrophyApi {

    @Value("${psn.api.url.trophy-summary:https://m.np.playstation.net/api/trophy/v1/users/%s/trophySummary}")
    private String trophySummaryApiUrl;

    @Value("${psn.api.url.trophy-list:https://m.np.playstation.net/api/trophy/v1/users/%s/trophyTitles}")
    private String trophyListApiUrl;

    @Autowired
    private AuthApi authApi;

    /**
     * <p>获取奖杯统计</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param accountId
     * @return com.vincent.external.trophy.model.TrophySummaryResponse
     */
    public TrophySummaryResponse getTrophySummary(String accountId) {

        Map<String, String> headers = authApi.getTokenAndFillHeaders();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        String url = String.format(trophySummaryApiUrl, accountId);

        TrophySummaryResponse trophySummaryResponse = HttpUtils.doGet(url, headers, TrophySummaryResponse.class);

        return trophySummaryResponse;

    }

    /**
     * <p>获取奖杯列表</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param accountId
     * @return TrophyListResponse
     */
    public TrophyListResponse getTrophyList(String accountId) {

        Map<String, String> headers = authApi.getTokenAndFillHeaders();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        String url = String.format(trophyListApiUrl, accountId);

        return HttpUtils.doGet(url, headers, TrophyListResponse.class);

    }

}
