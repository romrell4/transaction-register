package Model;

import java.util.Date;

/**
 * Created by eric on 9/29/16.
 */
public class Transaction {
	private static int databaseId = 0;

	private int transactionId;
	private PaymentType paymentType;
	private Date purchaseDate;
	private String business;
	private double amount;
	private Category category;
	private String description;
	private String updatedBy;

	public Transaction(PaymentType paymentType, Date purchaseDate, String business, double amount, Category category, String description) {
		this.transactionId = createTxId();
		this.paymentType = paymentType;
		this.purchaseDate = purchaseDate;
		this.business = business;
		this.amount = amount;
		this.category = category;
		this.description = description;
	}

	private int createTxId() {
		return databaseId++;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
