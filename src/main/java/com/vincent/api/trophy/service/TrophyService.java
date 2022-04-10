package com.vincent.api.trophy.service;

import com.vincent.api.trophy.dao.TrophySummaryDao;
import com.vincent.api.trophy.dao.TrophyTitleDao;
import com.vincent.api.trophy.model.TrophySummaryDTO;
import com.vincent.api.trophy.model.TrophyTitleDTO;
import com.vincent.api.user.model.UserInfoDTO;
import com.vincent.api.user.service.UserService;
import com.vincent.external.trophy.api.TrophyApi;
import com.vincent.external.trophy.model.TrophyListResponse;
import com.vincent.external.trophy.model.TrophySummaryResponse;
import com.vincent.external.trophy.model.TrophyTitleResponse;
import org.bson.codecs.UuidCodec;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
public class TrophyService {

    @Autowired
    private TrophyApi trophyApi;

    @Autowired
    private TrophySummaryDao trophySummaryDao;

    @Autowired
    private TrophyTitleDao trophyTitleDao;

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * <p>获取奖杯汇总信息</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param psnId
     * @return com.vincent.api.trophy.model.TrophySummaryDTO
     */
    public TrophySummaryDTO getTrophySummary(String psnId) {

        return trophySummaryDao.findById(psnId).orElse(null);

    }

    /**
     * <p>获取奖杯列表</p>
     * @author VincentHo
     * @date 2022/4/4
     * @param psnId
     * @return java.util.List<com.vincent.api.trophy.model.TrophyTitleDTO>
     */
    public List<TrophyTitleDTO> getTrophyTitles(String psnId, String gameName, Integer currentPage, Integer pageSize) {

        Criteria criteria = new Criteria();
        Criteria isPsnId = Criteria.where("psnId").is(psnId);
        if(ObjectUtils.isEmpty(gameName)) {
            criteria.andOperator(isPsnId);
        } else {
            Criteria likeGameName = Criteria.where("trophyTitleName").regex(gameName);
            criteria.andOperator(isPsnId, likeGameName);
        }
        Query query = new Query();
        query.addCriteria(criteria);
        query.skip((currentPage - 1) * pageSize);
        query.limit(pageSize);


        return mongoTemplate.find(query, TrophyTitleDTO.class);

    }

    /**
     * <p>更新奖杯汇总信息</p>
     * @author VincentHo
     * @date 2022/4/4
     * @param psnId
     * @return com.vincent.api.trophy.model.TrophySummaryDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public TrophySummaryDTO updateTrophySummary(String psnId) {

        UserInfoDTO userInfo = userService.queryUserInfo(psnId);
        String accountId = userInfo.getAccountId();

        TrophySummaryResponse trophySummary = trophyApi.getTrophySummary(accountId);

        TrophySummaryDTO result = new TrophySummaryDTO();
        result.setPsnId(psnId);
        result.setAccountId(accountId);
        BeanUtils.copyProperties(trophySummary, result);
        BeanUtils.copyProperties(trophySummary.getEarnedTrophies(), result);

        trophySummaryDao.save(result);

        return result;

    }

    /**
     * <p>更新奖杯列表信息</p>
     * @author VincentHo
     * @date 2022/4/4
     * @param psnId
     * @return com.vincent.api.trophy.model.TrophySummaryDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public List<TrophyTitleDTO> updateTrophyTitles(String psnId) {

        UserInfoDTO userInfo = userService.queryUserInfo(psnId);
        String accountId = userInfo.getAccountId();

        TrophyListResponse trophyListResponse = trophyApi.getTrophyList(accountId);
        List<TrophyTitleDTO> result = null;
        if(trophyListResponse != null) {
            List<TrophyTitleResponse> trophyTitleResponses = trophyListResponse.getTrophyTitles();
            if(!CollectionUtils.isEmpty(trophyTitleResponses)) {
                result = trophyTitleResponses.stream().map(trophyTitleResponse -> {
                    TrophyTitleDTO trophyTitle = new TrophyTitleDTO();
                    BeanUtils.copyProperties(trophyTitleResponse, trophyTitle);
                    trophyTitle.setAccountId(accountId);
                    trophyTitle.setPsnId(psnId);
                    trophyTitle.setId(trophyTitle.getPsnId() + "-" + trophyTitle.getNpCommunicationId());
                    return trophyTitle;
                }).collect(Collectors.toList());
                trophyTitleDao.saveAll(result);
            }
        }
        return result;

    }

}
