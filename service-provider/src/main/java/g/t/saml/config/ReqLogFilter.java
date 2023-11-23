package g.t.saml.config;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ReqLogFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            Map<String, String> req = new HashMap<>();

            req.put("req.remoteHost", request.getRemoteHost());

            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                req.put("req.requestURI", httpServletRequest.getRequestURI());
                StringBuffer requestURL = httpServletRequest.getRequestURL();
                if (requestURL != null) {
                    req.put("req.requestURL", requestURL.toString());
                }
                req.put("req.method", httpServletRequest.getMethod());
                req.put("req.req.queryString", httpServletRequest.getQueryString());
                req.put("req.userAgent", httpServletRequest.getHeader("User-Agent"));
                req.put("req.xForwardedFor", httpServletRequest.getHeader("X-Forwarded-For"));
                req.put("req.x-b3-spanid", httpServletRequest.getHeader("x-b3-spanid"));
                req.put("req.x-b3-traceid", httpServletRequest.getHeader("x-b3-traceid"));
            }

            log.info("Received request {} ", req);
        } finally {
            chain.doFilter(request, response);
        }

    }

}