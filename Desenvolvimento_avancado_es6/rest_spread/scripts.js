// function sum (a,b){
//     let value = 0;
//     for (let i = 0; i< arguments.length; i++){
//         value += arguments[i];
//     }

//     return value;
// }


// Rest operator
// function sum (...args){
//     return args.reduce((acc, value) => acc + value, 0);
// }

// const sum = (a, b, ...rest) => {
//     console.log(a, b, rest);
// }

// const multiply = (...args) => args.reduce((acc, value) => acc * value, 1);

// const sum = (...rest) => {
//     //spread operator
//     return multiply(...rest);
// }
// console.log(sum(5, 5, 5, 3, 2));
// console.log(multiply(3, 3, 4, 5));


//Spread operator pode ser utilizado em strings, array, objetos para consumir outros objetos literais e também em objetos iteráveis

// const str = 'Digital Innovation One';

// function logArgs(...args){
//     console.log(args);
// }

// logArgs(...str);

// const arr = [1, 2, 3, 4];

// function logArgs(a, b, c){
//     console.log(a, b, c);
// }

// const arr2 = arr.concat([5, 6, 7]);
// const arr3 = [...arr,5, 6, 7];
// const arrClone = [...arr, arr2, arr3]
// console.log(arr2);
// console.log(arr3);
// console.log(arrClone);

const obj = {
    test: 123
};

const obj2 = {
    test: 456
}

const objMerged = {
    ...obj,
    ...obj2
}
console.log(objMerged);


