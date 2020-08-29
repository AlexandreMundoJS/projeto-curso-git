let products = [
    { name: 'Grapefruit', calories: 170, color: 'red', sold: 8200 },
    { name: 'Orange', calories: 160, color: 'orange', sold: 12101 },
    { name: 'Cola', calories: 210, color: 'caramel', sold: 25412 },
    { name: 'Diet Cola', calories: 0, color: 'caramel', sold: 43922 },
    { name: 'Lemon', calories: 200, color: 'clear', sold: 14938 },
    { name: 'Raspberry', calories: 180, color: 'pink', sold: 9427 },
    { name: 'Root Beer', calories: 200, color: 'caramel', sold: 9909 },
    { name: 'Water', calories: 0, color: 'clear', sold: 62123 }
];

// Função completa
// function compareSold(product1, product2){
//     if (product1.sold > product2.sold){
//         return 1;
//     } else if (product1.sold === product2.sold){
//         return 0;
//     } else {
//         return -1;
//     }
// }

/* Função simplificada*/
function compareSold(product1, product2){
    return product1.sold - product2.sold;
}

function compareName(product1, product2){
    return (product1.name > product2.name) ? 1 : ((product2.name > product1.name) ? -1 : 0);
}

function compareCalories(product1, product2){
    return product1.calories - product2.calories;
}

function compareColor(product1, product2){
    return (product1.color > product2.color) ? 1 : ((product2.color > product1.color) ? -1 : 0);
}
/* Utilizando laço for normal */
// function printProducts(products){
//     for(let i = 0; i < products.length; i++){
//         console.log("Name: " + products[i].name + ", Calories: " + products[i].calories + ", Color: " + products[i].color + ", Sold: " + products[i].sold);
//     }
// }

/* Utilizando forEach */
function printProducts(products){
    console.log("Name: " + products.name + ", Calories: " + products.calories + ", Color: " + products.color + ", Sold: " + products.sold);
}

console.log("----------------- Compare by Sold -----------------");
products.sort(compareSold);
products.forEach(printProducts);
console.log("----------------- Compare by Name -----------------");
products.sort(compareName);
products.forEach(printProducts);
console.log("----------------- Compare by Calories -----------------");
products.sort(compareCalories);
products.forEach(printProducts);
console.log("----------------- Compare by Color -----------------");
products.sort(compareColor);
products.forEach(printProducts);