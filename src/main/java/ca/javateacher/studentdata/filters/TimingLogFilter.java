package ca.javateacher.studentdata.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(20)
public class TimingLogFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(TimingLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.trace("init() is called");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        logger.trace("doFilter() is called");
        long start = System.nanoTime();
        filterChain.doFilter(servletRequest, servletResponse);
        long end = System.nanoTime();
        long time = (end - start)/1000; // time taken to process in microseconds
        String uri = ((HttpServletRequest)servletRequest).getRequestURI();
        logger.info("time taken to process request for " + uri +
                " is "  + time + " microseconds");
    }

    @Override
    public void destroy() {
        logger.trace("destroy() is called");
    }
}
