package second_assignment;

import java.util.Arrays;

public class Sort {
	
	private int quickList[];
	private int mergeList[];
	private final int originalList[];
	
	public Sort(int list[]){
		this.originalList = list;
		this.mergeList = list.clone();
		this.quickList = list.clone();
	}

	public static void main(String[] args) {
		int[] a = {2, 10, 4, 5, 56, 23, 14, 1, 85};
		Sort s = new Sort(a);
		s.mergeSort();
		s.quickSort();
		for (int i = 0; i < s.getMergeList().length; i++) {
			System.out.print(s.getMergeList()[i] + " ");
		}
	}
	
	public void mergeSort(){
		mergeList = mergeSort(originalList);
	}
	
	private int[] mergeSort(int[] list){
		int size = list.length;
		if(size <= 3)
			list = baseCase(list);
		else{
			int halfSize = size/2;
			int[] firstHalf;
			int[] secondHalf;
			firstHalf = mergeSort(Arrays.copyOfRange(list, 0, halfSize));
			secondHalf = mergeSort(Arrays.copyOfRange(list, halfSize, list.length));
			list = mergeLists(firstHalf, secondHalf, size);
			
		}
		return list;
	}
	
	private int[] mergeLists(int[] firstHalf, int[] secondHalf, int size) {
		int mergedList[] = new int[size];
		return mergedList;
	}

	public void quickSort(){
		quickList[2] = 50;
	}
	
	private int[] baseCase(int[] list){
		for (int i = 0; i < list.length - 1; i++) {
			if(list[i] > list[i + 1]){
				int temp = list[i+1];
				list[i+1] = list[i];
				list[i] = temp;
			}
		}
		return list;
	}
	
//	private int[] baseCase(int[] list, int one, int two, int three){
//		return list;
//	}
	
	public int[] getQuickList(){
		return quickList;
	}
	
	public int[] getMergeList(){
		return mergeList;
	}
	
	public int[] getOriginalList(){
		return originalList;
	}
}
