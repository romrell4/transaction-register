package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by eric on 10/31/16.
 */
public class PaymentTypeSum {
	private PaymentType paymentType;
	private double total;

	public PaymentTypeSum(ResultSet resultSet) throws SQLException {
		this.paymentType = PaymentType.valueOf(resultSet.getString("PAYMENT_TYPE"));
		this.total = resultSet.getDouble("TOTAL");
	}
}
