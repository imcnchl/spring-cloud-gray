package cn.caohongliang.gray.user.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 用户DTO
 *
 * @author caohongliang
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {
	private static final long serialVersionUID = -1339856466198578808L;

	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 备注
	 */
	private String remark;
}
