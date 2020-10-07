const mongoose = require('mongoose');

// LINK DA BASE DE DADOS
const MONGOURI = 'mongodb://localhost:27017/node-auth';

// INICIALIZANDO O MONGO DE FORMA ASSÍNCRONA

const InitiateMongoServer = async () =>{
    try{
        await mongoose.connect(MONGOURI, {
            useNewUrlParser: true
        });
        console.log('Conectado à base de dados');
    } catch (error){
        console.log(error);
        throw error;
    }
};

module.exports = InitiateMongoServer;