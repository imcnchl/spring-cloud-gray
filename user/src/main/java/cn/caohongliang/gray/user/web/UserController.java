package cn.caohongliang.gray.user.web;

import cn.caohongliang.gray.common.logger.Logger;
import cn.caohongliang.gray.core.flowcontrol.config.FlowControlProperties;
import cn.caohongliang.gray.core.util.RequestUtils;
import cn.caohongliang.gray.user.api.dto.UserDTO;
import cn.caohongliang.gray.user.api.web.UserApi;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

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
		HttpServletRequest request = RequestUtils.getRequest();
		Enumeration<String> en = request.getHeaderNames();
		while (en.hasMoreElements()) {
			String header = en.nextElement();
			Enumeration<String> values = request.getHeaders(header);
			while (values.hasMoreElements()) {
				Logger.info("请求头：" + header + "=" + values.nextElement());
			}
		}
		String text = "环境=" + String.format("%1s", environmentName) + "，版本=" + String.format("%3s", environmentVersion) +
				"，时间=" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		return UserDTO.builder()
				.id(System.currentTimeMillis())
				.username(username)
				.remark("用户服务：" + text)
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
