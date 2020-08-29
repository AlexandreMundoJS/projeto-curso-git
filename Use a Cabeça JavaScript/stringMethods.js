let emot = "XOxxOO";
let hugs = 0;
let kisses = 0;

emot = emot.trim();
emot = emot.toUpperCase();

for (let i = 0; i < emot.length; i++){
    if (emot.charAt(i) === "X"){
        hugs++;
    }else if (emot.charAt(i) == "O"){
        kisses++;
    }
}

console.log(hugs);
console.log(kisses);