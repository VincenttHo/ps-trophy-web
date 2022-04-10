package com.vincent.api.trophy.dao;

import com.vincent.api.trophy.model.TrophySummaryDTO;
import com.vincent.api.trophy.model.TrophyTitleDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
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
 * 2022-04-04    VincentHo       v1.0.0        create
 * @date 2022-04-04
 */
public interface TrophyTitleDao extends MongoRepository<TrophyTitleDTO, String> {

    Optional<List<TrophyTitleDTO>> findByPsnId(String psnId);

}
