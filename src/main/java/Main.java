import Controller.TransactionController;
import Model.PaymentType;
import com.google.gson.Gson;

import static spark.Spark.*;

public class Main {
	private static Gson gson = new Gson();
	private static TransactionController controller = new TransactionController();

	public static void main(String[] args) {
		port(getHerokuAssignedPort());
		get("/hello", (req, res) -> {
			return json(controller.getAllTransactionsByPaymentType(PaymentType.CREDIT));
		});
	}

	private static String json(Object obj) {
		return gson.toJson(obj);
	}

	private static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
	}
}
