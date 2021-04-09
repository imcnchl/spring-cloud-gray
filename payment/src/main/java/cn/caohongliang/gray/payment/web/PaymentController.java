package cn.caohongliang.gray.payment.web;

import cn.caohongliang.gray.common.logger.Logger;
import cn.caohongliang.gray.core.util.RequestUtils;
import cn.caohongliang.gray.payment.api.dto.PaymentDTO;
import cn.caohongliang.gray.payment.api.web.PaymentApi;
import cn.caohongliang.gray.user.api.dto.UserDTO;
import cn.caohongliang.gray.user.api.web.UserApi;
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
public class PaymentController implements PaymentApi {
	@Resource
	private UserApi userApi;
	@Value("${environment.name}")
	private String environmentName;
	@Value("${environment.version}")
	private String environmentVersion;

	@Override
	public PaymentDTO getPaymentByOrderId(Long orderId) {
		HttpServletRequest request = RequestUtils.getRequest();
		Enumeration<String> en = request.getHeaderNames();
		while (en.hasMoreElements()) {
			String header = en.nextElement();
			Enumeration<String> values = request.getHeaders(header);
			while (values.hasMoreElements()) {
				Logger.info("请求头：" + header + "=" + values.nextElement());
			}
		}

		UserDTO user = null;
		try {
			user = userApi.getByName(orderId + "");
		} catch (Exception e) {
			log.error(e.getMessage());
//			log.error("", e);
		}
		String text = "环境=" + String.format("%1s", environmentName) + "，版本=" + String.format("%3s", environmentVersion) +
				"，时间=" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		return PaymentDTO.builder()
				.id(System.currentTimeMillis())
				.orderId(orderId)
				.remark("支付服务：" + text)
				.userId(user == null ? null : user.getId())
				.userRemark(user == null ? null : user.getRemark())
				.build();
	}
}
