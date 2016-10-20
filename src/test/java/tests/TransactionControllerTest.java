package tests;

import Controller.TransactionController;
import Model.PaymentType;
import Model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by eric on 10/20/16.
 */
@RunWith(JUnit4.class)
public class TransactionControllerTest {
	private static final Logger LOG = LogManager.getLogger(TransactionControllerTest.class);

	private TransactionController controller = new TransactionController();

	@Test
	public void test1() {
		List<Transaction> txs = controller.getAllTransactions(null);
		assertTrue(txs.size() > 0);

		txs = controller.getAllTransactions(PaymentType.CREDIT);
		assertTrue(txs.size() > 0);

		txs = controller.getAllTransactions(PaymentType.DEBIT);
		assertTrue(txs.size() > 0);

		txs = controller.getAllTransactions(PaymentType.SAVINGS);
		assertTrue(txs.size() > 0);

		txs = controller.getAllTransactions(PaymentType.PERMANENT_SAVINGS);
		assertTrue(txs.size() > 0);

		Transaction tx = controller.getTransactionById(txs.get(0).getTransactionId());
		assertTrue(tx.getTransactionId() > txs.get(0).getTransactionId());
	}
}
