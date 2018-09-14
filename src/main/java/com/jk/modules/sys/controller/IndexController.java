package com.jk.modules.sys.controller;

import com.feilong.core.DatePattern;
import com.github.pagehelper.PageInfo;
import com.jk.common.base.controller.BaseController;
import com.jk.common.util.ShiroUtils;
import com.jk.modules.log.model.Log;
import com.jk.modules.log.service.LogService;
import com.jk.modules.oti.model.OtiConfig;
import com.jk.modules.oti.service.OtiConfigService;
import com.jk.modules.oti.service.OtiFieldLibraryService;
import com.jk.modules.sys.model.Permission;
import com.jk.modules.sys.model.User;
import com.jk.modules.sys.service.PermissionService;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.RandomUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 首页
 * Created by fengj on 2017/1/22.
 */
@Controller
public class IndexController extends BaseController{

    private static final String BASE_PATH = "admin/";

    @Resource
    private PermissionService permissionService;
    @Resource
    private LogService logService;
    //demo 消息ID
    private static String MSG_ID_DEMO = "demo_msg";

    @Resource
    private OtiFieldLibraryService otiFieldLibraryService;
    @Resource
    private OtiConfigService otiConfigService;

    private static volatile String s_msgId;

    /*
     * @methodName: toIndex
     * @param: []
     * @description: 定义welcome-file-list页面
     * @return: java.lang.String
     * @author: cuiP
     * @date: 2017/8/5 18:48
     * @version: V1.0.0
     */
    @GetMapping(value = "")
    public String toIndex(){
        return "redirect:/admin/index";
    }

    /**
     * 首页
     * @return
     */
    @GetMapping(value = "/admin/index")
    public String list(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            String username, String startTime, String endTime, ModelMap modelMap) {
        try {
            log.debug("分页查询日志列表参数! pageNum = {}, username = {}, username = {}, startTime = {}, endTime = {}", pageNum, username, startTime, endTime);
            PageInfo<Log> pageInfo = logService.findPage(pageNum, PAGESIZE, username, startTime, endTime);
            log.info("分页查询日志列表结果！ pageInfo = {}", pageInfo);
            modelMap.put("pageInfo", pageInfo);
            modelMap.put("username", username);
            modelMap.put("startTime", startTime);
            modelMap.put("endTime", endTime);
        } catch (Exception e) {
            log.error("分页查询日志列表失败! e = {}", e);
        }
        return BASE_PATH + "log/log-list";
    }


    /**
     * 欢迎页
     * @return
     */
    @GetMapping(value = "/admin/welcome")
    public String welcome(ModelMap modelMap){
        log.info("------进入欢迎页-------");
        int len = 20;
        String[] dsArray = new String[len];
        Integer[] num1 = new Integer[len];
        Integer[] num2 = new Integer[len];
        Integer[] num3 = new Integer[len];
        Integer[] num4 = new Integer[len];
        Integer[] num5 = new Integer[len];

        for(int i = 0; i < len; i++){
            dsArray[i] = "'" + DateUtil.format(DateUtil.offsiteSecond(DateUtil.date(), i), DatePattern.COMMON_TIME) + "'";
            num1[i] = RandomUtil.randomInt(30) + 50 + 5;
            num2[i] = RandomUtil.randomInt(30) + 50 + 10;
            num3[i] = RandomUtil.randomInt(30) + 50 + 15;
            num4[i] = RandomUtil.randomInt(30) + 50 + 20;
            num5[i] = RandomUtil.randomInt(30) + 50 + 25;
        }
        String arrX = Arrays.toString(dsArray);

        String nArr1 = Arrays.toString(num1);
        String nArr2 = Arrays.toString(num2);
        String nArr3 = Arrays.toString(num3);
        String nArr4 = Arrays.toString(num4);
        String nArr5 = Arrays.toString(num5);

        modelMap.put("nameArr", "['邮件营销', '百度信用', '实时计算', '直接访问',   '搜索引擎']");

        modelMap.put("arrX", arrX);

        modelMap.put("nArr1", nArr1);
        modelMap.put("nArr2", nArr2);
        modelMap.put("nArr3", nArr3);
        modelMap.put("nArr4", nArr4);
        modelMap.put("nArr5", nArr5);

        return "index";
    }

    /**
     * 未授权页面
     * @return
     */
    @GetMapping(value = "/admin/403")
    public String unauthorized(){
        log.info("------没有权限-------");
        return BASE_PATH+"common/403";
    }
}
