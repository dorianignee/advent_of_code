import aoc
all_lines = aoc.lines("03_in.txt")

lines = all_lines[:]
for i in range(len(lines[0])):
    col_sum = 0
    for line in lines:
        col_sum += int(line[i])
    most_common = str(int(col_sum >= len(lines)/2))
    lines = [line for line in lines if line[i] == most_common]
    if len(lines) == 1:
        break
oxygen = int(lines[0], 2)

lines = all_lines[:]
for i in range(len(lines[0])):
    col_sum = 0
    for line in lines:
        col_sum += int(line[i])
    most_common = str(int(col_sum < len(lines)/2))
    lines = [line for line in lines if line[i] == most_common]
    if len(lines) == 1:
        break
co2 = int(lines[0], 2)


print(oxygen*co2)
