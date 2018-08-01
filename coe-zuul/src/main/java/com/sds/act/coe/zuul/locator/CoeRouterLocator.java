package com.sds.act.coe.zuul.locator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

public class CoeRouterLocator extends SimpleRouteLocator {
    private static Logger logger = LoggerFactory.getLogger(CoeRouterLocator.class);

    public CoeRouterLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
    }

    @Override
    protected ZuulProperties.ZuulRoute getZuulRoute(String adjustedPath) {

        ZuulProperties.ZuulRoute zuulRoute = super.getZuulRoute(adjustedPath);

        logger.info("#####" + zuulRoute.toString());

        return zuulRoute;
    }
}
