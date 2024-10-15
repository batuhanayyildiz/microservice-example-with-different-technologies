package com.demo.userservice.service;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.demo.userservice.constants.LogConstants.X_TRACE_ID;

@Service
public class TraceIdGeneratorService {

    public void generateTraceId(HttpServletRequest request){
        String traceId= UUID.randomUUID().toString();
        MDC.put(X_TRACE_ID,traceId);
    }

}
