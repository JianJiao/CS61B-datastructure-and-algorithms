public class BadTransactionException extends Exception {
	public int amount;	

	public BadTransactionException(int badAmount){
		super("Invalid amount: "+ badAmount);
		amount = badAmount;
	}
}
