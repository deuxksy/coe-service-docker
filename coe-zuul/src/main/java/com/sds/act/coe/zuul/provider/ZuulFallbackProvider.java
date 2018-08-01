package com.sds.act.coe.zuul.provider;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.netflix.zuul.context.RequestContext;
import com.sds.act.coe.zuul.vo.ErrorResponseBodyVO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RefreshScope
@Component
public class ZuulFallbackProvider implements FallbackProvider {
    private static final String SERVICE_ID = "serviceId";
    private static final String REQUEST_URI = "requestURI";

    @Value("${hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds}")
    private String hystrixThreadTimeoutMilliseconds;


    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause instanceof HystrixTimeoutException) {
            return response(HttpStatus.GATEWAY_TIMEOUT, getInvalidParam(cause), getRootCauseMsg(cause));
        } else {
            return response(HttpStatus.SERVICE_UNAVAILABLE, getInvalidParam(cause), getRootCauseMsg(cause));
        }
    }

    private ClientHttpResponse response(HttpStatus status, String invalidParam, String rootCauseMsg) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() {
                return status;
            }

            @Override
            public int getRawStatusCode() {
                return status.value();
            }

            @Override
            public String getStatusText() {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() {

                HttpStatus status = getStatusCode();
                ErrorResponseBodyVO responseBodyVO;

                if (status == HttpStatus.GATEWAY_TIMEOUT) {
                    responseBodyVO = new ErrorResponseBodyVO("Error", "Failed to handle the request in given thread time. (" + rootCauseMsg + ")", invalidParam);
                } else if (status == HttpStatus.SERVICE_UNAVAILABLE) {
                    responseBodyVO = new ErrorResponseBodyVO("Error", "Service Unavailable. Please try after sometime. (" + rootCauseMsg + ")", invalidParam);
                } else {
                    responseBodyVO = new ErrorResponseBodyVO();
                }

                return new ByteArrayInputStream(responseBodyVO.toJSONString().getBytes());

            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    private String getInvalidParam(final Throwable cause) {
        if (cause instanceof HystrixTimeoutException) {
            return "hystrix....timeoutInMilliseconds: " + hystrixThreadTimeoutMilliseconds;
        } else {
            RequestContext context = RequestContext.getCurrentContext();
            String serviceId = context.get(SERVICE_ID).toString().toUpperCase();
            String requestURI = context.get(REQUEST_URI).toString();
            return serviceId + requestURI;
        }
    }

    private String getRootCauseMsg(final Throwable cause) {
        return ExceptionUtils.getRootCauseMessage(cause);
    }


}
