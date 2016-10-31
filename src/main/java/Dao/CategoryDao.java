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
	private static final String SELECT_CLAUSE = "select c.CATEGORY_ID, c.NAME, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') as MONTH, c.AMOUNT_BUDGETED, round(sum(tx.AMOUNT), 2) as AMOUNT_SPENT, round(c.AMOUNT_BUDGETED - sum(tx.AMOUNT), 2) as AMOUNT_LEFT from CATEGORIES c join TRANSACTIONS tx on c.CATEGORY_ID = tx.CATEGORY_ID ";
	private static final String DATE_WHERE_CLAUSE_FORMAT = "DATE '%d/1/%d' <= tx.PURCHASE_DATE and tx.PURCHASE_DATE < DATE '%d/1/%d' ";
	private static final String CATEGORY_ID_WHERE_CLAUSE_FORMAT = "c.CATEGORY_ID = %d ";
	private static final String GROUP_CLAUSE = "group by c.CATEGORY_ID, c.NAME, c.AMOUNT_BUDGETED, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') ";
	private static final String ORDER_CLAUSE = "order by c.CATEGORY_ID, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') desc";

	public List<CategoryHelper> getBudget(Integer categoryId, Integer month, Integer year) {
		try (Connection connection = getConnection()) {
			StringBuilder sql = new StringBuilder(SELECT_CLAUSE);

			List<String> whereStatements = new ArrayList<>(2);
			if (categoryId != null) {
				whereStatements.add(String.format(CATEGORY_ID_WHERE_CLAUSE_FORMAT, categoryId));
			}

			if (month != null && year != null) {
				whereStatements.add(String.format(DATE_WHERE_CLAUSE_FORMAT, month, year, (month + 1) % 12, month == 12 ? year + 1 : year));
			}

			sql.append(createWhereClause(whereStatements));
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

	public List<CategoryHelper> getAllActive() {
		try (Connection connection = getConnection()) {
			ResultSet resultSet = connection.prepareStatement("select null as MONTH, CATEGORY_ID, NAME, null as AMOUNT_BUDGETED, null as AMOUNT_SPENT, null as AMOUNT_LEFT from CATEGORIES where ACTIVE = TRUE").executeQuery();

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
