package Dao;

import Model.CategoryHelper;
import Model.CategoryPrediction;
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
	private static final String SELECT_CLAUSE = "select c.CATEGORY_ID, c.NAME, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') as MONTH, c.AMOUNT_BUDGETED, round(coalesce(sum(tx.AMOUNT), 0), 2) as AMOUNT_SPENT, round(c.AMOUNT_BUDGETED - coalesce(sum(tx.AMOUNT), 0), 2) as AMOUNT_LEFT ";
	private static final String FROM_CLAUSE = "from CATEGORIES c left join TRANSACTIONS tx on c.CATEGORY_ID = tx.CATEGORY_ID ";
	private static final String DATE_CLAUSE_FORMAT = "and DATE '%d/1/%d' <= tx.PURCHASE_DATE and tx.PURCHASE_DATE < DATE '%d/1/%d' ";
	private static final String CATEGORY_ID_CLAUSE_FORMAT = "and c.CATEGORY_ID = %d ";
	private static final String WHERE_ACTIVE_TRUE = "where c.ACTIVE = TRUE ";
	private static final String GROUP_CLAUSE = "group by c.CATEGORY_ID, c.NAME, c.AMOUNT_BUDGETED, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') ";
	private static final String ORDER_CLAUSE = "order by c.CATEGORY_ID, TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') desc";
	private static final String CATEGORY_PREDICTION_SQL = "select a.BUSINESS, a.CATEGORY_ID, PERCENT\n" +
														  "from (\n" +
														  "\tselect a.BUSINESS, a.CATEGORY_ID, ROUND((1.0 * a.COUNT) / b.COUNT, 2) as PERCENT\n" +
														  "\tfrom (\n" +
														  "\t\tselect t.BUSINESS, t.CATEGORY_ID, count(*) as COUNT\n" +
														  "\t\tfrom TRANSACTIONS t\n" +
														  "\t\tgroup by t.BUSINESS, t.CATEGORY_ID\n" +
														  "\t) a\n" +
														  "\tjoin (\n" +
														  "\t\tselect t.BUSINESS, count(*) as COUNT\n" +
														  "\t\tfrom TRANSACTIONS t\n" +
														  "\t\tgroup by t.BUSINESS\n" +
														  "\t) b\n" +
														  "\t\ton a.BUSINESS = b.BUSINESS) a\n" +
														  "join (\n" +
														  "\tselect y.BUSINESS, y.CATEGORY_ID\n" +
														  "\tfrom (\n" +
														  "\t\tselect BUSINESS, max(COUNT) as COUNT\n" +
														  "\t\tfrom (\n" +
														  "\t\t\tselect t.BUSINESS, t.CATEGORY_ID, count(*) as COUNT\n" +
														  "\t\t\tfrom TRANSACTIONS t\n" +
														  "\t\t\tgroup by t.BUSINESS, t.CATEGORY_ID) a\n" +
														  "\t\tgroup by a.BUSINESS) z\n" +
														  "\tjoin (\n" +
														  "\t\tselect t.BUSINESS, t.CATEGORY_ID, count(*) as COUNT\n" +
														  "\t\tfrom TRANSACTIONS t\n" +
														  "\t\tgroup by t.BUSINESS, t.CATEGORY_ID) y\n" +
														  "\t\t\n" +
														  "\t\ton z.BUSINESS = y.BUSINESS\n" +
														  "\t\tand z.COUNT = y.COUNT\n" +
														  ") b\n" +
														  "\ton a.BUSINESS = b.BUSINESS\n" +
														  "\tand a.CATEGORY_ID = b.CATEGORY_ID\n" +
														  "order by a.BUSINESS";

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
			ResultSet resultSet = connection.prepareStatement("select null as MONTH, CATEGORY_ID, NAME, AMOUNT_BUDGETED, null as AMOUNT_SPENT, null as AMOUNT_LEFT from CATEGORIES where ACTIVE = TRUE").executeQuery();

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

	public List<CategoryPrediction> getCategoryPredictions() {
		try (Connection connection = getConnection()) {
			ResultSet resultSet = connection.prepareStatement(CATEGORY_PREDICTION_SQL).executeQuery();

			List<CategoryPrediction> predictions = new ArrayList<>();
			while (resultSet.next()) {
				predictions.add(new CategoryPrediction(resultSet));
			}
			return predictions;
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}
}
