function makeCounter(){
    let count = 0;
    return {
        increment: function(){
            count++;
            return count;
        }
    };
}

let counter = makeCounter();
console.log(counter.increment());
console.log(counter.increment());
console.log(counter.increment());

