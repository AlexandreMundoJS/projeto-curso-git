let animacao = window.requestAnimationFrame || function(callback){
    window.setTimeout(callback, 1000/60);
}

let canvas = document.createElement("canvas");
let width = 400;
let height = 600;
canvas.width = width;
canvas.height = height;
let contexto = canvas.getContext("2d");

window.onload = function(){
    document.body.appendChild(canvas);
    animacao(step);
};

let step = function(){
    atualizar();
    renderizar();
    animacao(step);
};




function Raquete(x, y, width, height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.velocidade_x = 0;
    this.velocidade_y = 0;
}

Raquete.prototype.renderizar = function(){
    contexto.fillStyle = "black";
    contexto.fillRect(this.x, this.y, this.width, this.height);
};

function Jogador(){
    this.raquete = new Raquete(175, 580, 50, 10);
}

function Computador(){
    this.raquete = new Raquete(175, 10, 50, 10);
}

Jogador.prototype.renderizar = function(){
    this.raquete.renderizar();
}

Computador.prototype.renderizar = function(){
    this.raquete.renderizar();
}

function Bolinha(x, y){
    this.x = x;
    this.y = y;
    this.velocidade_x = 0;
    this.velocidade_y = 3;
    this.radius = 5;
}

Bolinha.prototype.renderizar = function() {
    contexto.beginPath();
    contexto.arc(this.x, this.y, this.radius, 2 * Math.PI, false);
    contexto.fillStyle="white";
    contexto.fill();
};

let jogador = new Jogador();
let computador = new Computador();

//A bolinha será renderizada no centro da tela
let bolinha = new Bolinha(200,300);

let renderizar = function(){
    contexto.fillStyle = "#836FFF";
    contexto.fillRect(0, 0, width, height);
    //Atualizar a partir daqui
    jogador.renderizar();
    computador.renderizar();
    bolinha.renderizar();
}

function atualizar(){
    jogador.atualizar();
    computador.atualizar(bolinha)
    bolinha.atualizar(jogador.raquete, computador.raquete);
};

Bolinha.prototype.atualizar = function(raquete1, raquete2){
    this.x += this.velocidade_x;
    this.y += this.velocidade_y;

    let top_x = this.x - 5;
    let top_y = this.y - 5;
    let bottom_x = this.x + 5;
    let bottom_y = this.y + 5;

    // Se bate na extremidade esquerda
    if (this.x - 5 < 0){
        this.x = 5;
        this.velocidade_x = -this.velocidade_x;
    } else if (this.x + 5 > 400){ //Se bate na extremidade direita
        this.x=395;
        this.velocidade_x = -this.velocidade_x;
    }

    //Se tocar no fim do jogo ou no início, ocorre um ponto
    if (this.y < 0 || this.y > 600){
        this.velocidade_x = 0;
        this.velocidade_y = 3;
        // E centralizamos a bolinha novamente
        this.x = 200;
        this.y = 300;
    }

    if (top_y > 300){
        if (top_y < (raquete1.y + raquete1.height) && bottom_y > raquete1.y && top_x < (raquete1.x + raquete1.width) && bottom_x > raquete1.x){
            // Acerta a raquete do jogador
            this.velocidade_y = -3;
            this.velocidade_x += (raquete1.velocidade_x /2);
            this.y += this.velocidade_y;
        }
    }else{
        if(top_y < (raquete2.y + raquete2.height) && bottom_y > raquete2.y && top_x < (raquete2.x + raquete2.width) && bottom_x > raquete2.x){
            // Acerta a raquete do computador
            this.velocidade_y = 3;
            this.velocidade_x += (raquete2.velocidade_x / 2);
            this.y += this.velocidade_y;
        }
    }
}

let keysDown = {};

window.addEventListener("keydown", function(event){
    keysDown[event.keyCode] = true;
});

window.addEventListener("keyup", function(event){
    delete keysDown[event.keyCode];
});

Jogador.prototype.atualizar = function(){
    for (let key in keysDown) {
        //Captura e pega o número da tecla pressionada
        let valor = Number(key);
        // Tecla seta para esquerda (37)
        if (valor == 37) {
            this.raquete.mover(-4, 0);
        }
        // Tecla seta para direita (39)
          else if (valor == 39) {
            this.raquete.mover(4, 0);
        } else{
            this.raquete.mover(0, 0);
        }
    }
};

Raquete.prototype.mover = function(x, y){
    this.x += x;
    this.y += y;
    this.velocidade_x = x;
    this.velocidade_y = y;
    // Mover tudo para a esquerda
    if(this.x < 0){
        this.x = 0;
        this.velocidade_x = 0;
    }
    // Mover tudo para a direita
      else if (this.x + this.width > 400){
        this.x = 400 - this.width;
        this.velocidade_x = 0;
    }
}

Computador.prototype.atualizar = function (bolinha){
    let posicao_x = bolinha.x;
    let diferenca = -((this.raquete.x + (this.raquete.width / 2)) - posicao_x);
    // Máxima velocidade para a esquerda
    if (diferenca < 0 && diferenca < -4) {
        diferenca = -5;
    } 
    // Máxima velocidade para a direita  
      else if (diferenca > 0 && diferenca > 4){
        diferenca = 5;
    }
    this.raquete.mover(diferenca, 0);
    if (this.raquete.x < 0){
        this.raquete.x = 0;
    } else if (this.raquete.x + this.raquete.width > 400){
        this.raquete.x = 400 - this.raquete.width;
    }
}
