package parser;

public class MydslParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4276106745365278315L;
	private String message;
	
	public MydslParsingException(String message){
		this.message = message;
	}
	
	public void printStackTrace(){
		System.err.println(message);
		super.printStackTrace();
	}

	public String toString(){
		return MydslParsingException.class + " : " + message;
	}
}
