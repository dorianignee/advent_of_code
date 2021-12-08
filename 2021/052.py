import aoc

class Coords:
    def __init__(self, line):
        self.x1, self.y1, self.x2, self.y2 = aoc.ints(line)
        if self.x1 > self.x2:
            self.x1,self.x2=self.x2,self.x1
            self.y1,self.y2=self.y2,self.y1

    def __repr__(self):
        return f"Coords [x1={self.x1},y1={self.y1},x2={self.x2},y2={self.y2}]"

def main():
    global fields
    coords = [Coords(line) for line in aoc.lines("05_in.txt")]

    fields = [[0]*1000 for _ in range(1000)]

    for c in coords:
        if c.y1 == c.y2:
            for x in range(c.x1, c.x2+1):
                fields[c.y1][x] += 1
        else:
            if c.x1 == c.x2:
                if c.y1>c.y2:
                    c.y1,c.y2=c.y2,c.y1
                for y in range(c.y1, c.y2+1):
                    fields[y][c.x1] += 1
            else:
                if c.y1>c.y2:
                    rng = range(c.y1,c.y2-1,-1)
                else:
                    rng = range(c.y1,c.y2+1)
                x = c.x1
                for y in rng:
                    fields[y][x] += 1
                    x+=1
    print(sum([1 for row in fields for cell in row if cell > 1]))

if __name__ == "__main__":
    main()
