package cn.com.makejournal.newlife.platform.commons.entity;

/**
 * Ip和地址对应关系
 * 
 * @author huke
 * 
 */
public class IpAndAddress {
	/**
	 * Ip地址(area=华中)
	 */
	private String ip;

	/**
	 * 国家 (country=中国)
	 */
	private String country;

	// 区域 (area=华中)
	private String area;
	// 省份(region=湖北省)
	private String region;
	// 城市(city=武汉市)
	private String city;
	// 县
	private String county;
	// 运营商(isp=电信)
	private String isp;

	public IpAndAddress() {
		super();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	@Override
	public String toString() {
		return "IpAndAddress [area=" + area + ", city=" + city + ", country="
				+ country + ", county=" + county + ", ip=" + ip + ", isp="
				+ isp + ", region=" + region + "]";
	}
}
