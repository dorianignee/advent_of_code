const fs = require('node:fs');
const data = fs.readFileSync('./01_in.txt', 'utf8');

// summarize number consisting of first and last digit per line
let sum = 0;
for (let line of data.split('\n')) {
    let firstDigit = line.match(/\d|one|two|three|four|five|six|seven|eight|nine/)[0];
    let lastDigit = line.match(/.*(\d|one|two|three|four|five|six|seven|eight|nine)/)[1];
    let firstValue = wordValue(firstDigit);
    let lastValue = wordValue(lastDigit);

    let num = parseInt(firstValue + lastValue);
    sum += num;
}
console.log(sum);

function wordValue(word) {
    const dict = {
        "zero": "0",
        "one": "1",
        "two": "2",
        "three": "3",
        "four": "4",
        "five": "5",
        "six": "6",
        "seven": "7",
        "eight": "8",
        "nine": "9"
    };
    return dict[word] ?? word;
}