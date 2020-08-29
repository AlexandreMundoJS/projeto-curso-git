let passengers = [
    {    name: "Jane Doloop", paid: true, ticket: "coach" },
    {    name: "Dr Evel", paid: true, ticket: "firstclass" },
    {    name: "Sue Property", paid: true, ticket: "firstclass" },
    {    name: "John Funcall", paid: true, ticket: "premium" }
];


function createDrinkOrder(passenger){
    let orderFunction;
    if (passenger.ticket === "firstclass"){
        orderFunction = function(){
            console.log("Would you like a cocktail or wine?");
        }
    } else if(passenger.ticket === "premium"){
        orderFunction = function(){
            console.log("Would you like wine, cola or water?");
        }

    } else {
        orderFunction = function(){
            console.log("Your choice is cola or water");
        }
    }
    return orderFunction;
}

function createDinnerOrder(passenger){
    let orderFunction;
    if (passenger.ticket === "firstclass"){
        orderFunction = function(){
            console.log("Would you like chicken or pasta?");
        }
    } else if (passenger.ticket === "premium"){
        orderFunction = function(){
            console.log("Would you like a snack box or cheese plate?");
        }
    } else {
        orderFunction = function(){
            console.log("Would you like peanuts or pretzels?")
        }
    }
    return orderFunction;
}

function serveCustomer(passengers){
    let getDrinkOrderFunction = createDrinkOrder(passengers);
    let getDinnerOrderFunction = createDinnerOrder(passengers);

    // // pegar o pedido de bebidas
    getDrinkOrderFunction();

    // pegar o pedido de comida

    getDinnerOrderFunction();
   
    getDrinkOrderFunction();
    getDrinkOrderFunction();

    // exibir filme 
    getDrinkOrderFunction();

    // recolher o lixo

}

function servePassengers(passengers) {
    for (let i = 0; i < passengers.length; i++){
        serveCustomer(passengers[i]);
    }
}

servePassengers(passengers);