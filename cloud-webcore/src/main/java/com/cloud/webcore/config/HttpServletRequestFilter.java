package com.cloud.webcore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@Component
@WebFilter(filterName = "httpServletRequestFilter",urlPatterns = "/*")
public class HttpServletRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("自定义filter初始化.......................");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper=null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest)request);
        }
        if(requestWrapper==null) {
            chain.doFilter(request, response);
        }else {
//            log.info("------------------------------请求报文----------------------------------");
//            log.info(getParamsFromRequestBody((HttpServletRequest) requestWrapper));
//            log.info("------------------------------请求报文----------------------------------");
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {
        log.info("自定义filter销毁.......................");
    }

    /* *
     * 获取请求体内容
     * @return
     * @throws IOException
     */
    private String getParamsFromRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader br = null;
        String listString = "";
        try {
            br = request.getReader();
            String str = "";
            while ((str = br.readLine()) != null) {
                listString += str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listString;
    }

}
