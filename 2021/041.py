import aoc

class Table:
    def __init__(self, deftxt):
        self.lines = [aoc.ints(line) for line in aoc.lines(deftxt)];

    def check(self, num):
        for line in self.lines:
            if find(line,num) >= 0:
                line[find(line,num)] = 0

        for line in self.lines:
            if line == [0]*len(line):
                return sum([sum(line) for line in self.lines])

        zero_cols = [True]*len(self.lines[0])
        for line in self.lines:
            for col in range(len(line)):
                if line[col] != 0:
                    zero_cols[col] = False

        if any(zero_cols):
            return sum([sum(line) for line in self.lines])

        return 0

def find(lst, num):
    try:
        i = lst.index(num)
        return i
    except Exception:
        return -1

def main():
    blocks = aoc.blocks("04_in.txt")

    drawn_numbers = aoc.ints(blocks[0])

    tables = [Table(block) for block in blocks[1:]]

    for num in drawn_numbers:
        for table in tables:
            res = table.check(num)
            if res > 0:
                print(num * res)
                return

if __name__ == "__main__":
    main()
