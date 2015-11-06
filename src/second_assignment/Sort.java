package second_assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * This class has two sort algorithms implemented, quick and merge. You
 * initialize the class with the list to be sorted. You can run each sort and
 * then run the statistics (separated stages) or run the methods that do all
 * that for you (run and print the results).
 * 
 * Getters are provided to access the lists (original, mergeSort, quickSort),
 * but once initialized, the original list (and consequently the others) cannot
 * be modified.
 * 
 * To change the input or/and the output file change their address in the main
 * function and recompile.
 * 
 * @author Parley Pacheco Martins 1484000
 * October 31st, 2015
 *
 */
public class Sort {

	private static PrintStream out;

	private int quickKeyComp;
	private int quickKeyMoves;
	private long quickTime;
	private int quickList[];

	private int mergeKeyComp;
	private int mergeKeyMoves;
	private long mergeTime;
	private int mergeList[];

	private final int originalList[];

	/**
	 * This constructs a class Sort. It has two types of sort implemented, quick
	 * and merge.
	 * 
	 * @param list
	 *            The original list to be sorted.
	 */
	public Sort(int[] list) {
		this.originalList = list;
		this.mergeList = list.clone();
		this.quickList = list.clone();
	}

	/**
	 * The main function. Runs the sorts and prints the results in an output
	 * file.
	 * 
	 * @param args
	 *            Standard parameter
	 */
	public static void main(String[] args) {
		String FOLDER = "C:\\src\\csc310\\src";
		String INPUT_FILE = FOLDER + "\\second_assignment\\data.txt";
		String OUTPUT_FILE = FOLDER + "\\second_assignment\\output.txt";
		Scanner in;
		File file = new File(INPUT_FILE);
		try {
			in = new Scanner(file);
			out = new PrintStream(OUTPUT_FILE, "UTF-8");
			while (in.hasNextInt()) {
				int[] list = getLine(in);
				Sort sortAlgorithms = new Sort(list);
				sortAlgorithms.printMergeSortResults();
				sortAlgorithms.printQuickSortResults();
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
	 * This sorts the list using merge sort. It resets all counters and
	 * calculate the time the sort takes to run.
	 */
	public void mergeSort() {
		mergeKeyComp = 0;
		mergeKeyMoves = 0;
		mergeTime = System.nanoTime();
		mergeSort(0, originalList.length - 1);
		mergeTime = System.nanoTime() - mergeTime;
	}

	/**
	 * This sorts the list using quick sort. It resets all counters and
	 * calculate the time the sort takes to run.
	 */
	public void quickSort() {
		quickKeyComp = 0;
		quickKeyMoves = 0;
		quickTime = System.nanoTime();
		quickSort(0, originalList.length - 1);
		quickTime = System.nanoTime() - quickTime;
	}

	/**
	 * This prints the key movements, key comparisons and execution time of
	 * merge sort
	 */
	public void printMergeStatics() {
		out.println("mergeSort: ");
		printStatics(mergeKeyComp, mergeKeyMoves, mergeTime);
	}

	/**
	 * This prints the key movements, key comparisons and execution time of
	 * quick sort
	 */
	public void printQuickStatics() {
		out.println("quickSort: ");
		printStatics(quickKeyComp, quickKeyMoves, quickTime);
	}

	/**
	 * This gets the list sorted by quick sort.
	 * 
	 * @return It returns an array of integers
	 */
	public int[] getQuickList() {
		return quickList;
	}

	/**
	 * This gets the list sorted by merge sort.
	 * 
	 * @return It returns an array of integers
	 */
	public int[] getMergeList() {
		return mergeList;
	}

	/**
	 * This gets the unsorted list.
	 * 
	 * @return It returns an array of integers
	 */
	public int[] getOriginalList() {
		return originalList;
	}

	/**
	 * This prints the original list, runs quick sort and prints its statistics
	 * and the sorted list
	 */
	public void printQuickSortResults() {
		out.println("===================================");
		out.print("Running QuickSort on list: ");
		printList(getOriginalList());
		quickSort();
		out.print("Finished sort: ");
		printList(getQuickList());
		printQuickStatics();
		out.println("===================================");
	}

	/**
	 * This prints the original list, runs merge sort and prints its statistics
	 * and the sorted list
	 */
	public void printMergeSortResults() {
		out.println("===================================");
		out.print("Running MergeSort on list: ");
		printList(getOriginalList());
		mergeSort();
		out.print("Finished sort: ");
		printList(getMergeList());
		printMergeStatics();
		out.println("===================================");
	}

	/**
	 * This reads an input line and returns the correspondent array.
	 * 
	 * @param in
	 *            The Scanner where the input is
	 * @return an array of integers
	 */
	private static int[] getLine(Scanner in) {
		Integer size = in.nextInt();
		int[] numbers = new int[size];
		for (int i = 0; i < size; i++) {
			numbers[i] = in.nextInt();
		}
		return numbers;
	}

	/**
	 * The actual sort algorithm. It's a recursive algorithm that is first
	 * called from the public interface with the first and last positions of the
	 * original list.
	 * 
	 * @param list
	 *            The unsorted list
	 * @return a sorted array of integers
	 */
	private void mergeSort(int first, int last) {
		printList(mergeList, first, last);
		if (last - first < 3)
			mergeList = baseCase(mergeList, first, first + 1, last);
		else {
			int middle = (first + last) / 2;
			mergeSort(first, middle);
			mergeSort(middle + 1, last);
			mergeList = mergeLists(first, middle, middle + 1, last);
		}
		out.print("====>");
		printList(mergeList);
		out.print("\n");
	}

	/**
	 * This merges the lists after the merge sort runs.
	 * 
	 * @param firstHalf
	 *            an array of integers with the first half of the entire list
	 * @param secondHalf
	 *            an array of integers with the second half of the entire list
	 * @param size
	 *            the size that the merged list has to have
	 * @return Returns the an array of integers with two sorted lists merged
	 */
	private int[] mergeLists(int first1, int last1, int first2, int last2) {
		int mergedList[] = mergeList.clone();
		int mergedIt = first1;
		while (first1 <= last1 && first2 <= last2) {
			if (mergeList[first1] < mergeList[first2]) {
				mergedList[mergedIt] = mergeList[first1];
				first1++;
			} else {
				mergedList[mergedIt] = mergeList[first2];
				first2++;
			}
			mergeKeyComp++;
			mergeKeyMoves++;
			mergedIt++;
		}
		int smallestIt = 0;
		int last = 0;
		if (first1 > first2) {
			smallestIt = first2;
			last = last2;
		} else {
			smallestIt = first1;
			last = last1;
		}
		while (smallestIt <= last) {
			mergedList[mergedIt] = mergeList[smallestIt];
			smallestIt++;
			mergedIt++;
			mergeKeyMoves++;
		}
		return mergedList;
	}

	/**
	 * The base case to comparisons. It's called when a list has size 3 or less.
	 * It's also used in the median of three, and that's why one has to pass the
	 * three positions where the sort must happen.
	 * 
	 * @param list
	 *            The small list to be sorted.
	 * @param pos1
	 *            The first position to be sorted.
	 * @param pos2
	 *            The second position to be sorted.
	 * @param pos3
	 *            The third position to be sorted.
	 * @return the same list that came as parameter with the positions passed
	 *         sorted. If the size is 3 or less the list is completely sorted.
	 */
	private int[] baseCase(int[] list, int pos1, int pos2, int pos3) {
		if (list.length > 1 && pos3 - pos1 >= 1) {
			if (list[pos1] > list[pos2]) {
				swap(list, pos1, pos2);
				quickKeyMoves += 3;
				mergeKeyMoves += 3;
			}
			if (list[pos2] > list[pos3]) {
				swap(list, pos2, pos3);
				quickKeyMoves += 3;
				mergeKeyMoves += 3;
			}
			if (list[pos2] < list[pos1]) {
				swap(list, pos2, pos1);
				quickKeyMoves += 3;
				mergeKeyMoves += 3;
			}
			quickKeyComp += 3;
			mergeKeyComp += 3;
		}
		return list;
	}

	/**
	 * Swaps two positions in a given list.
	 * 
	 * @param list
	 *            The list in which the swap must happen.
	 * @param pos1
	 *            The position of the first element to be swapped.
	 * @param pos2
	 *            The position of the second element to be swapped.
	 * @return the given list with the two elements in the given positions
	 *         swapped.
	 */
	private int[] swap(int[] list, int pos1, int pos2) {
		int temp = list[pos1];
		list[pos1] = list[pos2];
		list[pos2] = temp;
		quickKeyMoves += 3;
		mergeKeyMoves += 3;
		return list;
	}

	/**
	 * The actual quick sort algorithm. It's recursive and the first call
	 * happens on {@link Sort#quickSort()}
	 * 
	 * @param first
	 *            the position of the first element of the list to be sorted.
	 * @param last
	 *            the position of the last element of the list to be sorted.
	 */
	private void quickSort(final int first, final int last) {
		printList(quickList, first, last);
		if ((last - first) < 3) {
			baseCase(quickList, first, first + 1, last);
		} else {
			int pivotPosition = (first + last) / 2;

			baseCase(quickList, first, pivotPosition, last);
			swap(quickList, first, pivotPosition);
			pivotPosition = first;
			int pivot = quickList[pivotPosition];
			for (int low = first + 1; low < last; low++) {
				if (quickList[low] < pivot) {
					pivotPosition++;
					swap(quickList, low, pivotPosition);
					quickKeyComp += 1;
				}
			}
			swap(quickList, first, pivotPosition);
			quickSort(first, pivotPosition - 1);
			quickSort(pivotPosition + 1, last);
		}
		out.print("====>");
		printList(quickList, first, last);
		out.println();
	}

	/**
	 * A wrapper to print the whole list.
	 * 
	 * @param list
	 *            list to be printed.
	 */
	private void printList(final int[] list) {
		printList(list, 0, list.length - 1);
	}

	/**
	 * Prints the given list, from a given start point to a given end point
	 * 
	 * @param list
	 *            the list to be printed
	 * @param begin
	 *            the first element to be printed.
	 * @param end
	 *            the last position of the list to be printed.
	 */
	private void printList(final int[] list, int begin, int end) {
		out.print("[ ");
		for (int i = begin; i <= end; i++) {
			out.print(list[i] + " ");
		}
		out.println("] ");
	}

	/**
	 * Prints the statistics of the sort algorithms.
	 * 
	 * @param keyComp
	 *            The number of key comparisons made by an algorithm.
	 * @param keyMoves
	 *            The number of key movements made by an algorithm.
	 * @param time
	 *            The time an algorithm took to run.
	 */
	private void printStatics(int keyComp, int keyMoves, long time) {
		out.println("\t key comparisons: " + keyComp);
		out.println("\t key movements: " + keyMoves);
		out.println("\t time: " + time + " nanoseconds\n");
	}
}
