let order = [];
let clickedOrder = [];
let score = 0;

// 0 - verde
// 1 - vermelho
// 2 - amarelo
// 3 - azul

const blue = document.querySelector('.blue');
const red = document.querySelector('.red');
const yellow = document.querySelector('.yellow');
const green = document.querySelector('.green');
// cria ordem aleatória de cores
function shuffleOrder(){
    let colorOrder = Math.floor(Math.random() * 4);
    order[order.length] = colorOrder;

    clickedOrder = [];

    for (let i in order){
        let elementColor = createColorElement(order[i]);
        lightColor(elementColor, Number(i) + 1);
    }
}

// acende a próxima cor
function lightColor(element, number){
    number = number * 500;
    setTimeout(() => {
        element.classList.add('selected');
    }, number - 250);

    setTimeout(() => {
        element.classList.remove('selected');
    });
}

// checa se os botões clicados são os mesmos da ordem gerada no jogo
function checkOrder(){
    for (let i in clickedOrder){
        if(clickedOrder[i] != order[i]){
            gameOver();
            break;
        }
    }
    if (clickedOrder.length == order.length){
        alert('Pontuação: ' + score + '\nVocê acertou! Iniciando próximo nível!');
        nextLevel();
    }
}

// função para o clique do usuário

function click(color){
    clickedOrder[clickedOrder.length] = color;
    createColorElement(color).classList.add('selected');

    setTimeout(() => {
        createColorElement(color).classList.remove('selected');
        checkOrder();
    }, 250)

}

// funcao que retorna a cor
function createColorElement(color){
    if (color == 0){
        return green;
    } else if (color == 1){
        return red;
    } else if (color == 2){
        return yellow;
    } else if (color == 3) {
        return blue;
    }
}

// função para próximo nível do jogo
function nextLevel(){
    score++;
    shuffleOrder();
}

// função para game over
function gameOver(){
    alert(`Pontuação: ${score}!\nVocê perdeu o jogo!\nClique em OK para iniciar um novo jogo`);
    order = [];
    clickedOrder = [];
    
    playGame();
};

// função para inicio do jogo
function playGame(){
    score = 0;
    alert('Bem vindo ao Genesis! Iniciando novo jogo');

    nextLevel();
}


// eventos de cick para as cores
green.onclick = () => click(0);
red.onclick = () => click(1);
yellow.onclick = () => click(2);
blue.onclick = () => click(3);

// inicio do jogo
playGame();
