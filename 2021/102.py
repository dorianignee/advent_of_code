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
            return True
        return False

def check_line(line):
    stack = []
    result = 0
    for bracket in line:
        if bracket in "([{<":
            stack.append(Bracket(bracket))
        else:
            ans = stack.pop().check(bracket)
            if not ans:
                return 0
    while len(stack) > 0:
        result *= 5
        result += ")]}>".index(stack.pop().expected)+1
    return result

lines = aoc.lines("10_in.txt")
results = []

for line in lines:
    line_res = check_line(line)
    if line_res > 0:
        results.append(line_res)

results.sort()
print(results[len(results)//2])
