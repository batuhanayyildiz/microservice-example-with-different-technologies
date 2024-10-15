package com.demo.userservice.model.log;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestWrapper extends HttpServletRequestWrapper {
    @Getter
    private String body;
    private HttpServletRequest request;


    public RequestWrapper(HttpServletRequest request){
        super(request);
        this.body="";
        try(BufferedReader bufferedReader=request.getReader()){
            String line;
            while((line=bufferedReader.readLine()) != null)
                this.body+=line;

        }
        catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public Map<String,String> getAllHeaders(){
        Map<String, String> headers = new HashMap<>();
        Collections.list(getHeaderNames()).forEach(headerName-> headers.put(headerName,getHeader(headerName)));
        return headers;

    }

    @Override
    public ServletInputStream getInputStream(){
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException
    {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }


}
