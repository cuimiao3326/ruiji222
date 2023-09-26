package org.example.ruiji.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.ruiji.common.BaseContext;
import org.example.ruiji.common.R;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取请求信息
        String requestURI = request.getRequestURI();
        log.info("接收到的请求的URL为:{}",requestURI);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };
        //判断本次请求是否需要处理
        boolean check = check(requestURI,urls);
        //如果不需要处理，则直接放行
        if(check){
            log.info("本次请求不需要处理");
            filterChain.doFilter(request,response);
            return;
        }
        //判断登录状态，如果已经登录，则直接放行

        if(request.getSession().getAttribute("employee")!=null){
            log.info("已经登录");

            long id = Thread.currentThread().getId();
            log.info("Thread:{}",id);

            Long employee = (Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(employee);
            log.info("用户session信息:{}",employee);



            filterChain.doFilter(request,response);
            return;
        }
        //如果未登录则返回未登录的结果，通过输出流的方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("用户未登录");



    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param requestURL
     * @return
     */
    public boolean check(String requestURL,String[] urls){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if(match){
                return true;
            }
        }
        return false;
    }
}
