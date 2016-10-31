package Controller;

import Dao.CategoryDao;
import Dao.TransactionDao;
import Model.CategoryHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
}
