const EventEmitter = require('events');

class Users extends EventEmitter {
    userLogged(data){
        this.emit('User logged', data);
    }
}

const users = new Users();

users.once('User Logged', data => {
    console.log(data);
});

users.userLogged({user: 'Alexandre Machado'});