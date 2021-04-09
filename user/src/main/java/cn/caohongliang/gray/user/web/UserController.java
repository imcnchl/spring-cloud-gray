package cn.caohongliang.gray.user.web;

import cn.caohongliang.gray.core.flowcontrol.FlowControlProperties;
import cn.caohongliang.gray.user.api.dto.UserDTO;
import cn.caohongliang.gray.user.api.web.UserApi;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController implements UserApi {
	@Resource
	private FlowControlProperties flowControlProperties;
	@Value("${environment.name}")
	private String environmentName;
	@Value("${environment.version}")
	private String environmentVersion;

	@Override
	public UserDTO getByName(String username) {
		return UserDTO.builder()
				.id(System.currentTimeMillis())
				.username(username)
				.remark("用户服务：" + environmentName + "-" + environmentVersion + "-" + username)
				.build();
	}

	@Override
	public Object grayStatus() {
		System.out.println("-------------------------------------------");
		System.out.println(new Gson().toJson(flowControlProperties));
		System.out.println("-------------------------------------------");
		return flowControlProperties;
	}
}
