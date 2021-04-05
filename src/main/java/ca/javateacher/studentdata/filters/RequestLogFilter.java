package ca.javateacher.studentdata.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestLogFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.trace("init() is called");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        logger.trace("doFilter() is called");
        String requestUri = ((HttpServletRequest)servletRequest).getRequestURI();
        String remoteHost = servletRequest.getRemoteHost();
        logger.info("request for " + requestUri + " from " + remoteHost);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.trace("destroy() is called");
    }
}
