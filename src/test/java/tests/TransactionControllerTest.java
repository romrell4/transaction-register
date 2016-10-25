package tests;

import Controller.TransactionController;
import Model.Errors.BadRequestException;
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
		List<Transaction> txs = controller.getAllTransactions(null, null, null);
		assertTrue(txs.size() > 0);

		txs = controller.getAllTransactions("credit", null, null);
		assertTrue(txs.size() > 0);

		txs = controller.getAllTransactions("DEBIT", null, null);
		assertTrue(txs.size() > 0);

		txs = controller.getAllTransactions("SaViNgS", null, null);
		assertTrue(txs.size() > 0);

		txs = controller.getAllTransactions("PERMANENT_SAVINGS", null, null);
		assertTrue(txs.size() > 0);

		try {
			txs = controller.getAllTransactions("permanent savings", null, null);
			assertTrue(false);
		} catch (BadRequestException e) {
			assertTrue(true);
		}

		Transaction tx = controller.getTransactionById(String.valueOf(txs.get(0).getTransactionId()));
		assertTrue(tx.getTransactionId() == txs.get(0).getTransactionId());

		try {
			controller.getTransactionById("hello");
			assertTrue(false);
		} catch (BadRequestException e) {
			assertTrue(true);
		}

	}
}
