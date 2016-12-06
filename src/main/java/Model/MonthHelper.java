package Model;

import java.util.List;

/**
 * Created by eric on 12/6/16.
 */
public class MonthHelper {
	private String month;
	private List<CategoryHelper> categories;

	public MonthHelper(String month, List<CategoryHelper> categories) {
		this.month = month;
		this.categories = categories;
	}
}
