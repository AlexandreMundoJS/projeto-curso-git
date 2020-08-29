const textSize = 'Texto'.length;
console.log(textSize);

const splittedText = 'Texto'.split('x');
console.log(splittedText);

const replacedText = 'Texto'.replace('Text', 'txeT');
console.log(replacedText);

const lastChar = 'Texto'.slice(-1);
console.log(lastChar);

const allWithoutLastChar = 'Texto'.slice(0, -1);
console.log(allWithoutLastChar);

const secondToEnd = 'Texto'.slice(1);
console.log(secondToEnd);

const twoCharsBeforeFirstPos = 'Texto'.substr(0, 2);
console.log(twoCharsBeforeFirstPos);