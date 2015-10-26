def insertion_sort(array):
	print(array)
	for i in range(1, len(array)):
		key = array[i]
		j = i - 1;
		while j >= 0 and key < array[j]:
			array[j + 1] = array[j]
			array[j] = key
			j = j - 1
	print(array)

a = [0, 1, 0, 1]