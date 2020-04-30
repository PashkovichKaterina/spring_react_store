module.exports = {
    "env": {
        "es6": true,
        "browser": true,
        "node": true
    },
    "settings": {
        "react": {
            "version": "16.12.0"
        }
    },
    "parser": "babel-eslint",
    "extends": [
        "eslint:recommended",
        "plugin:react/recommended"
    ],
    "globals": {
        "Atomics": "readonly",
        "SharedArrayBuffer": "readonly"
    },
    "parserOptions": {
        "ecmaFeatures": {
            "jsx": true
        },
        "ecmaVersion": 2018,
        "sourceType": "module"
    },
    "plugins": [
        "react"
    ],
    "rules": {
        "react/prop-types": 0,
        "default-case": 1,
        "eqeqeq": 1
    }
};