package cn.caohongliang.gray.common.util;

import cn.caohongliang.gray.common.logger.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP相关工具类
 *
 * @author caohongliang
 */
public class IPUtils {

	public static String getClientIp(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");
		if (Logger.isDebugEnabled()) {
			Logger.debug("x-forwarded-for：{}", ip);
		}
		if (!StringUtils.hasLength(ip)) {
			String[] arr = ip.split(",");
			return arr.length <= 0 ? "" : arr[arr.length - 1];
		}
		ip = request.getHeader("X-Real-IP");
		if (!StringUtils.hasLength(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	public static String getServerIp() {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getLocalHost();
			return inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			Logger.error("获取服务器IP失败：", e);
		}
		return "";
	}
}
