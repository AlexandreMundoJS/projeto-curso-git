function Pessoa (customProperties){
    return {
        name: 'Alexandre',
        lastName: 'Machado',
        ...customProperties
    }
}

const p = Pessoa({name: 'Custom Name', age: 23});
console.log(p);