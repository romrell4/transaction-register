package Controller;

import Dao.TransactionDao;
import Model.Category;
import Model.PaymentType;
import Model.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by eric on 9/29/16.
 */
public class TransactionController {

	private TransactionDao dao = new TransactionDao();

	public List<Transaction> getAllTransactions() {
		return dao.getAll();
	}

	public Transaction getTransactionById(int transactionId) {
		return dao.getById(transactionId);
	}

	public Transaction createTransaction(Transaction transaction) {
		return dao.save(transaction);
	}

	public void deleteTransaction(int transactionId) {
		dao.deleteById(transactionId);
	}

	public Transaction updateTransaction(int transactionId, Transaction transaction) {
		return dao.update(transactionId, transaction);
	}

	public List<Transaction> getAllTransactionsByPaymentType(PaymentType paymentType) {
		return dao.getAllByPaymentType(paymentType);
	}
}
