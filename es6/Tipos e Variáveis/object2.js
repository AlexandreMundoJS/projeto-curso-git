let user = {
    name: "Alexandre",
    lastName: 'Machado'
}

console.log("Propriedades do objeto user: ", Object.keys(user));

Object.assign(user, {fullName: "Alexandre Machado"});
console.log("\Adiciona a propriedade fullName no objeto user", user);

const newObj = { foo: 'bar' };
Object.freeze(newObj);

newObj.foo = 'changes';
delete newObj.foo;
newObj.bar = 'foo';

console.log('\nVariavel newObj após as alterações:', newObj);

const person = { name: 'Guilherme' };
Object.seal(person);

person.name = 'Alexandre Machado';
delete person.name;
person.age = 26;

console.log('\nVariável person após as alterações:', person);