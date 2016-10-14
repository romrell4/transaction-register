package Dao;

import Model.Errors.InternalServerException;
import Model.Errors.NotFoundException;
import Model.PaymentType;
import Model.Transaction;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Created by eric on 9/29/16.
 */
public class TransactionDao {
	private static final Logger LOG = Logger.getLogger(TransactionDao.class);
	private final DataSource DATASOURCE;

	private Connection getConnection() {
		try {
			return DATASOURCE.getConnection();
		} catch (SQLException e) {
			LOG.error(e);
			throw new InternalServerException("Failed to connect to database", e);
		}
	}

	public TransactionDao() {
		HikariConfig config = new HikariConfig();
		String dbUrl = System.getenv("JDBC_DATABASE_URL");
		config.setJdbcUrl(dbUrl != null ? dbUrl : "jdbc:postgresql://ec2-54-243-202-174.compute-1.amazonaws.com:5432/dbeve90fv3htt2");
		config.setUsername("nttptecwetywbt");
		config.setPassword("rGwDbJXFZKHAOQiouxXhP5fLEu");
		config.addDataSourceProperty("sslmode", "require");

		DATASOURCE = new HikariDataSource(config);
	}

	public Transaction getById(int transactionId) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from TRANSACTIONS where TRANSACTION_ID = ?");
			preparedStatement.setInt(1, transactionId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return new Transaction(resultSet);
			} else {
				throw new NotFoundException("Couldn't find transaction with ID: " + transactionId);
			}
		} catch (SQLException e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public List<Transaction> getAll() {
		try (Connection connection = getConnection()) {
			ResultSet resultSet = connection.prepareStatement("select * from TRANSACTIONS").executeQuery();

			List<Transaction> transactions = new ArrayList<>();
			while (resultSet.next()) {
				transactions.add(new Transaction(resultSet));
			}
			return transactions;
		} catch (SQLException e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public List<Transaction> getAllByPaymentType(PaymentType paymentType) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from TRANSACTIONS where PAYMENT_TYPE = ?");
			preparedStatement.setString(1, paymentType.toString());
			ResultSet resultSet = preparedStatement.executeQuery();

			List<Transaction> transactions = new ArrayList<>();
			while (resultSet.next()) {
				transactions.add(new Transaction(resultSet));
			}
			return transactions;
		} catch (SQLException e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public void deleteById(int transactionId) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("delete from TRANSACTIONS where TRANSACTION_ID = ?");
			preparedStatement.setInt(1, transactionId);
			preparedStatement.executeQuery();
		} catch (SQLException e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public Transaction save(Transaction transaction) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into TRANSACTIONS (PAYMENT_TYPE, PURCHASE_DATE, BUSINESS, AMOUNT, CATEGORY, DESCRIPTION, UPDATED_BY, DATE_TIME_UPDATED) values (?, ?, ?, ?, ?, ?, ?, now()) returning *");
			preparedStatement.setString(1, transaction.getPaymentType().toString());
			preparedStatement.setDate(2, new java.sql.Date(transaction.getPurchaseDate().getTime()));
			preparedStatement.setString(3, transaction.getBusiness());
			preparedStatement.setDouble(4, transaction.getAmount());
			preparedStatement.setString(5, transaction.getCategory().toString());
			preparedStatement.setString(6, transaction.getDescription());
			preparedStatement.setString(7, "ERIC");

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return new Transaction(resultSet);
			} else {
				throw new InternalServerException("Creation failed");
			}
		} catch (SQLException e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public void update(int transactionId, Transaction transaction) {
		LOG.info(1);
		try (Connection connection = getConnection()) {
			LOG.info(2);
			PreparedStatement preparedStatement = connection.prepareStatement("update TRANSACTIONS set PAYMENT_TYPE = ?, PURCHASE_DATE = ?, BUSINESS = ?, AMOUNT = ?, CATEGORY = ?, DESCRIPTION = ?, UPDATED_BY = ?, DATE_TIME_UPDATED = now() where TRANSACTION_ID = ?");
			LOG.info(3);
			preparedStatement.setString(1, transaction.getPaymentType().toString());
			preparedStatement.setDate(2, new java.sql.Date(transaction.getPurchaseDate().getTime()));
			preparedStatement.setString(3, transaction.getBusiness());
			preparedStatement.setDouble(4, transaction.getAmount());
			preparedStatement.setString(5, transaction.getCategory().toString());
			preparedStatement.setString(6, transaction.getDescription());
			preparedStatement.setString(7, "ERIC");
			preparedStatement.setInt(8, transactionId);

			int results = preparedStatement.executeUpdate();
			LOG.info(results);
			if (results == 0) {
				throw new NotFoundException("No results updated");
			}
		} catch (SQLException e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}
}
