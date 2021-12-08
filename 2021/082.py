import aoc

def get_num(line):
    seg_num = [set(num) for num in line[:58].split(" ")]
    displays = [set(num) for num in line[61:].split(" ")]

    numbers = [None]*10

    # allocate obvious numbers
    for num in seg_num:
        if len(num) == 2:
            numbers[1] = num
        elif len(num) == 3:
            numbers[7] = num
        elif len(num) == 4:
            numbers[4] = num
        elif len(num) == 7:
            numbers[8] = num

    seg_num = [num for num in seg_num if len(num) in (5,6)]

    # find 6: 6 segments where one segment of number 7 isn't present
    candidates = [num for num in seg_num if len(num) == 6]
    for candidate in candidates:
        if len(numbers[7] & candidate) == 2:
            numbers[6] = candidate
            break
    candidates.remove(candidate)
    
    # find 9: 6 segments containing all segments of number 4
    for candidate in candidates:
        if len(numbers[4] & candidate) == 4:
            numbers[9] = candidate
            break
    candidates.remove(candidate)
    
    # save 0: remaining number with 6 segments
    numbers[0] = candidates[0]

    # find 3: 5 segments containing both segments of number 1
    candidates = [num for num in seg_num if len(num) == 5]
    for candidate in candidates:
        if len(numbers[1] & candidate) == 2:
            numbers[3] = candidate
            break
    candidates.remove(candidate)

    # find 2: 5 segments with two overlapping with number 4
    for candidate in candidates:
        if len(numbers[4] & candidate) == 2:
            numbers[2] = candidate
            break
    candidates.remove(candidate)

    # save 5: remaining number
    numbers[5] = candidates[0]

    # build number
    result = 0
    for digit in range(4):
        result *= 10
        result += numbers.index(displays[digit])
    return result

print(sum([get_num(line) for line in aoc.lines("08_in.txt")]))


        
