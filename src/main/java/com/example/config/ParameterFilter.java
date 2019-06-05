package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Tengfei.Wang
 * @Description;
 * @Date: Created in 下午5:56 19/6/18
 * @Modified by:
 */
public class ParameterFilter extends OncePerRequestFilter {

    private Logger log = LoggerFactory.getLogger(ParameterFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = httpServletRequest.getServletPath();
        log.info("request uri:" + servletPath);

    }


    @Override
    public void destroy() {

    }



}
