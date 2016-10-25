package tests;

import Controller.CategoryController;
import Model.CategoryHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by eric on 10/20/16.
 */
@RunWith(JUnit4.class)
public class CategoryControllerTest {
	private static final Logger LOG = LogManager.getLogger(CategoryControllerTest.class);

	private CategoryController controller = new CategoryController();

	@Test
	public void test1() {
		List<CategoryHelper> categories = controller.getAllCategoriesForBudget(null, null, null);
		int fullSize = categories.size();
		assertTrue(fullSize > 0);

		categories = controller.getAllCategoriesForBudget(null, "9", null);
		assertTrue(categories.size() == fullSize);

		categories = controller.getAllCategoriesForBudget(null, null, "2015");
		assertTrue(categories.size() == fullSize);

		categories = controller.getAllCategoriesForBudget(null, "09", "2015");
		int oneMonthSize = categories.size();
		assertTrue(oneMonthSize < fullSize);

		categories = controller.getAllCategoriesForBudget(String.valueOf(categories.get(0).getCategoryId()), null, null);
		int oneCategorySize = categories.size();
		assertTrue(oneCategorySize < fullSize);
	}
}
