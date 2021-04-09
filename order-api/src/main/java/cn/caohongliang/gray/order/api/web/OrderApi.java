package cn.caohongliang.gray.order.api.web;

import cn.caohongliang.gray.order.api.dto.OrderDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/order")
@FeignClient(name = "order", contextId = "orderApi")
public interface OrderApi {

	@GetMapping("/detail/{orderId}")
	OrderDetailDTO getOrderDetail(@PathVariable("orderId") Long orderId);
}
