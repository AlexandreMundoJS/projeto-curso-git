function botao() {
    // console.log("clicou!")
    let clicou = document.getElementById("agradecimento");
    clicou.textContent = "Obrigado por Clicar";
}

function redirecionar() {
    window.open("https://globallabs.academy/");
    // window.location.href = "https://globallabs.academy/";
}

function trocar(elemento){
    elemento.textContent="Obrigado por passar o mouse"
}


function voltar(elemento){
    elemento.textContent="Passe o mouse aqui";
}

function load(){
    alert("Página carregada");
}

function funcaoChange(elemento){
    console.log(elemento.value);
}
// function soma(n1, n2){
//     return n1 + n2;
// }

// function setReplace(frase, nome, novoNome){
//     return frase.replace(nome, novoNome);
// }

// function validaIdade(idade){
//     var validar;
//     (idade >= 18) ? validar = true : validar = false;
//     return validar;
// }


// console.log(validaIdade(18));
// console.log(soma(1, 2));
// console.log(setReplace("Vai Japão", "Japão", "Brasil"));


// var nome = "Alexandre Machado"
// var n1 = 5;
// var n2 = 3;
// var frase = "Japão é o melhor time do mundo";

// // alert(`Bem vindo ${nome}, sua n1 é ${n1 + n12}`);

// console.log(nome);
// console.log(n1 * n2);
// console.log(frase);
// console.log(frase.replace("Japão", "Brasil"));
// console.log(frase.toLowerCase());


// var lista = ["maça", "pera", "laranja"];
// lista.push("uva");
// lista.pop();
// console.log(lista);
// console.log(lista[0]);
// console.log(lista.toString());
// console.log(lista.join(" | "));
// console.log(lista.reverse());

// var fruta = [{nome: "maça", cor:"vermelha"}, {nome: "uva", cor:"roxa"}];
// console.log(fruta);

// var idade = 18;

// if (idade >= 18){
//     console.log('maior de idade');
// } else {
//     console.log('menor de idade');
// }

// var count = 0;

// while(count <= 5){
//     console.log(count);
//     count++;
// }

// var count;

// for(count = 0; count <=5; count++){
//     console.log(count);
// }

// let d = new Date();
// console.log(d.getFullYear());
// console.log(d.getMonth());
// console.log(d.getDay());
// console.log(d.getHours());
// console.log(d.getMinutes());
// console.log(d.getSeconds());



