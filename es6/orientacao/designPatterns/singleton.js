function Pessoa(){
    if (!Pessoa.instance){
        Pessoa.instance = this;
    }

    return Pessoa.instance;
}

const p = Pessoa.call({name: 'Alexandre' });
const p2 = Pessoa.call({name: 'Custom Name'});

console.log(p);
console.log(p2);