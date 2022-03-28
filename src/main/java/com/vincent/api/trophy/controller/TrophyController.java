package com.vincent.api.trophy.controller;

import com.vincent.api.trophy.model.TrophySummaryDTO;
import com.vincent.api.trophy.service.TrophyService;
import com.vincent.external.trophy.model.TrophySummaryResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Tag(name = "奖杯接口")
@RestController
@RequestMapping("/trophy")
@Slf4j
public class TrophyController {

    @Autowired
    private TrophyService trophyService;

    @GetMapping("/summary/{accountId}")
    public TrophySummaryDTO getTrophySummary(@PathVariable("accountId") String accountId) {

        return trophyService.getTrophySummary(accountId);

    }

}
