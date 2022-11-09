package com.notificationservice.web;

import com.notificationservice.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationSenderController {
    @Resource
    private NotificationService notificationService;

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public String pushNotification(HttpServletRequest request) {
        String serviceToken = request.getParameter("serviceToken");
        String message = request.getParameter("message");
        notificationService.send(serviceToken, message);
        return "push";
    }
}
