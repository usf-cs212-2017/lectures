import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionDemo {

	private static int calcPercentage(int total, int possible) {
		return 100 * total / possible;
	}

	private static void printResult(int total, int possible, int percentage) {
		System.out.printf("%d/%d = %d%% %n", total, possible, percentage);
	}

	public static void run() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter total points earned: ");
		earned = scanner.nextInt();

		System.out.print("Enter total points possible: ");
		possible = scanner.nextInt();

		percentage = calcPercentage(earned, possible);
		printResult(earned, possible, percentage);

		scanner.close();
		System.out.println("[done]");
	}

	public static void main(String[] args) {
		// Earned: 2, Possible: 4, Percentage: 30%
		// Earned: a, Error
		// Earned: 2, Possible: a, Error
		// Earned: 2, Possible: 0, Error
		// Earned: 2, Possible: -1, Error?

		run();
	}
}
