let eightBall = {
    index: 0,
    advice: ["yes", "no", "maybe", "not a chance"],
    shake: function() {
        if (this.index >= this.advice.length){
            this.index = 0;
        }
    },
    look: function() {
        return this.advice[this.index];
    }
}

eightBall.shake();
console.log(eightBall.look());