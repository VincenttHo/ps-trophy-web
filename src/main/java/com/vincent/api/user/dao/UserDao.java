package com.vincent.api.user.dao;

import com.vincent.api.user.model.UserInfoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

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
 * 2022-03-28    VincentHo       v1.0.0        create
 * @date 2022-03-28
 */
public interface UserDao extends MongoRepository<UserInfoDTO, String> {
}
