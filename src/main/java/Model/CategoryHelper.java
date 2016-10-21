package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by eric on 10/18/16.
 */
public class CategoryHelper {
	private int categoryId;
	private String name;
	private String month;
	private double amountBudgeted;
	private double amountSpent;
	private double amountLeft;

	public CategoryHelper(ResultSet resultSet) throws SQLException {
		this.categoryId = resultSet.getInt("CATEGORY_ID");
		this.name = resultSet.getString("NAME");
		this.month = resultSet.getString("MONTH");
		this.amountBudgeted = resultSet.getDouble("AMOUNT_BUDGETED");
		this.amountSpent = resultSet.getDouble("AMOUNT_SPENT");
		this.amountLeft = resultSet.getDouble("AMOUNT_LEFT");
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getAmountBudgeted() {
		return amountBudgeted;
	}

	public void setAmountBudgeted(double amountBudgeted) {
		this.amountBudgeted = amountBudgeted;
	}

	public double getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent(double amountSpent) {
		this.amountSpent = amountSpent;
	}

	public double getAmountLeft() {
		return amountLeft;
	}

	public void setAmountLeft(double amountLeft) {
		this.amountLeft = amountLeft;
	}
}
