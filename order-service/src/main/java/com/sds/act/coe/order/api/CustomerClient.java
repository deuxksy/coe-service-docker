package com.sds.act.coe.order.api;

import com.sds.act.coe.order.domain.Customer;
import com.sds.act.coe.order.web.rest.ApiConstants;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RefreshScope
@FeignClient(
        name = "${coe.application.customer-service}",
        decode404 = true
)
public interface CustomerClient {
    @RequestMapping(method = RequestMethod.GET, value = ApiConstants.API_V1_BASE_PATH + "/customers")
    List<Customer> findAll();
}
