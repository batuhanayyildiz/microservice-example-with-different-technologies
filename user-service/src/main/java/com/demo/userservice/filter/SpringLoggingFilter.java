package com.demo.userservice.filter;


import com.demo.userservice.model.log.RequestWrapper;
import com.demo.userservice.model.log.ResponseWrapper;
import com.demo.userservice.service.TraceIdGeneratorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.demo.userservice.constants.LogConstants.X_TRACE_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringLoggingFilter extends OncePerRequestFilter {
    private final TraceIdGeneratorService traceIdGeneratorService;

    @Value("${spring.application.name}")
    private String appName;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        RequestWrapper requestWrapper = new RequestWrapper(request);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);


        traceIdGeneratorService.generateTraceId(request);
        setResponseHeader(responseWrapper);

        log.info(requestLogAsFormatString(requestWrapper));
        filterChain.doFilter(requestWrapper, responseWrapper);
        log.info(responseLogAsFormatString(responseWrapper));

    }

    private String responseLogAsFormatString(ResponseWrapper response) {
        return String.format("Response status: %s, Response Headers: %s, Response TraceId: %s, Response Body, %s",
                response.getStatus(),
                response.getAllHeaders(),
                response.getHeader(X_TRACE_ID),
                IOUtils.toString(response.getCopyBody(),response.getCharacterEncoding()));

    }
    private String requestLogAsFormatString(RequestWrapper request){

        return String.format("## %s ## Application name: %s, Request method: %s, Request Uri: %s, Request Headers: %s, Request Body: %s",
                request.getServerName(),
                appName,
                request.getMethod(),
                request.getRequestURI(),
                request.getAllHeaders(),
                request.getBody());
    }

    private void setResponseHeader(HttpServletResponse response) {
        response.addHeader(X_TRACE_ID, MDC.get(X_TRACE_ID));
    }



}
