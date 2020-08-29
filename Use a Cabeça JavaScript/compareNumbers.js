let numbersArray = [60, 50, 62, 58, 54, 54];
/* Função completa
function compareNumbers (num1, num2){
    if (num1 > num2){
        return 1;
    } else if (num1 === num2){
        return 0;
    } else {
        return -1;
    }
}*/

// Função simplificada
function compareNumbers (num1, num2){
    return num1 - num2;
}

/* Ordem decrescente 
 function compareNumbers (num1, num2){
     if (num1 > num2){
         return 1;
     } else if (num1 === num2){
         return 0;
     } else {
         return -1;
     }
 }
*/
numbersArray.sort(compareNumbers);
console.log(numbersArray);