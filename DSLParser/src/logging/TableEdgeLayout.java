package logging;

import org.apache.log4j.spi.LoggingEvent;

import javafx.util.Pair;

public class TableEdgeLayout extends TableLayout {
	
	public String format(LoggingEvent event){
		StringBuffer message = new StringBuffer();
		
		Pair<String,String> messageObject = (Pair<String,String>) event.getMessage();
		if(messageObject.getValue().equals("Start")){
			message.append("\n");
		}
		
		message.append(getFormattedLevel(event));
		message.append(buildTable(event));
		message.append("\n");
		
		if(messageObject.getValue().equals("End")){
			message.append("\n");
		}		
		
		return message.toString();
	}
	
	public String buildTable(LoggingEvent event){
		StringBuffer message = new StringBuffer();
		
		Pair<String,String> messageObject = (Pair<String,String>) event.getMessage();
		String originalMessage = messageObject.getKey();
		
		int prefixWidth = (int) Math.ceil((TABLE_WIDTH-originalMessage.length()-2)/2d);
		int postfixWidth = (int) Math.floor((TABLE_WIDTH-originalMessage.length()-2)/2d);
		
		message.append(HEIGHT_CHAR);
		for(int i = 0; i<prefixWidth; i++){
			message.append(WIDTH_CHAR);
		}
		
		message.append(originalMessage);
		
		for(int i = 0; i<postfixWidth; i++){
			message.append(WIDTH_CHAR);
		}
		message.append(HEIGHT_CHAR);
		
		return message.toString();
	}

}
