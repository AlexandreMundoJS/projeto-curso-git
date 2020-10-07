/* REQUISIÇÕES */

const express = require('express');
const bodyParser = require('body-parser');
const user = require('./routes/user')
const InitiateMongoServer = require('./config/db');

// CONECTANDO AO DB
InitiateMongoServer();

const app = express();

// Definição da porta 
const PORT = process.env.PORT || 4000;

// MiddleWare
app.use(bodyParser.json());

app.get("/", (req, res) => {
    res.json({ message: "API está funcionando!"});
});

/**
 * Router Middleware
 * Router - /user/*
 * Method - *
 */

app.use('user/', user);

app.listen(PORT, (req, res) => {
    console.log(`Servidor iniciado na porta ${PORT}`);
});
