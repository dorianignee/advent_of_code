const fs = require('node:fs');
const data = fs.readFileSync('./01_in.txt', 'utf8');

// summarize number consisting of first and last digit per line
let sum = 0;
for (let line of data.split('\n')) {
    let digits = line.match(/\d/g);
    let num = parseInt(digits[0] + digits[digits.length-1]);
    sum += num;
}
console.log(sum);