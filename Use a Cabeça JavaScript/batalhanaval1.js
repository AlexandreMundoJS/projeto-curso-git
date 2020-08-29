// declarando as localizações
var randomLoc = Math.floor(Math.random() * 5);
var location1 = randomLoc;
var location2 = location1 + 1;
var location3 = location2 + 1;

// declarando os palpites e acertos
var guess;
var hits = 0;
var guesses = 0;

// declarando registro se navio afundou ou não
var isSunk = false;
while (isSunk == false) {
    guess = prompt("Ready, aim, fire! (enter a number from 0-6):");
    if (guess < 0 || guess > 6) {
        alert("Please enter a valid cell number");
    } else {
        guesses++;
        if (guess == location1 || guess == location2 || guess == location3) {
            alert("HIT!");
            hits++;
            if (hits == 3) {
                isSunk = true;
                alert("You sank my battleship");
            }
        } else {
            alert("MISS!");
        }
    }
}
var stats = "You took " + guesses + " guesses to sink the battleship, " + "wich means your shooting accuracy was " + (3 / guesses);
alert(stats);