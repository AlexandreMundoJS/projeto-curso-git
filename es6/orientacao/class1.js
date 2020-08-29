'use strict'
class Animal {
    constructor(qtdePatas){
        this.qtdePatas = qtdePatas;
    }

    moveimentar() {}
}

class Cachorro extends Animal {
    constructor(morde){
        super(4);
        this.qtdePatas = 4
        this.morde = morde;
    }

    latir(){
        console.log("AU AU!");
    }
}

const pug = new Cachorro(false);
console.log(pug.latir());