let user = {
    name: 'Alexandre'
};
console.log(user);

user.name = 'Outro nome 1';
console.log(user);

user['name'] = "Outro nome 2";
console.log(user);

const prop = 'name';
user[prop] = "outro nome 3";

console.log(user);
// function gerProp(prop){
//     return user[prop];
// }

user.lastName = "Machado";

delete user.name;
