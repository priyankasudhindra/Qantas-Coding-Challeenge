package com.notificationservice.web;

import com.notificationservice.domain.NotificationToken;
import com.notificationservice.service.NotificationTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller(value = "notificationRegisteringController")
public class NotificationRegisteringController {
    @Resource
    private NotificationTokenService notificationTokenService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register(NotificationToken token) {
        notificationTokenService.register(token);
    }

    @RequestMapping(value = "/tokens", method = RequestMethod.GET)
    public
    @ResponseBody
    List<NotificationToken> getNotificationTokens() {
        return notificationTokenService.get();
    }

    @RequestMapping(value = "/tokens/{appKey}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<NotificationToken> getNotificationTokensBy(@PathVariable String appKey) {
        return notificationTokenService.getBy(appKey);
    }

    @RequestMapping(value = "/host", method = RequestMethod.GET)
    public
    @ResponseBody
    String getHost() {
        return System.getenv("VCAP_APP_HOST");
    }

    @RequestMapping(value = "/port", method = RequestMethod.GET)
    public
    @ResponseBody
    String getPort() {
        return System.getenv("VCAP_APP_PORT");
    }
}
