package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by eric on 10/20/16.
 */
public class Category {
	private int categoryId;
	private String name;
	private Double amount_budgeted;
	private boolean active;
	private String updatedBy;
	private Date dateTimeUpdated;


	public Category(ResultSet resultSet) throws SQLException {
		this.categoryId = resultSet.getInt("CATEGORY_ID");
		this.name = resultSet.getString("NAME");
		this.amount_budgeted = resultSet.getDouble("AMOUNT_BUDGETED");
		this.active = resultSet.getBoolean("ACTIVE");
		this.updatedBy = resultSet.getString("UPDATED_BY");
		this.dateTimeUpdated = resultSet.getDate("DATE_TIME_UPDATED");
	}
}
