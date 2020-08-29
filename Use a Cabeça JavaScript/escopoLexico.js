let justAVar = "Oh, don't you worry about it, I'm global";

function whereAreYou(){
    let justAVar = "Just an everyday LOCAL";

    function inner(){
        return justAVar;
    }
    return inner;
}

let innerFunction = whereAreYou();
let result = innerFunction();
console.log(result);