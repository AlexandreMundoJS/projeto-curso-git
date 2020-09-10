// let arr = ['apple', 'banana', 'orange', ['Tomato']];

/* Verboso
let apple = arr[0];
let banana = arr[1];
let orange = arr[2];
let tomato = arr[3][0];
*/

// Destructuring Assignment

// var [apple, banana, laranja, [tomato]] = ['apple', 'banana', 'orange', ['Tomato']];

// console.log(apple)
// console.log(tomato)


let obj = {
    name: 'Alexandre',
    props: {
        age: 26,
        favoriteColors: ['black', 'blue']
    }
}
let age2 = obj.props.age;
let nome = obj.name;

let {name} = obj;
let {props: {age, favoriteColors: [color1, color2]}} = obj;

console.log(name);
console.log(age);
console.log(color1);
console.log(color2);


// functions

function sum([a,b] = [0, 0]){
    return a + b;
}

console.log(sum([5, 15]));

function sum2({a, b}){
    return a + b;
}

console.log(sum2({a: 5, b: 5}))