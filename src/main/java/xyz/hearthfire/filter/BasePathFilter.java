package xyz.hearthfire.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import xyz.hearthfire.utils.BaseUtil;

import javax.servlet.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by fz on 2016/1/16.
 */
public class BasePathFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(BasePathFilter.class);

    private String basePath = "";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("spring.properties");
            basePath = properties.getProperty("basePath");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(BaseUtil.getExceptionStackTrace(e));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setAttribute("basePath", this.basePath);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
