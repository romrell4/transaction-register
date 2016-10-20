package Controller;

import Model.Errors.BadRequestException;
import Model.Errors.InternalServerException;
import Model.Errors.NotFoundException;
import Model.PaymentType;
import Model.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;

import static spark.Spark.*;

public class Main {
	private static final Logger LOG = LogManager.getLogger(Main.class);
	private static Gson gson = new GsonBuilder().setDateFormat("MM/dd/yyyy HH:mm:ss").create();
	private static CategoryController categoryController = new CategoryController();
	private static TransactionController transactionController = new TransactionController();

	public static void main(String[] args) {
		int port = getHerokuAssignedPort();
		System.out.println("Listening on port " + port);
		port(port);

		get("/tx", (req, res) -> {
			final String type = req.queryParams("type");
			try {
				final PaymentType paymentType = type != null ? PaymentType.valueOf(type) : null;
				return transactionController.getAllTransactions(paymentType);
			} catch (IllegalArgumentException e) {
				throw new BadRequestException("Invalid payment type " + type, e);
			}
		}, gson::toJson);
		get("/tx/:id", (req, res) -> transactionController.getTransactionById(Integer.parseInt(req.params(":id"))), gson::toJson);
		post("/tx", (req, res) -> transactionController.createTransaction(getTxFromRequest(req)), gson::toJson);
		put("/tx/:id", (req, res) -> transactionController.updateTransaction(Integer.parseInt(req.params(":id")), getTxFromRequest(req)), gson::toJson);
		delete("/tx/:id", (req, res) -> transactionController.deleteTransaction(Integer.parseInt(req.params(":id"))), gson::toJson);

		get("/categories", (req, res) -> {
			String month = req.queryParams("month");
			String year = req.queryParams("year");
			return categoryController.getAllCategoriesByMonth(month != null ? Integer.parseInt(month) : null, year != null ? Integer.parseInt(year) : null);
		}, gson::toJson);

		exception(BadRequestException.class, (e, request, response) -> {
			LOG.error(e);
			response.status(400);
			response.body(gson.toJson(((BadRequestException) e).error));
		});
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
		exception(Exception.class, (e, request, response) -> {
			LOG.error(e);
			response.status(500);
			response.body(e.getMessage());
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
