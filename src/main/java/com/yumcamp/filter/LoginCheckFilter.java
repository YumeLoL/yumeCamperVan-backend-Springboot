package com.yumcamp.filter;

import com.alibaba.fastjson.JSON;
import com.yumcamp.common.BaseContext;
import com.yumcamp.common.R;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * Request Interceptor
 * Check if the user has already login
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    // Path matching unit
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        // get request URI
        String requestURI = request.getRequestURI();
        log.info("......The intercepted requests......：{}",requestURI);

        // define urls that can get pass directly
        String[] urls = new String[]{
                "/login",
                "/logout",
                "/signup",
                "/campervan/**",
                "/vanType/**",
                "/member/bookings/disabledDates/**"
        };

        // define a check method, compare requestURI and urls
        boolean check = check(urls, requestURI);

        // if check is true, go pass
        if(check){
            log.info("......The request URL: {} can go pass......",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        // if check is false
        // have to check if the user has already login
        log.info("....check the member id in session, member id is {}:",request.getSession().getAttribute("member"));
        HttpSession session = request.getSession(false);

        if(session.getAttribute("member") != null){
            log.info("......The user has already login，the id is：{}",session.getAttribute(
                    "member"));

            // get current logined member's id from session
            String currentLoginEmpId = (String) session.getAttribute("member");
            BaseContext.setCurrentId(currentLoginEmpId);

            response.setHeader("X-IsLoggedIn", String.valueOf(true));

            filterChain.doFilter(request,response);
            return;
        }


        log.info("......The member does not login......");
        // The user is not logged in, redirect to the login page
        // add configed Header, make sure R.error message sent to front-end
        response.setHeader("X-IsLoggedIn", String.valueOf(false));
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }


    /**
     * Path matching method
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);

            if(match){
                return true;
            }
        }
        return false;
    }

}



