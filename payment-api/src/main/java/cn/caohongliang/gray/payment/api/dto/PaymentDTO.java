package cn.caohongliang.gray.payment.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PaymentDTO implements Serializable {

	private Long id;
	private Long orderId;
	private String remark;
	private Long userId;
	private String userRemark;
}
