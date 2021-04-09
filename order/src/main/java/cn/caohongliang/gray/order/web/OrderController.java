package cn.caohongliang.gray.order.web;

import cn.caohongliang.gray.order.api.dto.OrderDetailDTO;
import cn.caohongliang.gray.order.api.web.OrderApi;
import cn.caohongliang.gray.payment.api.dto.PaymentDTO;
import cn.caohongliang.gray.payment.api.web.PaymentApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class OrderController implements OrderApi {

	@Resource
	private PaymentApi paymentApi;
	//	@Resource
//	private RestTemplate restTemplate;
	@Value("${environment.name}")
	private String environmentName;
	@Value("${environment.version}")
	private String environmentVersion;

	@Override
	public OrderDetailDTO getOrderDetail(Long orderId) {
		log.info("--------------- getOrderDetail -----------------orderId={}", orderId);
		String username = "username-" + System.currentTimeMillis();

		PaymentDTO payment = null;
		try {
			payment = paymentApi.getPaymentByOrderId(orderId);
		} catch (Exception e) {
//			log.error("", e);
			log.error(e.getMessage());
			payment = PaymentDTO.builder()
					.id(System.currentTimeMillis())
					.orderId(orderId)
					.remark("支付服务：" + e.getMessage())
					.userId(null)
					.userRemark(null)
					.build();
		}

//		System.out.println("使用rest");
//		PaymentDTO paymentDTO = restTemplate.getForObject("http://payment/payment/order/1234", PaymentDTO.class);
//		System.out.println(new Gson().toJson(paymentDTO));

		return OrderDetailDTO.builder()
				.orderId(orderId)
				.orderRemark("订单服务: " + environmentName + "-" + environmentVersion + "-" + orderId)
				.userRemark(payment == null ? "" : payment.getUserRemark())
				.paymentRemark(payment == null ? "" : payment.getRemark())
				.build();
	}
}
