// REQUISIÇÕES

const express = require('express');
const { check, validationResult } = require('express-validator/check');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const router = express.Router();

const User = require('../model/User');
/**
 * @method - POST
 * @param - /signup
 * @description - User SignUp
 */

router.post(
    '/signup',
    [
        check("username", "Por favor, adicione um nome de usuário válido")
            .not()
            .isEmpty(),
        check('email', 'Por favor, adicione um e-mail válido'),
        check('password', 'Por favor, adicione uma senha válida').isLength({
            min: 6
        })
    ],
    async (req, res) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(400).json({
                errors: errors.array()
            });
        }

        const {
            username,
            email,
            password
        } = req.body;
        try {
            let user = await User.findOne({
                email
            });
            if (user) {
                return res.status(400).json({
                    msg: 'Usuário já existe'
                })
            }

            user = new User({
                username,
                email,
                password
            });

            const salt = await bcrypt.genSalt(10);
            user.password = await bcrypt.hash(password, salt);

            await user.save();

            const payload = {
                user: {
                    id: user.id
                }
            };

            jwt.sign(
                payload,
                'randomString', {
                expiresIn: 10000
            },
                (err, token) => {
                    if (err) throw err;
                    res.status(200).json({
                        token
                    });
                }
            );
        } catch (err) {
            console.log(err.message);
            res.status(500).send('Erro ao salvar!')
        }
    }
);
module.exports = router;