package cn.caohongliang.gray.user.api.web;

import cn.caohongliang.gray.user.api.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@FeignClient(name = "user", contextId = "userApi")
public interface UserApi {

	/**
	 * 根据用户名获取用户
	 *
	 * @param username 用户名
	 * @return user
	 */
	@GetMapping("/name/{username}")
	UserDTO getByName(@PathVariable("username") String username);

	@GetMapping("/gray")
	Object grayStatus();
}
