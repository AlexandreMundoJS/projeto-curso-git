const pets = [
    {
        name: 'rex',
        type: 'dog',
        age: 10,
        weight: 30
    },
    {
        name: 'miau',
        type: 'cat',
        age: 2,
        weight: 3
    }, {
        name: 'gulp',
        type: 'fish',
        age: 1,
        weight: 1
    },
    {
        name: 'quiqui',
        type: 'dog',
        age: 10,
        weight: 12
    }
];

// const totalAge = pets.reduce((total, pet)=>{
//     return {
//         totalAge: total.totalAge + pet.age,
//         totalWeight: total.totalWeight + pet.weight
//     };
// }, { totalAge: 0, totalWeight: 0});


// const totalDogWeight = pets.reduce((total, pet) => {
//     if (pet.type !== 'dog') return total;
//     return total + pet.weight;
// }, 0);

// console.log(totalDogWeight);

// const dogs = pets.filter((pet) => {
//     return pet.type === 'dog'
// });
// console.log(dogs);

const totalWeightDogs = pets.filter((pet) => {
    return pet.type === 'dog'
}).reduce((total, pet) => {
    return total + pet.weight
}, 0)

console.log(totalWeightDogs);