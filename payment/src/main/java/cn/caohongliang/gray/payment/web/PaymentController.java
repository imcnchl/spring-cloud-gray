package cn.caohongliang.gray.payment.web;

import cn.caohongliang.gray.payment.api.dto.PaymentDTO;
import cn.caohongliang.gray.payment.api.web.PaymentApi;
import cn.caohongliang.gray.user.api.dto.UserDTO;
import cn.caohongliang.gray.user.api.web.UserApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class PaymentController implements PaymentApi {
	@Resource
	private UserApi userApi;
	@Value("${environment.name}")
	private String environmentName;
	@Value("${environment.version}")
	private String environmentVersion;

	@Override
	public PaymentDTO getPaymentByOrderId(Long orderId) {
		UserDTO user = null;
		try {
			user = userApi.getByName(orderId + "");
		} catch (Exception e) {
			log.error(e.getMessage());
//			log.error("", e);
		}
		return PaymentDTO.builder()
				.id(System.currentTimeMillis())
				.orderId(orderId)
				.remark("支付服务：" + environmentName + "-" + environmentVersion + "-" + orderId)
				.userId(user == null ? null : user.getId())
				.userRemark(user == null ? null : user.getRemark())
				.build();
	}
}
