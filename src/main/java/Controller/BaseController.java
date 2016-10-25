package Controller;

import Model.Errors.BadRequestException;
import Model.PaymentType;

/**
 * Created by eric on 10/25/16.
 */
public class BaseController {
	protected Integer toInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			throw new BadRequestException("Invalid query param " + string);
		}
	}

	protected PaymentType toPaymentType(String string) {
		try {
			return PaymentType.valueOf(string.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new BadRequestException("Invalid query param " + string);
		}
	}
}
