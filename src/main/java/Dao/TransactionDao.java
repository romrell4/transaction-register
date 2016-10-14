package Dao;

import Model.Category;
import Model.PaymentType;
import Model.Transaction;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static Model.PaymentType.*;
import static Model.Category.*;

/**
 * Created by eric on 9/29/16.
 */
public class TransactionDao {
	private static final Logger LOG = Logger.getLogger(TransactionDao.class);
	private static int databaseId = 1;
	private static List<Transaction> database = createDatabase();

	private static List<Transaction> createDatabase() {
		List<Transaction> txs = new ArrayList<>();
		txs.add(new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, School, "", "", new Date()));
		txs.add(new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Misc, "", "", new Date()));
		txs.add(new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, School, "", "", new Date()));
		txs.add(new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, School, "", "", new Date()));
		txs.add(new Transaction(databaseId++, CREDIT, new Date(), "BYU", -1.00, Misc, "", "", new Date()));
		txs.add(new Transaction(databaseId++, DEBIT, new Date(), "BYU", 1500.00, School, "", "", new Date()));
		txs.add(new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Category.School, "", "", new Date()));
		txs.add(new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Category.School, "", "", new Date()));
		txs.add(new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Category.School, "", "", new Date()));
		txs.add(new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Category.School, "", "", new Date()));
		return txs;
	}

	private Connection connection;

	public TransactionDao() {
		String dbUrl = System.getenv("JDBC_DATABASE_URL");
		try {
			this.connection = DriverManager.getConnection(dbUrl);
		} catch (SQLException e) {
			LOG.error("Couldn't connect to the database");
		}
	}

	public Transaction getById(int transactionId) {
		Collection<Transaction> txs = Collections2.filter(database, transaction -> transaction.getTransactionId() == transactionId);
		return txs.iterator().hasNext() ? txs.iterator().next() : null;
	}

	public List<Transaction> getAll() throws SQLException {
		PreparedStatement preparedStatement = null;
		try {
			LOG.info("Starting tx");
			LOG.info(System.getenv("JDBC_DATABASE_URL"));
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			preparedStatement = connection.prepareStatement("select * from TRANSACTIONS");
			LOG.info(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			LOG.info("Executed tx");

			List<Transaction> transactions = new ArrayList<>();
			while (resultSet.next()) {
				resultSet.getInt("TRANSACTION_ID");
				transactions.add(new Transaction(resultSet));
			}
			return transactions;
		} catch (Exception e) {
			LOG.error(e);
			throw e;
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

	public List<Transaction> getAllByPaymentType(PaymentType paymentType) {
		return new ArrayList<>(Collections2.filter(database, transaction -> transaction.getPaymentType() == paymentType));
	}

	public void deleteById(int transactionId) {
		Transaction tx = getById(transactionId);
		if (tx != null) {
			database.remove(tx);
		}
	}

	public Transaction save(Transaction transaction) {
		transaction.setTransactionId(databaseId++);
		transaction.setUpdatedBy("Eric");
		transaction.setDateTimeUpdated(new Date());
		database.add(transaction);
		return transaction;
	}

	public Transaction update(int transactionId, Transaction transaction) {
		Transaction tx = getById(transactionId);
		tx.setPaymentType(transaction.getPaymentType());
		tx.setPurchaseDate(transaction.getPurchaseDate());
		tx.setBusiness(transaction.getBusiness());
		tx.setAmount(transaction.getAmount());
		tx.setCategory(transaction.getCategory());
		tx.setDescription(transaction.getDescription());
		tx.setUpdatedBy("Eric");
		tx.setDateTimeUpdated(new Date());
		return tx;
	}
}
