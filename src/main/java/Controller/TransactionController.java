package Controller;

import Dao.TransactionDao;
import Model.Category;
import Model.ErrorResponse;
import Model.PaymentType;
import Model.Transaction;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eric on 9/29/16.
 */
public class TransactionController {
	private static final Logger LOG = Logger.getLogger(TransactionController.class);

	private TransactionDao dao = new TransactionDao();

	public Object getAllTransactions() {
		try {
			return dao.getAll();
		} catch (SQLException e) {
			LOG.error(e);
			return new ErrorResponse("Error connecting to database");
		}
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
