const user = {
    name: 'Alexandre',
    lastName: 'Machado'
};

function getUserWithFullName(user){
    return {
        ...user,
        fullName: `${user.name} ${user.lastName}`
    }
}

const userWithFullName= getUserWithFullName(user);

console.log(userWithFullName);