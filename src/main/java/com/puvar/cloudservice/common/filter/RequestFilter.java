package com.puvar.cloudservice.common.filter;

import com.puvar.cloudcommon.common.constants.RequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 替换httpServletRequest为RequestWrapper。解决requestBody丢失参数的问题
 * @Auther: dingyuanmeng
 * @Date: 2019/9/24
 * @version : 1.0
 */
@Component
public class RequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
        filter.doFilter(new RequestWrapper(request),response);
    }
}
