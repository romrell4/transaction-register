package Dao;

import Model.Category;
import Model.PaymentType;
import Model.Transaction;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static Model.PaymentType.*;
import static Model.Category.*;

/**
 * Created by eric on 9/29/16.
 */
public class TransactionDao {
	private static int databaseId = 1;
	private static List<Transaction> database = Arrays.asList(
			new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, School, "", "", new Date()),
			new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Misc, "", "", new Date()),
			new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, School, "", "", new Date()),
			new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, School, "", "", new Date()),
			new Transaction(databaseId++, CREDIT, new Date(), "BYU", -1.00, Misc, "", "", new Date()),
			new Transaction(databaseId++, DEBIT, new Date(), "BYU", 1500.00, School, "", "", new Date()),
			new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Category.School, "", "", new Date()),
			new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Category.School, "", "", new Date()),
			new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Category.School, "", "", new Date()),
			new Transaction(databaseId++, CREDIT, new Date(), "BYU", 1500.00, Category.School, "", "", new Date())
	);

	public Transaction getById(int transactionId) {
		return new ArrayList<>(Collections2.filter(database, transaction -> transaction.getTransactionId() == transactionId)).get(0);
	}

	public List<Transaction> getAll() {
		return database;
	}

	public List<Transaction> getAllByPaymentType(PaymentType paymentType) {
		return new ArrayList<>(Collections2.filter(database, transaction -> transaction.getPaymentType() == paymentType));
	}

	public void deleteById(int transactionId) {
		database.remove(getById(transactionId));
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
