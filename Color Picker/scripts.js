let input = document.querySelectorAll("input");

for(let i = 0; i < input.length; i++){
    input[i].addEventListener("input", function(){
        let red = document.getElementById("red").value; 
        let green = document.getElementById("green").value;
        let blue = document.getElementById("blue").value;
        
        let display = document.getElementById("display");
        display.style.background = "rgb(" + red + ", " + green +", " + blue + ")";
    });
}