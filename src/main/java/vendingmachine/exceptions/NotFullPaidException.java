package vendingmachine.exceptions;

public class NotFullPaidException extends RuntimeException {

	public NotFullPaidException() {
		super("You need to pay more!");
	}

}
