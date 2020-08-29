var products = ["choo Choo Chocolate", "Icy Mint", "Cake Batter", "Blubblegum"];
var hasBubbleGum = [false, false, false, true];
var i = 0;

while (i < hasBubbleGum.length) {
    if (hasBubbleGum[i]) {
        console.log(products[i] + " contains bubble gum")
    }
    i++;
}