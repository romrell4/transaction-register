package Dao;

import Model.Category;
import Model.PaymentType;
import Model.Transaction;

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
	private static List<Transaction> database = Arrays.asList(
			new Transaction(CREDIT, new Date(), "BYU", 1500.00, School, ""),
			new Transaction(CREDIT, new Date(), "BYU", 1500.00, Misc, ""),
			new Transaction(CREDIT, new Date(), "BYU", 1500.00, School, ""),
			new Transaction(CREDIT, new Date(), "BYU", 1500.00, School, ""),
			new Transaction(CREDIT, new Date(), "BYU", -1.00, Misc, ""),
			new Transaction(DEBIT, new Date(), "BYU", 1500.00, School, ""),
			new Transaction(CREDIT, new Date(), "BYU", 1500.00, Category.School, ""),
			new Transaction(CREDIT, new Date(), "BYU", 1500.00, Category.School, ""),
			new Transaction(CREDIT, new Date(), "BYU", 1500.00, Category.School, ""),
			new Transaction(CREDIT, new Date(), "BYU", 1500.00, Category.School, "")
	);

	public static Transaction getById(int transactionId) {
		//TODO: Actually get it
		return null;
	}

	public static List<Transaction> getAllTransactionsByPaymentType(PaymentType paymentType) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		for (Transaction transaction : database) {
			if (transaction.getPaymentType() == paymentType) {
				transactions.add(transaction);
			}
		}
		return transactions;
	}

	public static void deleteById(int transactionId) {
		//TODO: Actually delete it
	}

	public static Transaction saveTransaction(Transaction transaction) {
		//TODO: Actually save it
		return null;
	}

	public static Transaction updateTransaction(int transactionId, Transaction transaction) {
		//TODO: Actually update it
		return null;
	}
}
