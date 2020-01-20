package com.curso.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
@Component
public class RouteFilter extends ZuulFilter{

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteFilter.class);
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.ROUTE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
