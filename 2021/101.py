import aoc

class Bracket:
    def __init__(self, bracket):
        if bracket == "(":
            self.expected = ")"
        elif bracket == "[":
            self.expected = "]"
        elif bracket == "{":
            self.expected = "}"
        elif bracket == "<":
            self.expected = ">"
        else:
            print(f"Bracket '{bracket}' unknown.")

    def check(self, bracket):
        if self.expected == bracket:
            return 0
        elif bracket == ")":
            return 3
        elif bracket == "]":
            return 57
        elif bracket == "}":
            return 1197
        elif bracket == ">":
            return 25137
        else:
            print(f"Bracket '{bracket}' unknown.")

def check_line(line):
    stack = []
    for bracket in line:
        if bracket in "([{<":
            stack.append(Bracket(bracket))
        else:
            ans = stack.pop().check(bracket)
            if ans > 0:
                return ans
    return 0

lines = aoc.lines("10_in.txt")
result = 0

for line in lines:
    result += check_line(line)

print(result)
