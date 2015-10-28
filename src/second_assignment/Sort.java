package second_assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

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

	public Sort(int list[]) {
		this.originalList = list;
		this.mergeList = list.clone();
		this.quickList = list.clone();
	}

	public static void main(String[] args) {
		readFileAndRunSorts();
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
		quickSort(0, originalList.length - 1);
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

	private static void readFileAndRunSorts() {
		String FOLDER = "C:\\Users\\Parley\\Documents\\GitHub\\csc310\\src";
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

	public void printQuickSortResults() {
		out.print("Running QuickSort on list: ");
		printList(originalList);
		quickSort();
		out.print("Finished sort: ");
		printList(getQuickList());
		printQuickStatics();
	}

	public void printMergeSortResults() {
		out.print("Running MergeSort on list: ");
		printList(originalList);
		mergeSort();
		out.print("Finished sort: ");
		printList(getMergeList());
		printMergeStatics();
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
		return baseCase(list, 0, list.length);
	}

	private int[] baseCase(int[] list, int begin, int end) {
		for (int i = begin; i < end - 1; i++) {
			if (list[i] > list[i + 1]) {
				int temp = list[i + 1];
				list[i + 1] = list[i];
				list[i] = temp;
				mergeKeyMoves += 3;
				quickKeyMoves += 3;
			}
			mergeKeyComp++;
			quickKeyComp++;
		}
		if (list.length > 1 && list[0] > list[1]) {
			int temp = list[0];
			list[0] = list[1];
			list[1] = temp;
			mergeKeyMoves += 3;
			quickKeyMoves += 3;
		}
		mergeKeyComp++;
		quickKeyComp++;
		return list;
	}

	private void medianOfThree(int pos1, int pos2, int pos3) {
		if (quickList.length > 1 && pos3 - pos1 > 1) {
			if (quickList[pos1] > quickList[pos2]) {
				swap(pos1, pos2);
				quickKeyMoves += 3;
			}
			if (quickList[pos2] > quickList[pos3]) {
				swap(pos2, pos3);
				quickKeyMoves += 3;
			}
			if (quickList[pos2] < quickList[pos1]) {
				swap(pos2, pos1);
				quickKeyMoves += 3;
			}
			quickKeyComp += 3;
		}
	}

	private void swap(int pos1, int pos2) {
		int temp = quickList[pos1];
		quickList[pos1] = quickList[pos2];
		quickList[pos2] = temp;
		quickKeyMoves += 3;
	}

	private void quickSort(final int begin, final int end) {
		// printList(quickList, begin, end + 1);
		if ((end - begin) < 3) {
//			printList(quickList, begin, end);
			medianOfThree(begin, begin + 1, end);
//			printList(quickList, begin, end);
		} else {
			int pivotPosition = (begin + end) / 2;
			medianOfThree(begin, pivotPosition, end);
			int low = begin + 1;
			int high = end - 1;
//			out.println("Boundaries:" + begin + " - " + end);
//			out.println("Pivot: " + quickList[pivotPosition] + ". Position: " + pivotPosition);
			while (low <= high && low < end) {
				if (low > pivotPosition) {
					swap(low, pivotPosition);
					low = pivotPosition;
					pivotPosition = low + 1;
					quickKeyComp++;
				}
				while (quickList[low] > quickList[pivotPosition]) {
					swap(low, high);
					if (high == pivotPosition) {
						pivotPosition = low;
						// quickKeyMoves++;
					}
					high--;
					quickKeyComp += 2;
				}
				low++;
			}
//			out.println("[after sort] Pivot: " + quickList[pivotPosition] + ". Position: " + pivotPosition);
			quickSort(begin, pivotPosition);
			quickSort(pivotPosition + 1, end);
		}
		// out.print("====>");
		// printList(quickList);
	}

	private void printList(final int[] list) {
		printList(list, 0, list.length);
	}

	private void printList(final int[] list, int begin, int end) {
		out.print("[ ");
		for (int i = begin; i < end; i++) {
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
