package logging;

import org.apache.log4j.spi.LoggingEvent;

import javafx.util.Pair;

public class TableContentLayout extends TableLayout {
	
	public String format(LoggingEvent event){
		StringBuffer message = new StringBuffer();
		message.append(getFormattedLevel(event));
		message.append(buildTable(event));
		message.append("\n");
		return message.toString();
	}
	
	public String buildTable(LoggingEvent event){
		StringBuffer message = new StringBuffer();
		
		Pair<String,String> rowValues = (Pair<String, String>)event.getMessage();
		int firstParameterLength = rowValues.getKey().length();
		int secondParameterLength = rowValues.getValue().length();
		
		int firstRowWidth = (int) Math.ceil((TABLE_WIDTH-2-4-2)/2);
		int secondRowWidth = (int) Math.floor((TABLE_WIDTH-2-4-2)/2);
		
		message.append(HEIGHT_CHAR + " ");
		message.append(rowValues.getKey());
		for(int i = 0; i<firstRowWidth-firstParameterLength; i++){
			message.append(" ");
		}
		
		message.append(" ");
		message.append(HEIGHT_CHAR);
		message.append(HEIGHT_CHAR);
		message.append(" ");
		
		message.append(rowValues.getValue());
		for(int i = 0; i<secondRowWidth-secondParameterLength; i++){
			message.append(" ");
		}
		message.append(" " + HEIGHT_CHAR);
		
		return message.toString();
	}

}
