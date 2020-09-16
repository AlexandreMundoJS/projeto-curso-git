// fetch('../fetch/data.json')
//     .then((responseStream) => {
//         if (responseStream.status === 200) {
//             return responseStream.json()
//         } else {
//             throw new Error('Request Error');
//         }
//     })
//     .then(data => {
//         console.log(data);
//     })
//     .catch(err => {
//         console.log(err);
//     });

// Async / await
const asyncTimer = () => {
    new Promise((resolve, reject) => {
        setTimeout(()=>{
            resolve(12345);
        }, 1000)
    })
}

const simpleFunction = async () => {
    const data = await asyncTimer();
    console.log(data);
    const dataJson = await fetch('../fetch/data.json').then(resStream => {
        resStream.json()
    });
    return dataJson;
}

simpleFunction().then(data => {
    console.log(data)
}).catch(err => {
    console.log(err);
})