import Controller.TransactionController;
import Model.Errors.InternalServerException;
import Model.PaymentType;
import Model.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import spark.Request;

import static spark.Spark.*;

public class Main {
	private static final Logger LOG = Logger.getLogger(Main.class);
	private static Gson gson = new GsonBuilder().setDateFormat("MM/dd/yyyy HH:mm:ss").create();
	private static TransactionController controller = new TransactionController();

	public static void main(String[] args) {
		int port = getHerokuAssignedPort();
		System.out.println("Listening on port " + port);
		port(port);

		get("/transactions", (request, response) -> {
			LOG.info("/transactions GET");
			return json(controller.getAllTransactions());
		});
		get("/transactions/:id", (request, response) -> {
			LOG.info("/transactions/:id GET");
			try {
				return json(controller.getTransactionById(Integer.parseInt(request.params(":id"))));
			} catch (Exception e) {
				LOG.error(e);
				return json(new InternalServerException(e.getMessage()));
			}
		});
		get("/transactions/:type", (req, res) -> {
			LOG.info("/transactions/:type GET");
			PaymentType type = PaymentType.valueOf(req.params(":type").toUpperCase());
			return json(controller.getAllTransactionsByPaymentType(type));
		});
		post("/transactions", (request, response) -> {
			LOG.info("/transactions POST");
			Transaction transaction = getTxFromRequest(request);
			return json(controller.createTransaction(transaction));
		});
		put("/transactions/:id", (request, response) -> {
			LOG.info("/transactions/:id PUT");
			try {
				Transaction transaction = getTxFromRequest(request);
				controller.updateTransaction(Integer.parseInt(request.params(":id")), transaction);
				return "Success";
			} catch (Exception e) {
				LOG.error(e);
				return json(e);
			}
		});
		delete("/transactions/:id", (request, response) -> {
			LOG.info("/transactions/:id DELETE");
			controller.deleteTransaction(Integer.parseInt(request.params(":id")));
			return "Success";
		});
	}

	private static String json(Object obj) {
		return gson.toJson(obj);
	}

	private static Transaction getTxFromRequest(Request request) {
		return gson.fromJson(request.body(), Transaction.class);
	}

	private static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		return processBuilder.environment().get("PORT") != null ? Integer.parseInt(processBuilder.environment().get("PORT")) : 4567;
	}
}
