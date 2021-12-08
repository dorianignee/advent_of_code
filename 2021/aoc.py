import re

def read_raw(path):
    return "".join(open(path).readlines())

def ints(input):
    # return a list of ints being separated by non-numerical characters
    if input.endswith(".txt"):
        return ints(read_raw(input))
    else:
        return [int(num) for num in re.split("\D+", input) if num != '']

def lines(input):
    # return lines separated by \n.
    if input.endswith(".txt"):
        return lines(read_raw(input))
    else:
        return input.split("\n")

def blocks(input):
    # return blocks separated by \n\n
    if input.endswith(".txt"):
        return blocks(read_raw(input))
    else:
        return input.split("\n\n")
