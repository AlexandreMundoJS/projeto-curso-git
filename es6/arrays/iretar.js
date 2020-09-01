const arr = [1, 2, 3, 4, 5];
arr.forEach((value, index) => {
    console.log(`${index}: ${value}`);
})

const arr2 = [1, 2, 3, 4, 5];
arr2.map((value, index) => {
    console.log(`${index}: ${value}`);
})

arr2.flatMap(value => [[value* 2]]);
console.log(arr2);

const arr3 = [1, 2, 3, 4];
arr3.join("-");
arr3.reduce((total, value) => total += value, 0);

