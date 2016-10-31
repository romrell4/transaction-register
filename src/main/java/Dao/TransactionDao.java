package Dao;

import Model.Errors.InternalServerException;
import Model.Errors.NotFoundException;
import Model.PaymentType;
import Model.PaymentTypeSum;
import Model.Transaction;
import Model.TransactionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * Created by eric on 9/29/16.
 */
public class TransactionDao extends BaseDao {
	private static final Logger LOG = LogManager.getLogger(TransactionDao.class);
	private static final String SELECT_CLAUSE = "select tx.*, c.NAME from TRANSACTIONS tx join CATEGORIES c on tx.CATEGORY_ID = c.CATEGORY_ID ";

	public List<TransactionHelper> getAll(PaymentType paymentType, Integer month, Integer year) {
		try (Connection connection = getConnection()) {
			String sql = SELECT_CLAUSE;
			List<String> whereStatements = new ArrayList<>();
			if (paymentType != null) {
				whereStatements.add("tx.PAYMENT_TYPE = '" + paymentType + "' ");
			}
			if (month != null && year != null) {
				whereStatements.add("TO_CHAR(tx.PURCHASE_DATE, 'YYYY-MM') = '" + year + "-" + month + "' ");
			}
			sql += createWhereClause(whereStatements);
			ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

			List<TransactionHelper> transactions = new ArrayList<>();
			while (resultSet.next()) {
				transactions.add(new TransactionHelper(resultSet));
			}
			return transactions;
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public TransactionHelper getById(int transactionId) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLAUSE + "where tx.TRANSACTION_ID = ?");
			preparedStatement.setInt(1, transactionId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return new TransactionHelper(resultSet);
			} else {
				throw new NotFoundException("Couldn't find transaction with ID: " + transactionId);
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public List<PaymentTypeSum> getSumsByPaymentType() {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("select PAYMENT_TYPE, SUM(AMOUNT) as TOTAL from TRANSACTIONS group by PAYMENT_TYPE");
			ResultSet resultSet = preparedStatement.executeQuery();

			List<PaymentTypeSum> sums = new ArrayList<>();
			while (resultSet.next()) {
				sums.add(new PaymentTypeSum(resultSet));
			}
			return sums;
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public void deleteById(int transactionId) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("delete from TRANSACTIONS where TRANSACTION_ID = ?");
			preparedStatement.setInt(1, transactionId);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public TransactionHelper save(TransactionHelper transaction) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into TRANSACTIONS (PAYMENT_TYPE, PURCHASE_DATE, BUSINESS, AMOUNT, CATEGORY_ID, DESCRIPTION, UPDATED_BY, DATE_TIME_UPDATED) values (?, ?, ?, ?, ?, ?, ?, now()) returning *");
			preparedStatement.setString(1, transaction.getPaymentType().toString());
			preparedStatement.setDate(2, new java.sql.Date(transaction.getPurchaseDate().getTime()));
			preparedStatement.setString(3, transaction.getBusiness());
			preparedStatement.setDouble(4, transaction.getAmount());
			preparedStatement.setInt(5, transaction.getCategoryId());
			preparedStatement.setString(6, transaction.getDescription());
			preparedStatement.setString(7, "ERIC");

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Transaction newTx = new Transaction(resultSet);
				return getById(newTx.getTransactionId());
			}
			throw new InternalServerException("Creation failed");
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}

	public TransactionHelper update(int transactionId, TransactionHelper transaction) {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("update TRANSACTIONS set PAYMENT_TYPE = ?, PURCHASE_DATE = ?, BUSINESS = ?, AMOUNT = ?, CATEGORY_ID = ?, DESCRIPTION = ?, UPDATED_BY = ?, DATE_TIME_UPDATED = now() where TRANSACTION_ID = ? returning *");
			preparedStatement.setString(1, transaction.getPaymentType().toString());
			preparedStatement.setDate(2, new java.sql.Date(transaction.getPurchaseDate().getTime()));
			preparedStatement.setString(3, transaction.getBusiness());
			preparedStatement.setDouble(4, transaction.getAmount());
			preparedStatement.setInt(5, transaction.getCategoryId());
			preparedStatement.setString(6, transaction.getDescription());
			preparedStatement.setString(7, "ERIC");
			preparedStatement.setInt(8, transactionId);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Transaction newTx = new Transaction(resultSet);
				return getById(newTx.getTransactionId());
			} else {
				throw new NotFoundException("Couldn't find transaction with ID: " + transactionId);
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("SQL Error", e);
		}
	}
}
