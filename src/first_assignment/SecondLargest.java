/*
 * Parley Pacheco Martins 1484000
 */
package first_assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SecondLargest {
	private Map<Integer, List<Integer>> tree;
	private File file;
	private String INPUT_FILE = "C:\\Users\\Parley\\Documents\\GitHub\\csc310\\src\\first_assignment\\data.txt";
	private String OUTPUT_FILE = "C:\\Users\\Parley\\Documents\\GitHub\\csc310\\src\\first_assignment\\output.txt";
	/*
	 * I chose to use Integer (the class) instead of int just to keep a pattern,
	 * since I have to use it in the HashMap.
	 */
	private Integer largest;
	private Integer secondLargest;
	private PrintWriter out;

	public SecondLargest() {
		tree = new HashMap<Integer, List<Integer>>();
		file = new File(INPUT_FILE);
	}

	/**
	 * This reads the input file and sends the data to calculate what is
	 * expected
	 */
	public void readFile() {
		Scanner in;
		try {
			in = new Scanner(file);
			out = new PrintWriter(OUTPUT_FILE, "UTF-8");

			while (in.hasNextInt()) {
				getLine(in);
			}
			in.close();
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method gets the line in which the largest element has to be found
	 * 
	 * @param in
	 *            The scanner where the input is (console, file)
	 */
	private void getLine(Scanner in) {
		Integer size = in.nextInt();
		Integer[] numbers = new Integer[size];
		for (int i = 0; i < size; i++) {
			numbers[i] = in.nextInt();
		}
		mountTree(numbers);
		tree.clear();
	}

	/**
	 * This method is called after all is done. Used to print the data in the
	 * required format.
	 * 
	 * @param line
	 *            The Integer array in which the second largest must be found.
	 * @param largestComparisons
	 *            The comparisons to find the largest.
	 * @param secondComparisons
	 *            The comparisons to find the second largest.
	 */
	private void printOutput(Integer[] line, String largestComparisons, String secondComparisons) {

		out.println("============================");
		for (int i = 0; i < line.length; i++) {
			out.print(line[i] + " ");
		}
		out.println("\nFinding largest ...");
		out.println(largestComparisons);
		out.println("Finding 2nd largest ...");
		out.println(secondComparisons);
		out.println("Winner is: " + largest);
		out.println("2nd place: " + secondLargest);
		out.print(printResults());
		out.println("============================");
	}

	private void mountTree(Integer[] line) {
		String largestComparisons = findLargest(line);
		String secondComparisons = findSecondLargest();
		printOutput(line, largestComparisons, secondComparisons);
	}

	/**
	 * After the largest has been found, this calculates the second largest.
	 * 
	 * @return It returns a String with the comparisons.
	 */
	private String findSecondLargest() {
		String comparisons = "";
		try {
			Integer[] candidates = new Integer[tree.get(largest).size()];
			tree.get(largest).toArray(candidates);
			secondLargest = candidates[0];
			for (int i = 1; i < candidates.length; i++) {
				comparisons += "Comparison: " + secondLargest + " to " + candidates[i] + " Winner: ";
				if (secondLargest < candidates[i]) {
					secondLargest = candidates[i];
				}
				comparisons += secondLargest + "\n";
			}
		} catch (NullPointerException e) {
			secondLargest = largest;
		}
		return comparisons;
	}

	/**
	 * This is where the comparisons are made to find the largest in the line.
	 * 
	 * @param line
	 *            The integers where the largest will be found
	 * @return returns a String with all the comparisons
	 */
	private String findLargest(Integer[] line) {
		String comparisons = "";
		List<Integer> nextLevel = new ArrayList<Integer>();
		// Observe that the pace here is 2 instead of 1.
		for (int i = 0; i < line.length; i = i + 2) {
			Integer firstNumber = line[i];
			try {
				Integer secondNumber = line[i + 1];
				Integer winner;
				if (firstNumber > secondNumber) {
					insertInTree(firstNumber, secondNumber);
					nextLevel.add(firstNumber);
					winner = firstNumber;
				} else {
					insertInTree(secondNumber, firstNumber);
					nextLevel.add(secondNumber);
					winner = firstNumber;
				}
				comparisons += writeComparisons(firstNumber, secondNumber, winner);
			} catch (ArrayIndexOutOfBoundsException e) {
				/*
				 * This happens when I reach the last element of an odd sized
				 * array and my i+1 is out of bounds
				 */
				comparisons += writeComparisons(firstNumber, firstNumber, firstNumber);
				nextLevel.add(firstNumber);
			}
		}
		/*
		 * I choose recursion to help with the tournament tree. Since the keys
		 * in the keySet are not ordered (because it's a Set) and in order to
		 * check the level of the tree I would need to check how many losers
		 * each one had, I decided to use the recursion. Every "round" I do a
		 * new list with the winners of that round, then I call the function
		 * again with those numbers. Since the size is reduced by half each
		 * round, the total number of comparisons is the same as if it was
		 * sequential.
		 */
		if (nextLevel.size() == 1)
			largest = nextLevel.get(0);
		else
			comparisons += findLargest(nextLevel.toArray(new Integer[nextLevel.size()]));
		return comparisons;
	}

	private String writeComparisons(Integer firstNumber, Integer secondNumber, Integer winner) {
		String comparison = "Comparison: " + firstNumber + " to " + secondNumber;
		comparison += " Winner: " + winner + "\n";
		return comparison;
	}

	/**
	 * This method is to insert the losers in the tournament tree.
	 * 
	 * @param winner
	 *            The key in the tree.
	 * @param loser
	 *            The number that lost to the key.
	 */
	private void insertInTree(Integer winner, Integer loser) {
		List<Integer> losers = tree.get(winner);
		try {
			losers.add(loser);
		} catch (NullPointerException e) {
			losers = new ArrayList<>();
			losers.add(loser);
		}
		tree.put(winner, losers);
	}

	/**
	 * This is to print the results in the tournament tree.
	 * 
	 * @return It returns a String to be printed by whatever output system being
	 *         used
	 */
	private String printResults() {
		String results = "Results:\n";
		for (Integer winner : tree.keySet()) {
			results += winner + " won against " + tree.get(winner) + " \n";
		}
		return results;
	}
}
