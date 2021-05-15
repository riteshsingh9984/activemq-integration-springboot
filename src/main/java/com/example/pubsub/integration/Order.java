package com.example.pubsub.integration;
import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order implements Serializable {

    private String content;
    private String orderId;
    private String address;
    private Double amount;
    private Date timestamp;

    public Order() {
    }

    public Order(String content, Date timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Order [content=" + content + ", orderId=" + orderId + ", address=" + address + ", amount=" + amount
				+ ", timestamp=" + timestamp + "]";
	}

}