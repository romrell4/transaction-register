package Controller;

import Dao.TransactionDao;
import Model.Errors.BadRequestException;
import Model.PaymentType;
import Model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by eric on 9/29/16.
 */
public class TransactionController extends BaseController {
	private static final Logger LOG = LogManager.getLogger(TransactionController.class);

	private TransactionDao dao = new TransactionDao();

	public List<Transaction> getAllTransactions(String paymentTypeStr, String monthStr, String yearStr) {
		LOG.info("/transactions GET");
		PaymentType paymentType = paymentTypeStr == null ? null : toPaymentType(paymentTypeStr);
		Integer month = monthStr == null ? null : toInt(monthStr);
		Integer year = yearStr == null ? null : toInt(yearStr);
		return dao.getAll(paymentType, month, year);
	}

	public Transaction getTransactionById(String transactionIdStr) {
		LOG.info("/transactions/:id GET");
		int transactionId = toInt(transactionIdStr);
		return dao.getById(transactionId);
	}

	public Transaction createTransaction(Transaction transaction) {
		LOG.info("/transactions POST");
		return dao.save(transaction);
	}

	public String deleteTransaction(String transactionIdStr) {
		LOG.info("/transactions/:id DELETE");
		int transactionId = toInt(transactionIdStr);
		dao.deleteById(transactionId);
		return "Success";
	}

	public String updateTransaction(String transactionIdStr, Transaction transaction) {
		LOG.info("/transactions/:id PUT");
		int transactionId = toInt(transactionIdStr);
		dao.update(transactionId, transaction);
		return "Success";
	}
}
