package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by eric on 9/29/16.
 */
public class Transaction {
	private int transactionId;
	private PaymentType paymentType;
	private Date purchaseDate;
	private String business;
	private double amount;
	private int categoryId;
	private String description;
	private String updatedBy;
	private Date dateTimeUpdated;

	public Transaction(ResultSet resultSet) throws SQLException {
		this.transactionId = resultSet.getInt("TRANSACTION_ID");
		this.paymentType = PaymentType.valueOf(resultSet.getString("PAYMENT_TYPE"));
		this.purchaseDate = resultSet.getDate("PURCHASE_DATE");
		this.business = resultSet.getString("BUSINESS");
		this.amount = resultSet.getDouble("AMOUNT");
		this.categoryId = resultSet.getInt("CATEGORY_ID");
		this.description = resultSet.getString("DESCRIPTION");
		this.updatedBy = resultSet.getString("UPDATED_BY");
		this.dateTimeUpdated = resultSet.getDate("DATE_TIME_UPDATED");
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	public Date getDateTimeUpdated() {
		return dateTimeUpdated;
	}

	public void setDateTimeUpdated(Date dateTimeUpdated) {
		this.dateTimeUpdated = dateTimeUpdated;
	}
}
