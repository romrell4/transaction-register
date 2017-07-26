package Controller;

import Dao.CategoryDao;
import Dao.TransactionDao;
import Model.CategoryHelper;
import Model.CategoryPrediction;
import Model.MonthHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by eric on 10/18/16.
 */
public class CategoryController extends BaseController {
	private static final Logger LOG = LogManager.getLogger(CategoryController.class);

	private CategoryDao categoryDao = new CategoryDao();

	public List<CategoryHelper> getAllCategoriesForBudget(String categoryIdStr, String monthStr, String yearStr) {
		Integer categoryId = categoryIdStr == null ? null : toInt(categoryIdStr);
		Integer month = monthStr == null ? null : toInt(monthStr);
		Integer year = yearStr == null ? null : toInt(yearStr);
		return categoryDao.getBudget(categoryId, month, year);
	}

	public List<CategoryHelper> getAllActiveCategories() {
		return categoryDao.getAllActive();
	}

	public List<MonthHelper> getAllCategoriesByMonth() {
		Calendar cal = new GregorianCalendar();
		Calendar end = new GregorianCalendar(2014, 9, 1);
		cal.setTime(new Date());
		List<MonthHelper> months = new ArrayList<>();
		while (cal.after(end)) {
			List<CategoryHelper> categories = getAllCategoriesForBudget(null, String.valueOf(cal.get(Calendar.MONTH) + 1), String.valueOf(cal.get(Calendar.YEAR)));
			months.add(new MonthHelper(new SimpleDateFormat("MMMM YYYY").format(cal.getTime()), categories));
			cal.add(Calendar.MONTH, -1);
		}
		return months;
	}

	public List<CategoryPrediction> getCategoryPredictions() {
		return categoryDao.getCategoryPredictions();
	}
}
