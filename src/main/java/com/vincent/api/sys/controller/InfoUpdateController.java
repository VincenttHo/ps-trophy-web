package com.vincent.api.sys.controller;

import com.vincent.api.sys.service.InfoUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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
@RestController
@RequestMapping("/infos/update")
public class InfoUpdateController {

    @Autowired
    private InfoUpdateService infoUpdateService;

    @PostMapping("/all/{psnId}")
    public void updateAllInfo(@PathVariable("psnId") String psnId) {
        infoUpdateService.updateAllInfo(psnId);
    }

    @PostMapping("/userInfo/{psnId}")
    public void updateUserInfo(@PathVariable("psnId") String psnId) {
        infoUpdateService.updateUserInfo(psnId);
    }

    @PostMapping("/trophyInfo/{psnId}")
    public void updateTrophyInfo(@PathVariable("psnId") String psnId) {
        infoUpdateService.updateTrophyInfo(psnId);
    }

}
