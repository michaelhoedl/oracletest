package model;

public class Orderline {
	
	private int order_id;
	private int product_id;
	private float amount;
	
	public Orderline(int order_id, int product_id, float amount) {
		super();
		this.order_id = order_id;
		this.product_id = product_id;
		this.amount = amount;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	

}
