package Dao;

import Model.CategoryHelper;
import Model.Errors.InternalServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 10/18/16.
 */
public class CategoryDao extends BaseDao {
	private static final Logger LOG = LogManager.getLogger(CategoryDao.class);
	private static final String SELECT_CLAUSE = "select c.CATEGORY_ID, c.NAME, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') as MONTH, c.AMOUNT_BUDGETED, round(coalesce(sum(tx.AMOUNT), 0), 2) as AMOUNT_SPENT, round(c.AMOUNT_BUDGETED - coalesce(sum(tx.AMOUNT), 0), 2) as AMOUNT_LEFT ";
	private static final String FROM_CLAUSE = "from CATEGORIES c left join TRANSACTIONS tx on c.CATEGORY_ID = tx.CATEGORY_ID ";
	private static final String DATE_CLAUSE_FORMAT = "and DATE '%d/1/%d' <= tx.PURCHASE_DATE and tx.PURCHASE_DATE < DATE '%d/1/%d' ";
	private static final String CATEGORY_ID_CLAUSE_FORMAT = "and c.CATEGORY_ID = %d ";
	private static final String WHERE_ACTIVE_TRUE = "where ACTIVE = TRUE ";
	private static final String GROUP_CLAUSE = "group by c.CATEGORY_ID, c.NAME, c.AMOUNT_BUDGETED, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') ";
	private static final String ORDER_CLAUSE = "order by c.CATEGORY_ID, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') desc";

	public List<CategoryHelper> getBudget(Integer categoryId, Integer month, Integer year) {
		try (Connection connection = getConnection()) {
			StringBuilder sql = new StringBuilder(SELECT_CLAUSE).append(FROM_CLAUSE);

			if (categoryId != null) {
				sql.append(String.format(CATEGORY_ID_CLAUSE_FORMAT, categoryId));
			}

			if (month != null && year != null) {
				sql.append(String.format(DATE_CLAUSE_FORMAT, month, year, (month % 12) + 1, year + (month / 12)));
			}

			sql.append(WHERE_ACTIVE_TRUE);

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
