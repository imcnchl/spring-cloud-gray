package cn.caohongliang.gray.order.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 订单详情
 *
 * @author caohongliang
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO implements Serializable {
	private static final long serialVersionUID = -7499043199051202026L;

	private Long orderId;
	private String remark1;
	private String remark2;
	private String remark3;
}
