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
public class CategoryController {
	private static final Logger LOG = LogManager.getLogger(CategoryController.class);

	private CategoryDao categoryDao = new CategoryDao();
	private TransactionDao transactionDao = new TransactionDao();

	public List<CategoryHelper> getAllCategoriesForBudget(Integer categoryId, Integer month, Integer year) {
		return categoryDao.getBudget(categoryId, month, year);
	}
}
