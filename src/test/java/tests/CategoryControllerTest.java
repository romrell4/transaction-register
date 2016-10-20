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
		List<CategoryHelper> categories = controller.getAllCategoriesByMonth(null, null);
		assertTrue(categories.size() > 0);

		categories = controller.getAllCategoriesByMonth(9, 2014);
		assertTrue(categories.size() > 0);
		assertTrue(false);
	}
}
