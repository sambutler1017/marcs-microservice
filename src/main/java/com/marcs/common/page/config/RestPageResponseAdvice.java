package com.marcs.common.page.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.marcs.common.mapping.DefaultMapper;
import com.marcs.common.page.Page;

/**
 * Page Reponse Advice for mapping and formatting page responses.
 * 
 * @author Sam Butler
 * @since January 22, 2023s
 */
@SuppressWarnings("all")
@RestControllerAdvice
public class RestPageResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (returnType.getParameterType().getSimpleName().equals("Page")) {
            Page p = DefaultMapper.objectMapper().convertValue(body, Page.class);
            response.getHeaders().add("total-count", String.valueOf(p.getTotalCount()));
            return p.getList();
        }

        return body;
    }
}