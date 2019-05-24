package pl.minjas;

import pl.minjas.function.Function;
import pl.minjas.function.Polynomial;

import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		List<Integer> options = Arrays.asList(1, 2, 3, 4);
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			int chosenFunction;
			do {
				System.out.println("Choose function or insert 0 to quit:");
				System.out.println("1. Polynomial");
				System.out.println("2. ");
				System.out.println("3. ");
				System.out.println("4. ");
				
				chosenFunction = scanner.nextInt();
				if(chosenFunction == 0)
					return;
			} while (!options.contains(chosenFunction));
			
			Function function = null;
			switch (chosenFunction) {
				case 1:
					List<Double> factors = new ArrayList<>();
					String userInput;
					while (true) {
						System.out.println();
						System.out.println("Insert number to fill polynomial factor (from right to left). Insert 'x' " +
								"to end insertion");
						userInput = scanner.next();
						if(userInput.equals("x")) {
							break;
						} else {
							factors.add(Double.valueOf(userInput));
							System.out.println("Factors: ");
							for(Double d : factors) System.out.print(d + ", ");
						}
					}
					function = new Polynomial(factors);
					break;
				case 2:
					function = null;
					break;
				case 3:
					function = null;
					break;
				case 4:
					function = null;
					break;
			}
			
			System.out.println("How many steps?");
			int steps = scanner.nextInt();
			
			SecantMethod secantMethod = new SecantMethod(function, steps);
			SecantMethod.SecantResult result = secantMethod.getResult(4, 1);
			System.out.println("Result: " + result.getResult());
			
		}
	}
	
}
