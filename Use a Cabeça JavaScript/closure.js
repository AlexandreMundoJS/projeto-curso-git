// let count = 0;

// function counter() {
//     count++;
//     return count;
// }

// console.log(counter());
// console.log(counter());
// console.log(counter());


// COM CLOSURE:

function makeCounter() {
    //variavel local
    let count = 0;

    function counter(){
        count++;
        return count;
    }

    // Aqui está o closure!
    return counter;
}

let doCount = makeCounter();
console.log(doCount());
console.log(doCount());
console.log(doCount());