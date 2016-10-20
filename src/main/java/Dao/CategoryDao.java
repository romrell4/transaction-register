package Dao;

import Model.Category;
import Model.CategoryHelper;
import Model.Errors.InternalServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 10/18/16.
 */
public class CategoryDao extends BaseDao {
	private static final Logger LOG = LogManager.getLogger(CategoryDao.class);
	private static final String SELECT_CLAUSE = "select c.CATEGORY_ID, c.NAME, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') as MONTH, c.AMOUNT_BUDGETED, round(SUM(tx.AMOUNT), 2) as AMOUNT_SPENT, round(c.AMOUNT_BUDGETED - SUM(tx.AMOUNT), 2) as AMOUNT_LEFT from CATEGORIES c join TRANSACTIONS tx on c.CATEGORY_ID = tx.CATEGORY_ID ";
	private static final String DATE_WHERE_CLAUSE_FORMAT = "where DATE '%d/1/%d' <= tx.PURCHASE_DATE and tx.PURCHASE_DATE < DATE '%d/1/%d' ";
	private static final String GROUP_CLAUSE = "group by c.CATEGORY_ID, c.NAME, c.AMOUNT_BUDGETED, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') ";
	private static final String ORDER_CLAUSE = "order by c.CATEGORY_ID, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM')";

	public List<Category> getAll() {
		try (Connection connection = getConnection()) {
			ResultSet resultSet = connection.prepareStatement("select * from CATEGORIES").executeQuery();

			List<Category> categories = new ArrayList<>();
			while (resultSet.next()) {
				categories.add(new Category(resultSet));
			}
			return categories;
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public List<CategoryHelper> getAllByMonth(Integer month, Integer year) {
		try (Connection connection = getConnection()) {
			StringBuilder sql = new StringBuilder(SELECT_CLAUSE);
			if (month != null && year != null) {
				sql.append(String.format(DATE_WHERE_CLAUSE_FORMAT, month, year, (month + 1) % 12, month == 12 ? year + 1 : year));
			}
			sql.append(GROUP_CLAUSE).append(ORDER_CLAUSE);
			ResultSet resultSet = connection.prepareStatement(sql.toString()).executeQuery();

			List<CategoryHelper> categories = new ArrayList<>();
			while (resultSet.next()) {
				categories.add(new CategoryHelper(resultSet));
			}
			return categories;
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}
}
