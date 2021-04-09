package cn.caohongliang.gray.payment.api.web;

import cn.caohongliang.gray.payment.api.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/payment")
@FeignClient(name = "payment", contextId = "paymentApi")
public interface PaymentApi {

	@GetMapping("/order/{orderId}")
	PaymentDTO getPaymentByOrderId(@PathVariable("orderId") Long orderId);
}
