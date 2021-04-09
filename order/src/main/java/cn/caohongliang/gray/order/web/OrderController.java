package cn.caohongliang.gray.order.web;

import cn.caohongliang.gray.common.logger.Logger;
import cn.caohongliang.gray.core.util.RequestUtils;
import cn.caohongliang.gray.order.api.dto.OrderDetailDTO;
import cn.caohongliang.gray.order.api.web.OrderApi;
import cn.caohongliang.gray.payment.api.dto.PaymentDTO;
import cn.caohongliang.gray.payment.api.web.PaymentApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

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

		HttpServletRequest request = RequestUtils.getRequest();
		Enumeration<String> en = request.getHeaderNames();
		while (en.hasMoreElements()) {
			String header = en.nextElement();
			Enumeration<String> values = request.getHeaders(header);
			while (values.hasMoreElements()) {
				Logger.info("请求头：" + header + "=" + values.nextElement());
			}
		}

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

		String text = "环境=" + String.format("%1s", environmentName) + "，版本=" + String.format("%3s", environmentVersion) +
				"，时间=" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		return OrderDetailDTO.builder()
				.orderId(orderId)
				.remark1("订单服务：" + text)
				.remark2(payment == null ? "" : payment.getRemark())
				.remark3(payment == null ? "" : payment.getUserRemark())
				.build();
	}
}
