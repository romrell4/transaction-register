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

	public static void main(String[] args) {
		TransactionController controller = new TransactionController();
		System.out.println(controller.getAllTransactionsByPaymentType(PaymentType.Credit));
	}

	public Transaction createTransaction(PaymentType paymentType, Date purchaseDate, String business, double amount, Category category, String description) {
		Transaction transaction = new Transaction(paymentType, purchaseDate, business, amount, category, description);
		transaction = TransactionDao.saveTransaction(transaction);
		return transaction;
	}

	public void deleteTransaction(int transactionId) {
		TransactionDao.deleteById(transactionId);
	}

	public Transaction updateTransaction(int transactionId, Transaction transaction) {
		transaction = TransactionDao.updateTransaction(transactionId, transaction);
		return transaction;
	}

	public List<Transaction> getAllTransactionsByPaymentType(PaymentType paymentType) {
		List<Transaction> transactions = TransactionDao.getAllTransactionsByPaymentType(paymentType);
		return transactions;
	}
}
