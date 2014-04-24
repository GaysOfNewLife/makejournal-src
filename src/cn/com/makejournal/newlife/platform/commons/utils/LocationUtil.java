package cn.com.makejournal.newlife.platform.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import cn.com.makejournal.newlife.platform.commons.entity.IpAndAddress;

import com.alibaba.fastjson.JSONObject;

/**
 * 定位工具类
 * 
 * @author huke
 * @date 2014-02-14
 * 
 */
public class LocationUtil {

	/**
	 * 根据IP定位
	 * 
	 * @param ip
	 * @return
	 */
	public static IpAndAddress getLocationByIp(String ip) {
		GetMethod method = new GetMethod(
				"http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
		HttpClient client = new HttpClient();
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = null;
		try {
			InputStream resStream = method.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					resStream));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			value = resBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		IpAndAddress iaa = null;
		if (value == null || "".equals(value))
			return null;
		JSONObject ob = JSONObject.parseObject(value);
		Integer code = (Integer) ob.get("code");
		if (code == 0) {
			JSONObject data = ob.getJSONObject("data");
			iaa = new IpAndAddress();
			iaa.setIp(ip);
			iaa.setCountry((String) data.get("country"));// 国家
			iaa.setArea((String) data.get("area"));// 区域
			iaa.setRegion((String) data.get("region"));// 省份
			iaa.setCity((String) data.get("city"));// 城市
			iaa.setCounty((String) data.get("county"));// 县
			iaa.setIsp((String) data.get("isp"));// 运营商
		}
		return iaa;
	}

	public static void main(String[] args) {
		System.out.println(getLocationByIp("111.175.90.117").toString());
	}
}
