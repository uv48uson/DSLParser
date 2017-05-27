package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.runner.JUnitCore;
import org.xml.sax.SAXException;

public class ExecutionManager {
	public static void main(String[] args) throws IOException, MydslParsingException, SAXException {
		while (true) {
			System.out.println("Do you want to create/recreate the test files? [y/n]");
			String answer = getLine();
			if (answer.equals("y") || answer.equals("n")) {
				if (answer.equals("y")) {
					CreateTestFiles.createTestFiles();
				}

				while (true) {
					System.out.println("Do you want to run the tests? [y/n]");
					answer = getLine();

					if (answer.equals("y") || answer.equals("n")) {
						if (answer.equals("y")) {
							JUnitCore.main("test.AllTests");
						}
						System.out.println("EXIT");
						return;
					}

					System.out.println("Your input was not correct. Please try again.");
				}
			}
			System.out.println("Your input was not correct. Please try again.");
		}
	}

	public static String getLine() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
}
