function addN (n){
    let adder = function (x){
        return n + x;
    };
    return adder;
}

let add2 = addN(2);
console.log(add2(10));
console.log(add2(100));
