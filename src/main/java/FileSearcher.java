import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author: PRASANT
 * Date: 02/01/25
 */

public class FileSearcher {
	private final File folder;
	private final String keyword;
	private final ConcurrentHashMap<String, Boolean> results = new ConcurrentHashMap<>();

	public FileSearcher(File folder, String keyword) {
		this.folder = folder;
		this.keyword = keyword;
	}


	public void startSearch() throws InterruptedException {
		File[] files = folder.listFiles(((dir, name) -> name.endsWith(".txt")));

		if (files == null || files.length == 0) {
			System.out.println("No .txt files found in the folder!");
			return;
		}

		int threadPoolSize = Math.min(files.length, Runtime.getRuntime().availableProcessors());
		ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

		for (File file : files) {
			executorService.submit(new SearchTask(file, keyword, results));
		}

		// Shutdown executor and await termination
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
				System.err.println("Timeout: Some tasks did not finish within the allotted time.");
			}
		} catch (InterruptedException e) {
			System.err.println("Search interrupted: " + e.getMessage());
			Thread.currentThread().interrupt(); // Restore interrupt status
		}

		// print results
		results.forEach((file, found) -> {
			System.out.println(file + ": " + (found ? "Keyword Found" : "Not Found"));
		});
	}
}
