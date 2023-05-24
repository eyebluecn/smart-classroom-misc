package com.smart.classroom.misc.controller.biz.index;


import com.smart.classroom.misc.controller.auth.Feature;
import com.smart.classroom.misc.controller.auth.FeatureType;
import com.smart.classroom.misc.controller.base.BaseController;
import com.smart.classroom.misc.controller.base.result.WebResult;
import com.smart.classroom.misc.utility.util.DateUtil;
import com.smart.classroom.subscription.facade.healthy.HealthyFacade;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 用于快速测试应用启动情况的Controller
 * 一般微服务是不直接提供Controller的。
 *
 * @author lishuang
 * @date 2023-04-14
 */
@RestController
public class IndexController extends BaseController {

    @DubboReference
    private HealthyFacade healthyFacade;

    @Feature({FeatureType.PUBLIC})
    @RequestMapping("/")
    public String index(@RequestParam(defaultValue = "DDD") String name) {

        return "Hello 你好," + name + " 现在时间：" + new Date();
    }


    @Feature({FeatureType.PUBLIC})
    @RequestMapping("/api/index/healthy")
    public WebResult<?> health() {
        String remoteValue = healthyFacade.check();
        String result = "服务健康, 现在时间：" + DateUtil.convertDateToString(new Date()) + " 远程调用结果：" + remoteValue;
        return success(result);
    }


}
