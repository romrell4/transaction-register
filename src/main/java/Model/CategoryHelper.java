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
}
