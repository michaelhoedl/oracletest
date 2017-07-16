package model;

public class Orderline {
	
	private Orders order;
	private Product product;
	private float amount;
	
	public Orderline(Orders order, Product product, float amount) {
		super();
		this.order = order;
		this.product = product;
		this.amount = amount;
	}
	
	

	public Orderline(Orders order, Product product) {
		super();
		this.order = order;
		this.product = product;
	}



	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Orderline [order=" + order + ", product=" + product + ", amount=" + amount + "]";
	}
	
	
	

}
