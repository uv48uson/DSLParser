package logging;

import org.apache.log4j.SimpleLayout;
import org.apache.log4j.spi.LoggingEvent;

public abstract class TableLayout extends SimpleLayout{
	protected int TABLE_WIDTH = 100;
	protected int PREFIX_WIDTH = 10;
	protected char HEIGHT_CHAR = '|';
	protected char WIDTH_CHAR = '-';
	
	public int getTABLE_WIDTH() {
		return TABLE_WIDTH;
	}
	public void setTABLE_WIDTH(int tABLE_WIDTH) {
		TABLE_WIDTH = tABLE_WIDTH;
	}
	public int getPREFIX_WIDTH() {
		return PREFIX_WIDTH;
	}
	public void setPREFIX_WIDTH(int pREFIX_WIDTH) {
		PREFIX_WIDTH = pREFIX_WIDTH;
	}
	public char getHEIGHT_CHAR() {
		return HEIGHT_CHAR;
	}
	public void setHEIGHT_CHAR(char hEIGHT_CHAR) {
		HEIGHT_CHAR = hEIGHT_CHAR;
	}
	public char getWIDTH_CHAR() {
		return WIDTH_CHAR;
	}
	public void setWIDTH_CHAR(char wIDTH_CHAR) {
		WIDTH_CHAR = wIDTH_CHAR;
	}
	
	protected String getFormattedLevel(LoggingEvent event){
		String message = "";
		message += event.getLevel();
		for(int i = 0; i<10-event.getLevel().toString().length(); i++){
			message += " ";
		}
		return message;
	}
	
	protected abstract String buildTable(LoggingEvent event);
}
