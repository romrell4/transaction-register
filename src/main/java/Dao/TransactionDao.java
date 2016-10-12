package Dao;

import Model.Category;
import Model.PaymentType;
import Model.Transaction;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.log4j.Logger;

import java.util.*;

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

	public Transaction getById(int transactionId) {
		Collection<Transaction> txs = Collections2.filter(database, transaction -> transaction.getTransactionId() == transactionId);
		return txs.iterator().hasNext() ? txs.iterator().next() : null;
	}

	public List<Transaction> getAll() {
		return database;
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
