import java.io.File;
import java.util.Scanner;

/**
 * Author: PRASANT
 * Date: 02/01/25
 */

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Scanner scanner = new Scanner(System.in);


		System.out.println("Enter folder path: ");
		String folderPath = scanner.nextLine();

		System.out.println("Enter keyword to search: ");
		String keyword = scanner.nextLine();

		File folder = new File(folderPath);

		// check if folder exist
		if (!folder.exists() || !folder.isDirectory()) {
			System.out.println("Invalid folder path!");
			System.exit(0);
		}

		// start searching
		FileSearcher searcher = new FileSearcher(folder, keyword);
		searcher.startSearch();
	}
}
