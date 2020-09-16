// Promise

const doSomethingPromise = () => new Promise((resolve, reject) => {
    setTimeout(() => {
        resolve('First Data')
    }, 1500);
});

const doOtherThingPromise = () =>
    new Promise((resolve, reject) => {
        setTimeout(() => {
            resolve('Second Data');
        }, 1000);
    });

    // Promise.all([doSomethingPromise(), doOtherThingPromise()]).then((data) => {
    //     console.log(data[0].split(''));
    //     console.log(data[1].split(''));
    // }).catch(err => console.log(err));

    Promise.race([doSomethingPromise(), doOtherThingPromise()]).then((data) => {
        console.log(data);
    });


// doSomethingPromise()
//     .then(data => {
//         console.log(data.split(''));
//         return doOtherThingPromise()
//     })
//     .then(data2 => console.log(data2.split('')))
//     .catch(error => console.log(error));


// Pending
// Fulfilled
// Rejected

// Callback
// function doSomething(callback) {
//     setTimeout(() => {
//         callback('First Data');
//     }, 1000);
// }

// function doOtherThing(callback) {
//     setTimeout(() => {
//         callback('Second data');
//     }, 1000);
// }

// function doAll() {
//     try {
//         doSomething((data) => {
//             let processedData = data.split('');
//             try {
//                 doOtherThing((data2) => {
//                     let processedData2 = data2.split('');
//                     try {
//                         setTimeout(() => {
//                             console.log(processedData, processedData2)
//                         }, 1000);
//                     } catch (error) {
//                         console.log(error);
//                     }
//                 })
//             } catch (error) {
//                 console.log(error);
//             }
//         })
//     } catch (error) {
//         console.log(error);
//     }

// }

// doAll();