package com.disco.shoppingcart.stats;

import com.disco.shoppingcart.utils.MdcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class StatsLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object handler) {
        MdcUtil.clear();
        //logging from where the request came
        MdcUtil.put("REMOTE CLIENT ADDRESS", httpServletRequest.getRemoteAddr());
        MdcUtil.put("REQUEST ARRIVAL TIME", String.valueOf(System.currentTimeMillis()));
        log.info("{}", MdcUtil.toJson());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) {
        log.info("{}", MdcUtil.toJson());
        MdcUtil.put("REQUEST COMPLETION TIME", String.valueOf(System.currentTimeMillis()));
        MdcUtil.clear();
    }
}
