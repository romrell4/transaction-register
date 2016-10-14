package Controller;

import Dao.TransactionDao;
import Model.PaymentType;
import Model.Transaction;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by eric on 9/29/16.
 */
public class TransactionController {
	private static final Logger LOG = Logger.getLogger(TransactionController.class);

	private TransactionDao dao = new TransactionDao();

	public List<Transaction> getAllTransactions() {
		LOG.info("/transactions GET");
		return dao.getAll();
	}

	public Transaction getTransactionById(int transactionId) {
		LOG.info("/transactions/:id GET");
		return dao.getById(transactionId);
	}

	public Transaction createTransaction(Transaction transaction) {
		LOG.info("/transactions POST");
		return dao.save(transaction);
	}

	public String deleteTransaction(int transactionId) {
		LOG.info("/transactions/:id DELETE");
		dao.deleteById(transactionId);
		return "Success";
	}

	public String updateTransaction(int transactionId, Transaction transaction) {
		LOG.info("/transactions/:id PUT");
		dao.update(transactionId, transaction);
		return "Success";
	}

	public List<Transaction> getAllTransactionsByPaymentType(PaymentType paymentType) {
		LOG.info("/transactions/:type GET");
		return dao.getAllByPaymentType(paymentType);
	}
}
