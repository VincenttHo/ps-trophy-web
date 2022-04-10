package com.vincent.api.trophy.controller;

import com.vincent.api.trophy.model.TrophySummaryDTO;
import com.vincent.api.trophy.model.TrophyTitleDTO;
import com.vincent.api.trophy.model.UserTrophyDetailDTO;
import com.vincent.api.trophy.service.TrophyService;
import com.vincent.external.trophy.model.TrophySummaryResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Api(value = "奖杯接口", tags = "奖杯接口")
@RestController
@RequestMapping("/trophy")
@Slf4j
public class TrophyController {

    @Autowired
    private TrophyService trophyService;

    @Operation(summary = "获取用户奖杯汇总信息")
    @GetMapping("/summary/{psnId}")
    public TrophySummaryDTO getTrophySummary(@PathVariable("psnId") String psnId) {
        return trophyService.getTrophySummary(psnId);
    }

    @Operation(summary = "清理用户奖杯列表")
    @GetMapping("/titles/{psnId}")
    public List<TrophyTitleDTO> getTrophyTitles(@PathVariable("psnId") String psnId,
                                                @RequestParam("gameName") String gameName,
                                                @RequestParam("currentPage") Integer currentPage,
                                                @RequestParam("pageSize") Integer pageSize) {
        return trophyService.getTrophyTitles(psnId, gameName, currentPage, pageSize);

    }

    @Operation(summary = "清理用户奖杯明细信息")
    @GetMapping("/detail/{psnId}/{gameId}")
    public UserTrophyDetailDTO getUserTrophyDetail(@PathVariable("psnId") String psnId,
                                                   @PathVariable("gameId") String gameId) {
        return trophyService.getUserTrophies(psnId, gameId);
    }

}
