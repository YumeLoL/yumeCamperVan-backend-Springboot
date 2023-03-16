package com.yumcamp.filter;

import com.alibaba.fastjson.JSON;
import com.yumcamp.common.BaseContext;
import com.yumcamp.common.R;
import com.yumcamp.entity.Member;
import com.yumcamp.service.MemberService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    // Path matching unit
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Autowired
    private MemberService memberService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // get request URI
        String requestURI = request.getRequestURI();
        log.debug("Intercepted request: {}", requestURI);

        // define URLs that require authentication
        String[] secureUrls = new String[] { "/member/**" };

        // check if the current URL requires authentication
        boolean requiresAuthentication = requiresAuthentication(requestURI, secureUrls);

        // if the URL requires authentication
        if (requiresAuthentication) {
            // check if the user is authenticated
            Member authenticatedMember = getAuthenticatedMember(request);

            if (authenticatedMember != null) {
                // set the current user ID in the context
                BaseContext.setCurrentId(authenticatedMember.getMemberId());
                // continue with the filter chain
                filterChain.doFilter(request, response);
            }else {
                // user is not authenticated
                log.debug("User is not authenticated");
                // return an error object to the front-end
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            }

        }else {
            // URL does not require authentication
            log.debug("URL does not require authentication");
            // continue with the filter chain
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}


    /**
     * Checks if the given URL requires authentication.
     */
    private boolean requiresAuthentication(String requestURI, String[] secureUrls) {
        for (String secureUrl : secureUrls) {
            if (PATH_MATCHER.match(secureUrl, requestURI)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Returns the authenticated member if the user is authenticated, or null if not authenticated.
     */
    private Member getAuthenticatedMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Long userId = (Long) session.getAttribute("member"); // member id

            if (userId != null) {
                return memberService.getById(userId);
            }
        }
        return null;
    }
}
