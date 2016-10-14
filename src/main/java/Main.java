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

		get("/transactions", (request, response) -> {
			try {
				return json(controller.getAllTransactions());
			} catch (InternalServerException e) {
				LOG.error(e);
				response.status(500);
				return json(e.error);
			}
		});
		get("/transactions/:id", (request, response) -> {
			try {
				return json(controller.getTransactionById(Integer.parseInt(request.params(":id"))));
			} catch (NotFoundException e) {
				LOG.error(e);
				response.status(404);
				return json(e.getMessage());
			} catch (InternalServerException e) {
				LOG.error(e);
				response.status(500);
				return json(e.error);
			}
		});
		get("/transactions/:type", (request, response) -> {
			try {
				return json(controller.getAllTransactionsByPaymentType(PaymentType.valueOf(request.params(":type"))));
			} catch (InternalServerException e) {
				LOG.error(e);
				response.status(500);
				return json(e.error);
			}
		});
		post("/transactions", (request, response) -> {
			try {
				return json(controller.createTransaction(getTxFromRequest(request)));
			} catch (InternalServerException e) {
				LOG.error(e);
				response.status(500);
				return json(e.error);
			}
		});
		put("/transactions/:id", (request, response) -> {
			try {
				return json(controller.updateTransaction(Integer.parseInt(request.params(":id")), getTxFromRequest(request)));
			} catch (NotFoundException e) {
				LOG.error(e);
				response.status(404);
				return json(e.error);
			} catch (InternalServerException e) {
				LOG.error(e);
				response.status(500);
				return json(e.error);
			}
		});
		delete("/transactions/:id", (request, response) -> {
			try {
				return json(controller.deleteTransaction(Integer.parseInt(request.params(":id"))));
			} catch (NotFoundException e) {
				LOG.error(e);
				response.status(404);
				return json(e.error);
			} catch (InternalServerException e) {
				LOG.error(e);
				response.status(500);
				return json(e.error);
			}
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
