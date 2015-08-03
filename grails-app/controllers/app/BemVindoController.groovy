package app

class BemVindoController {

    def ola() {
        def msg = "Bem vindo agora é " + new Date()

        [mensagem: msg]
    }
}

