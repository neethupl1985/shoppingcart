package com.disco.shoppingCart.stats;

import com.disco.shoppingCart.utils.MdcUtil;
import com.disco.shoppingCart.utils.StatsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class StatsLoggingInterceptor implements HandlerInterceptor {
    public static final String DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object handler) {
        MdcUtil.clear();
        MdcUtil.put(StatsUtil.UTC_DATETIME.toString(),
                DateFormatUtils.formatUTC(System.currentTimeMillis(), DATE_TIME_FORMAT));
        log.info("{}", MdcUtil.toJson());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) {
        log.info("{}", MdcUtil.toJson());
        MdcUtil.clear();
    }
}
