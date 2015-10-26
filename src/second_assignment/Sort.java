package second_assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

public class Sort {

	private static PrintWriter out;

	private int quickKeyComp;
	private int quickKeyMoves;
	private long quickTime;
	private int quickList[];

	private int mergeKeyComp;
	private int mergeKeyMoves;
	private long mergeTime;
	private int mergeList[];

	private final int originalList[];

	public Sort(int list[]) {
		this.originalList = list;
		this.mergeList = list.clone();
		this.quickList = list.clone();
	}

	public static void main(String[] args) {
		readFile();
	}

	public void mergeSort() {
		mergeKeyComp = 0;
		mergeKeyMoves = 0;
		mergeTime = System.nanoTime();
		mergeList = mergeSort(originalList);
		mergeTime = System.nanoTime() - mergeTime;
	}

	public void quickSort() {
		quickKeyComp = 0;
		quickKeyMoves = 0;
		quickTime = System.nanoTime();
		// quickList = quickSort(originalList);
		quickTime = System.nanoTime() - quickTime;
	}

	public void printMergeStatics() {
		out.println("mergeSort: ");
		printStatics(mergeKeyComp, mergeKeyMoves, mergeTime);
	}

	public void printQuickStatics() {
		out.println("quickSort: ");
		printStatics(quickKeyComp, quickKeyMoves, quickTime);
	}

	public int[] getQuickList() {
		return quickList;
	}

	public int[] getMergeList() {
		return mergeList;
	}

	public int[] getOriginalList() {
		return originalList;
	}

	private static void readFile() {
		String FOLDER = "C:\\Users\\Parley\\Documents\\GitHub\\csc310\\src";
		String INPUT_FILE = FOLDER + "\\second_assignment\\data.txt";
		String OUTPUT_FILE = FOLDER + "\\second_assignment\\output.txt";
		Scanner in;
		File file = new File(INPUT_FILE);
		try {
			in = new Scanner(file);
			out = new PrintWriter(OUTPUT_FILE, "UTF-8");
			while (in.hasNextInt()) {
				int[] list = getLine(in);
				Sort sortAlgorithms = new Sort(list);
				out.print("Running MergeSort on list: ");
				sortAlgorithms.printList(list);
				sortAlgorithms.mergeSort();
				out.print("Finished sort: ");
				sortAlgorithms.printList(sortAlgorithms.getMergeList());
				sortAlgorithms.printMergeStatics();
			}
			in.close();
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	private static int[] getLine(Scanner in) {
		Integer size = in.nextInt();
		int[] numbers = new int[size];
		for (int i = 0; i < size; i++) {
			numbers[i] = in.nextInt();
		}
		return numbers;
	}

	private int[] mergeSort(int[] list) {
		// printList(list);
		int size = list.length;
		if (size <= 3)
			list = baseCase(list);
		else {
			int halfSize = (size / 2);
			int[] firstHalf = Arrays.copyOfRange(list, 0, halfSize);
			int[] secondHalf = Arrays.copyOfRange(list, halfSize, size);
			firstHalf = mergeSort(firstHalf);
			secondHalf = mergeSort(secondHalf);
			list = mergeLists(firstHalf, secondHalf, size);
		}
		return list;
	}

	private int[] mergeLists(int[] firstHalf, int[] secondHalf, int size) {
		int mergedList[] = new int[size];
		int firstIt = 0;
		int secondIt = 0;
		int mergedIt = 0;
		while (firstIt < firstHalf.length && secondIt < secondHalf.length) {
			if (firstHalf[firstIt] < secondHalf[secondIt]) {
				mergedList[mergedIt] = firstHalf[firstIt];
				firstIt++;
			} else {
				mergedList[mergedIt] = secondHalf[secondIt];
				secondIt++;
			}
			mergeKeyComp++;
			mergeKeyMoves++;
			mergedIt++;
		}
		int smallestIt = 0;
		int[] unfinishedList;
		int halfSize = 0;
		if (firstIt > secondIt) {
			smallestIt = secondIt;
			unfinishedList = secondHalf;
			halfSize = secondHalf.length;
		} else {
			smallestIt = firstIt;
			unfinishedList = firstHalf;
			halfSize = firstHalf.length;
		}
		while (smallestIt < halfSize) {
			mergedList[mergedIt] = unfinishedList[smallestIt];
			smallestIt++;
			mergedIt++;
			mergeKeyMoves++;
		}
		return mergedList;
	}

	private int[] baseCase(int[] list) {
		for (int i = 0; i < list.length - 1; i++) {
			if (list[i] > list[i + 1]) {
				int temp = list[i + 1];
				list[i + 1] = list[i];
				list[i] = temp;
				mergeKeyMoves += 3;
			}
			mergeKeyComp++;
		}
		if (list.length > 1 && list[0] > list[1]) {
			int temp = list[0];
			list[0] = list[1];
			list[1] = temp;
			mergeKeyMoves += 3;
		}
		mergeKeyComp++;
		return list;
	}

	private void printList(final int[] list) {
		out.print("[ ");
		for (int i = 0; i < list.length; i++) {
			out.print(list[i] + " ");
		}
		out.println("] ");
	}

	private void printStatics(int keyComp, int keyMoves, long time) {
		out.println("\t key comparisons: " + keyComp);
		out.println("\t key movements: " + keyMoves);
		out.println("\t time: " + time + " nanoseconds\n");
	}
}
