import Controller.TransactionController;
import Model.Errors.InternalServerException;
import Model.Errors.NotFoundException;
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

		get("/tx", (req, res) -> controller.getAllTransactions(req.queryParams("type") != null ? PaymentType.valueOf(req.queryParams("type")) : null), gson::toJson);
		get("/tx/:id", (req, res) -> controller.getTransactionById(Integer.parseInt(req.params(":id"))), gson::toJson);
		post("/tx", (req, res) -> controller.createTransaction(getTxFromRequest(req)), gson::toJson);
		put("/tx/:id", (req, res) -> controller.updateTransaction(Integer.parseInt(req.params(":id")), getTxFromRequest(req)), gson::toJson);
		delete("/tx/:id", (req, res) -> controller.deleteTransaction(Integer.parseInt(req.params(":id"))), gson::toJson);

		exception(NotFoundException.class, (e, request, response) -> {
			LOG.error(e);
			response.status(404);
			response.body(gson.toJson(((NotFoundException) e).error));
		});
		exception(InternalServerException.class, (e, request, response) -> {
			LOG.error(e);
			response.status(500);
			response.body(gson.toJson(((InternalServerException) e).error));
		});
	}

	private static Transaction getTxFromRequest(Request request) {
		return gson.fromJson(request.body(), Transaction.class);
	}

	private static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		return processBuilder.environment().get("PORT") != null ? Integer.parseInt(processBuilder.environment().get("PORT")) : 4567;
	}
}
