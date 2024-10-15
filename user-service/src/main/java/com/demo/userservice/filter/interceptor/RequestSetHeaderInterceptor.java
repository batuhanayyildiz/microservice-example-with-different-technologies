package com.demo.userservice.filter.interceptor;

import jakarta.annotation.Nullable;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static com.demo.userservice.constants.LogConstants.X_TRACE_ID;

public class RequestSetHeaderInterceptor  implements ClientHttpRequestInterceptor {
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution requestExecution) throws IOException {
        request.getHeaders().add(X_TRACE_ID, MDC.get(X_TRACE_ID));
        return requestExecution.execute(request,body);
    }
}
