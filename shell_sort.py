def shell_sort(array):
	print(array)
	print('\n')
	gaps = [10, 7, 4, 1]
	for gap in gaps:
		print(gap)
		for i in range(0, gap):
			for j in range(len(array) - 1 - i, 0, -gap):
				print(array, j)
				key = array[j]
				k = j - gap;
				while k >= 0 and key < array[k]:
					array[k + gap] = array[k]
					array[k] = key
					k = k - gap
	print(array)

