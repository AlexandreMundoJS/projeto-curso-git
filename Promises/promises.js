// const coinFlip = new Promise((resolve, reject) => Math.random() > 0.5 ? resolve ('Yay'): reject('Oops'));

// coinFlip.then((data) => console.log('Yay 2'));
// coinFlip.catch((err) => console.error('Next then will not execute'));
// coinFlip.then(()=> console.log('End 2'));


const coinFlip = new Promise ((resolve, reject) => setTimeout(() => Math.random() > 0.5 ? resolve('Yay') : reject('Oops'), 20));
const p = Promise.resolve('resolve').then(coinFlip);
p.then(console.log).catch(() => console.log('error'));