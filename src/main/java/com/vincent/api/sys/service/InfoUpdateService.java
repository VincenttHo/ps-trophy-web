package com.vincent.api.sys.service;

import com.vincent.api.trophy.service.TrophyService;
import com.vincent.api.user.dao.UserDao;
import com.vincent.api.user.dao.UserProfileDao;
import com.vincent.api.user.model.UserInfoDTO;
import com.vincent.api.user.model.UserProfileDTO;
import com.vincent.api.user.service.UserService;
import com.vincent.external.user.api.UserApi;
import com.vincent.external.user.model.response.info.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import sun.plugin.util.UserProfile;

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
@Service
public class InfoUpdateService {

    @Autowired
    private UserService userService;

    @Autowired
    private TrophyService trophyService;

    @Transactional(rollbackFor = Exception.class)
    public void updateAllInfo(String psnId) {

        this.updateUserInfo(psnId);

        this.updateTrophyInfo(psnId);

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(String psnId) {

        userService.updateUserInfo(psnId);

        userService.updateUserProfile(psnId);

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTrophyInfo(String psnId) {

        trophyService.updateTrophySummary(psnId);

        trophyService.updateTrophyTitles(psnId);

        trophyService.removeUserTrophy(psnId);

    }

}
