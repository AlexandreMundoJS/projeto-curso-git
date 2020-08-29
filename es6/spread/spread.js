let partes = ['ombro', 'joelho'];
let musica = ['cabeca', ...partes, 'e', 'pé'];

console.log(musica);


let i = 0;

do {
    for (let frase of musica) {
        console.log(frase);
    }
    i++;
}
while (i < 2);