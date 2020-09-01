const persons = Array.of('John', 'Cris', 'Jenny');
console.log(persons);

const numberAndStrings = Array.of(1, 2, 'strings', 'texto');
console.log(numberAndStrings);

const arr = Array(3);
console.log(arr);

const arr2 = Array(3, 2);
console.log(arr2);

const fruta = ['maça', 'banana'];
fruta.push('limão');
console.log(fruta);
fruta.pop();
console.log(fruta);
console.log(fruta.length);
fruta.unshift('acerola');
console.log(fruta);
fruta.shift();
console.log(fruta);

const salgados = ['salgadinho', 'coxinha'];
const alimentos = fruta.concat(salgados);
console.log(alimentos);


