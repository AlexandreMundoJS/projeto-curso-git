let a = 0/0;
let b = "food" * 1000;
let c = Math.sqrt(-9);
console.log(a);
console.log(b);
console.log(c);

// NaN != NaN (Se comparar NaN com NaN, eles não são iguais)
// para verificar se é NaN, utilize a função isNan

/*
if (isNaN(myNum)){
    myNum = 0;
}*/

let test11 = 0/0;
console.log(typeof test11);

//NaN é um valor que é um número mas não pode ser representado

if (99 == "99"){
    console.log("A number equals a string!");
} else {
    console.log("No way a number equals a string!");
}

if (null == undefined){
    console.log("true");
} else {
    console.log("false");
}