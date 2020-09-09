/* 
* Map
* Retorna um novo array com a mesma quantidade de itens
* que o array inicial
*/
const pets = [
    {
        name: 'rex',
        type: 'dog',
        age: 10
    },
    {
        name: 'miau',
        type: 'cat',
        age: 2
    }, {
        name: 'gulp',
        type: 'fish',
        age: 1
    }
];

const mapPetNames = pets.map ((pet) => {
    return pet.name;
})

console.log(mapPetNames);


/* 
* Foreach
* Não retorna um novo array com a mesma quantidade de elementos
*/
const forEachPetNames = [];
pets.forEach((pet)=> {
    forEachPetNames.push(pet.name);
})

console.log(forEachPetNames);