package model;

import java.util.Date;

public class Orders {
	
	private int order_id;
	private Date order_date;
	private String username;
	
	
	public Orders(int order_id, Date order_date, String username) {
		super();
		this.order_id = order_id;
		this.order_date = order_date;
		this.username = username;
	}


	public int getOrder_id() {
		return order_id;
	}


	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}


	public Date getOrder_date() {
		return order_date;
	}


	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
