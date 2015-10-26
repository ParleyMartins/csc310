a = [12, 6, 11, 5, 10, 4, 9, 3, 8, 2, 7, 1]

def swap(array, pos_1, pos_2):
    temp = array[pos_1]
    array[pos_1] = array[pos_2]
    array[pos_2] = temp

def base_case(array, pos_1 = 0, pos_2 = None, pos_3 = None):
    if pos_3 and array[pos_3] < array[pos_1]:
        swap(array, pos_3, pos_1)
    if pos_2 and array[pos_1] > array[pos_2]:
        swap(array, pos_1, pos_2)
    if pos_3 and array[pos_2] > array[pos_3]:
        swap(array, pos_2, pos_3)

def quick_sort(array, start = 0, end = None):
    comparisons = 0
    size = len(array)
    if end == None:
        end = size - 1
    print(start, end)
    if size <= 3 or end - start <= 3:
        print(array)
        return comparisons
    else:
        pivot_pos = int((start+end)/2)
        base_case(array, start, pivot_pos, end)
        comparisons += 3
        low = start + 1
        high = end - 1
        pivot = array[pivot_pos]
        while low != high:
            while array[low] > pivot:
                print("--- sort")
                print(high, low)
                print("sort ----")
                comparisons += 1
                swap(array, low, high)
                if high == pivot_pos:
                    pivot_pos = low
                high -= 1
            low += 1
            comparisons += 1
        comparisons += quick_sort(array, start, pivot_pos)
        comparisons += quick_sort(array, pivot_pos + 1, end)

quick_sort(a)
