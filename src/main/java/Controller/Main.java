package Controller;

import Model.Errors.BadRequestException;
import Model.Errors.InternalServerException;
import Model.Errors.NotFoundException;
import Model.PaymentType;
import Model.Transaction;
import Model.TransactionHelper;
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

		get("/transactions", (req, res) -> transactionController.getAllTransactions(req.queryParams("type"), req.queryParams("month"), req.queryParams("year")), gson::toJson);
		get("/transactions/sums", (req, res) -> transactionController.getPaymentTypeSums(), gson::toJson);
		get("/transactions/:id", (req, res) -> transactionController.getTransactionById(req.params(":id")), gson::toJson);
		post("/transactions", (req, res) -> transactionController.createTransaction(getTxFromRequest(req)), gson::toJson);
		put("/transactions/:id", (req, res) -> transactionController.updateTransaction(req.params(":id"), getTxFromRequest(req)), gson::toJson);
		delete("/transactions/:id", (req, res) -> transactionController.deleteTransaction(req.params(":id")), gson::toJson);

		get("/categories", (req, res) -> categoryController.getAllCategoriesForBudget(req.queryParams("categoryId"), req.queryParams("month"), req.queryParams("year")), gson::toJson);
		get("/categories/active", (req, res) -> categoryController.getAllActiveCategories(), gson::toJson);

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

	private static TransactionHelper getTxFromRequest(Request request) {
		return gson.fromJson(request.body(), TransactionHelper.class);
	}

	private static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		return processBuilder.environment().get("PORT") != null ? Integer.parseInt(processBuilder.environment().get("PORT")) : 4567;
	}
}
