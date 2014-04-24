package cn.com.makejournal.newlife.platform.exchange.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.makejournal.newlife.platform.commons.utils.IdUtils;
import cn.com.makejournal.newlife.platform.exchange.enums.OrderStatus;

/**
 * 订单
 * 
 * @author huke
 * 
 */
@Entity
@Table(name = "mj_exchange_order")
public class Order implements Serializable {

	private static final long serialVersionUID = 6906047942072253334L;

	/**
	 * id
	 */
	@Id
	private String id;

	/*
	 * 订单编号
	 */
	@Column(unique = true)
	private String orderCode;

	/**
	 * 状态 系统处理：待评价、已完成、已取消
	 */
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	/**
	 * 支付方式
	 */
	private String payMode;

	/**
	 * 购买用户(User的id)
	 */
	private String creator;

	/**
	 * 对应的社交平台的id
	 */
	private String platformUserId;

	/**
	 * pdf的Id
	 */
	private String pdfId;

	/**
	 * 下单时间
	 */
	private Date createTime;

	/**
	 * 订单支付金额
	 */
	private Double amount;

	/**
	 * 发票抬头
	 */
	private String invoiceTitle;

	/**
	 * 发票内容
	 */
	@Column(length = 2000)
	private String invoiceContent;

	/**
	 * 支付时间
	 */
	private Date payTime;

	/**
	 * 取消时间
	 */
	private Date cancelTime;

	public Order() {
		this.id = IdUtils.getUUID();
		this.createTime = new Date();
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPlatformUserId() {
		return platformUserId;
	}

	public void setPlatformUserId(String platformUserId) {
		this.platformUserId = platformUserId;
	}

	public String getPdfId() {
		return pdfId;
	}

	public void setPdfId(String pdfId) {
		this.pdfId = pdfId;
	}

}
