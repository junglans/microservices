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
public class TiempoTranscurridoPostFilter extends ZuulFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TiempoTranscurridoPostFilter.class);
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		LOGGER.info("Entrando a post filter...");
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		Long tiempoInicio = (Long)request.getAttribute("tiempoInicio");
		Long tiempoFin = System.currentTimeMillis();
		
		Long tiempoTotal = tiempoFin - tiempoInicio;
		
		LOGGER.info(String.format("%s enrutado a %s. Tiempo total %f segundos", request.getMethod(), request.getRequestURL().toString(), (tiempoTotal.doubleValue())/1000));
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
