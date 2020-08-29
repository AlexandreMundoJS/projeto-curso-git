window.onload = function(){
    let button = document.getElementById("cake");
    button.onclick = function(){
        console.log("Time to bake the cookies.");
        cookies.bake(2500);
    }
}

let cookies = {
    instructions: "Preheat oven to 350...",
    bake: function(time){
        console.log("Baking the cookies.");
        setTimeout(done, time);
    }
};

function done(){
    console.log("Cookies are ready, take them out to cool.");
    console.log("Cooling the cookies");
    let cool = setTimeout(function() {
        console.log("Cookies are cool, time to eat.");
    }, 1000);
}


//setTimeout(function() { 
//   console.log("Time to take the cookies out of the oven ");
//}, 600000);