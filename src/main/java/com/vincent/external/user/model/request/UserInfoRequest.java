package com.vincent.external.user.model.request;

import lombok.Builder;
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
@Builder
public class UserInfoRequest {

    /** psnId */
    private String psnId;

    public String buildRequestJson() {

        String result = null;

        String jsonTemplate = "{\n" +
        "    \"searchTerm\": \"%s\",\n" +
        "    \"domainRequests\": [\n" +
        "        {\n" +
        "            \"domain\": \"SocialAllAccounts\"\n" +
        "        }\n" +
        "    ]\n" +
        "}";

        if(!ObjectUtils.isEmpty(psnId)) {
            result = String.format(jsonTemplate, psnId);
        }

        return result;

    }

}
