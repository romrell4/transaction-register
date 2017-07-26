package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryPrediction {
	private String businessName;
	private Integer predictedCategoryId;
	private Double predictionScore;

	public CategoryPrediction(ResultSet resultSet) throws SQLException {
		this.businessName = resultSet.getString("BUSINESS");
		this.predictedCategoryId = resultSet.getInt("CATEGORY_ID");
		this.predictionScore = resultSet.getDouble("PERCENT");
	}
}
