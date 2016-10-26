package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by eric on 10/26/16.
 */
public class TransactionHelper extends Transaction {
	private String categoryName;

	public TransactionHelper(ResultSet resultSet) throws SQLException {
		super(resultSet);
		this.categoryName = resultSet.getString("NAME");
	}
}
