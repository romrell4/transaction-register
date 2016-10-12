import Controller.TransactionController;
import Model.PaymentType;
import Model.Transaction;
import com.google.gson.Gson;
import spark.Request;

import static spark.Spark.*;

public class Main {
	private static Gson gson = new Gson();
	private static TransactionController controller = new TransactionController();

	public static void main(String[] args) {
		int port = getHerokuAssignedPort();
		System.out.println("Listening on port " + port);
		port(port);

		get("/transactions", (request, response) -> json(controller.getAllTransactions()));
		get("/transactions/:id", (request, response) -> {
			return json(controller.getTransactionById(Integer.parseInt(request.params(":id"))));
		});
		get("/transactions/:type", (req, res) -> {
			PaymentType type = PaymentType.valueOf(req.params(":type").toUpperCase());
			return json(controller.getAllTransactionsByPaymentType(type));
		});
		post("/transactions", (request, response) -> {
			Transaction transaction = getTxFromRequest(request);
			return json(controller.createTransaction(transaction));
		});
		put("/transactions/:id", (request, response) -> {
			Transaction transaction = getTxFromRequest(request);
			return json(controller.updateTransaction(Integer.parseInt(request.params(":id")), transaction));
		});
		delete("/transactions/:id", (request, response) -> {
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
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
	}
}
