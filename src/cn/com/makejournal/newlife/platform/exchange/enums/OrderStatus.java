package cn.com.makejournal.newlife.platform.exchange.enums;

public enum OrderStatus {
	WAIT_EVALUATE("待评价"), CANCELED("已取消"), FINISHED("已完成");

	private String showName;

	private OrderStatus(String showName) {
		this.showName = showName;
	}

	public String getShowName() {
		return this.showName;
	}
}
