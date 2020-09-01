// function Pessoa(nome, idade) {
//     this.nome = nome;
//     this.idade = idade;

//     return {
//         nome,
//         idade: 20,
//         falar() {
//             console.log('objeto falar');
//         }
//     }
// }

// Pessoa.prototype.falar = function () {
//     console.log('prototype falar');
// };

// const pessoa = new Pessoa('Foo', 30);
// console.log(pessoa);
// pessoa.falar();
// console.log(pessoa.constructor === Object);
// console.log(pessoa.constructor === Pessoa);

// function adicionaUsuarioLogado(fn) {
//     const usuarioLogado = {
//       nome: 'Foo',
//       sobrenome: 'Bar'
//     };

//     fn(usuarioLogado);
//   }

//   function executaSeUsuarioEstaLogado(usuarioLogado, fn) {
//     if (!usuarioLogado) {
//       console.log('Usuário não está logado.');
//       return;
//     }

//     fn();
//   }

//   function notificaUsuarioLogado(usuarioLogado) {
//     console.log(`Bem-vindo usuário ${usuarioLogado.nome}!`);
//   }

//   adicionaUsuarioLogado(
//     usuarioLogado => executaSeUsuarioEstaLogado(usuarioLogado, () => {
//       notificaUsuarioLogado(usuarioLogado);
//     })
//   );


// function Conta() {}
// Conta.prototype.rendimento = 0;
// Conta.prototype.depositar = function() {}
// Conta.prototype.retirar = function() {}
// Conta.prototype.exibirSaldo = function() {
//   return `O saldo da conta é: ${this.saldo}.`;
// }

// function ContaPoupanca() {
//   this.exibirSaldo = function() {
//     return 'Operação não disponível';
//   }
// }

// ContaPoupanca.prototype.rendimento = 0.03;
// ContaPoupanca.prototype = Object.create(Conta.prototype);

// const contaPoupanca = new ContaPoupanca();
// console.log(contaPoupanca);
// console.log(contaPoupanca instanceof ContaPoupanca);
// console.log(contaPoupanca.exibirSaldo());

// console.log(contaPoupanca.__proto__.exibirSaldo());


// I - 
// function exibeNome(nome) {
//   console.log(nome);
// }

// II -
// function Pessoa(nome) {
//   this.nome = nome;
// }

// III -
// function Pessoa(nome) {
//   return {
//     nome
//   };
// }

// IV -
// function recuperaDadosFormulario(formulario) {
//   if (!formulario) {
//     return {};
//   }

//   const dados = {
//     nome: formulario.nome,
//     idade: formulario.idade
//   };

//   return dados;
// }

// V -
// function setNome(nome) {
//   this.nome = nome;
// }

// function Pessoa(nome) {
//     this.nome = nome;
// }

// function PessoaFisica(nome, cpf) {
//     Pessoa.call(this, nome);

//     this.cpf = cpf;
// }

// function PessoaJuridica(nome, cnpj) {
//     Pessoa(nome);

//     this.cnpj = cnpj;
// }

// const pessoaFisica = new PessoaFisica('Foo', '123.456.670-0');
// const pessoaJuridica = new PessoaJuridica('Bar', '12.345.678/9012-34');

// console.log(pessoaFisica);
// console.log(pessoaJuridica);

// class Pessoa {
//     #nome = '';
  
//     constructor(nome) {
//       this.#nome = nome;
//     }
  
//     get nome() {
//       return `Seu nome é: ${this.#nome}.`;
//     }
  
//     set nome(novoNome) {
//       this.#nome = novoNome;
//     }
//   }
  
//   const pessoa = new Pessoa();
  
//   console.log(pessoa);
//   console.log(pessoa.nome);
//   pessoa.nome = 'Foo';
//   console.log(pessoa.nome);

// class ID {
// 	static #contador = 0;

//   static get contador() {
//     return this.#contador;
//   }

//   static incrementaContador() {
//     return ++this.#contador;
//   }
// }

// class Cliente {
//   #id = 0;

//   constructor() {
//     this.#id = ID.incrementaContador();
//   }

//   get id() {
//     return this.#id;
//   }
// }

// const c1 = new Cliente();
// console.log(`Contador atual: ${ID.contador}.`);

// const c2 = new Cliente();
// const c3 = new Cliente();

// console.log(`Contador atual: ${ID.contador}.`);

const name = 'Foo';
const lastName = String('Bar');

console.log(name.constructor === lastName.constructor);
console.log(name.prototype === String.prototype);
console.log(lastName.__proto__ === String.prototype);
console.log(name.__proto__.split === lastName.__proto__.split);