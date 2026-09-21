package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
public class LoggingController {

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "")
    public void logging(HttpServletRequest request) {
        String requestIp = request.getRemoteAddr();
        log.trace("1: TRACE 로그 - {}", requestIp);
        log.debug("2: DEBUG 로그 - {}", requestIp);
        log.info ("3: INFO  로그 - {}", requestIp);
        log.warn ("4: WARN  로그 - {}", requestIp);
        log.error("5: ERROR 로그 - {}", requestIp);
    }
}
