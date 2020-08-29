'use strict';

class Person {
    // private attribute
    #name = '';

    constructor(name){
        this.#name = name;
    }
    
    get name() {
        return this.#name;
    }

    set name(name) {
        this.#name = name;
    }

    // static implementation call withou instantiate
    static walk(){
        console.log('Walking...')
    }
}

console.log(Person.walk());