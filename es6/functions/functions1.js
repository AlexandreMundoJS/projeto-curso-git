function fn(){
    return 'Code aqui';
}

const arrowFn = () => 'Code aqui';
const arrowFn2 = () => {
    return 'Code Aqui';
}

fn.prop = 'Posso criar propriedades';

console.log(fn());
console.log(fn.prop);

const logValue = value => console.log(value);
const logFnResult = fnParam => console.log(fnParam());
logFnResult(fn);

// const controlFnExec => fnParam => allowed => {
//     if (allowed) {
//         fnParam();
//     }
// }

// const handleFnExecution = controlFnExec(fn);


