'use strict';

function Animal(qtdePatas) {
    this.qtdePatas = qtdePatas;
    this.movimentar = function() {}
}

const cachorro = new Animal();
console.log(cachorro.qtdePatas);
console.log(cachorro.__proto__ === Animal.prototype);
console.log(Animal.__proto__ === Function.prototype);

console.log(cachorro instanceof Animal);
console.log(cachorro instanceof Function);

function Cachorro(morde){
    Animal.call(this, 4);
    this.morde = morde;
    this.latir = function() {
        console.log("Au! Au!");
    }
}


const pug = new Cachorro(false);
const pitbull = new Cachorro(true);
console.log(pug);
console.log(pitbull);

// function Pessoa(name) {
//     this.name = name;

//     return {
//         name: "Teste"
//     }
// }

// const p = new Pessoa("Alexandre");
// console.log(p);