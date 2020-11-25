package stackoverflow.Junittest;

public class MessageUtil {
	
	   String message = "Hello World";
	   
	public MessageUtil(String message) {
		this.message = message;
		
	}

	// prints the message
	public String printMessage() {
		System.out.println(message);
		return message;
	}

}
