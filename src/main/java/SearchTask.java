import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: PRASANT
 * Date: 02/01/25
 */

public class SearchTask implements Runnable{
	private final File file;
	private final String keyword;
	private final ConcurrentHashMap<String, Boolean> results;

	public SearchTask(File file, String keyword, ConcurrentHashMap<String, Boolean> results) {
		this.file = file;
		this.keyword = keyword;
		this.results = results;
	}

	@Override
	public void run() {
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			int lineNumber = 0;
			boolean found = false;

			while ((line = reader.readLine()) != null) {
				lineNumber++;
				if (line.contains(keyword)) {
					found = true;
					break;
				}
			}

			results.put(found ? file.getName() + "(Line " + lineNumber + ")" : file.getName(), found);
		} catch (IOException e) {
			System.err.printf("Error reading file: %s%n", file.getName());
		}
	}
}
