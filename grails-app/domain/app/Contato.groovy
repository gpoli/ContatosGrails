package app

import app.config.TipoContato

class Contato {

    String nome
    String apelido
    String email
    String telefone
    String notas

    TipoContato tipoContato

    static belongsTo = [
            tipoContato: TipoContato
    ]

    static constraints = {
        nome        blank: false
        apelido     nullable: true
        email       email: true, blank: false
        telefone    nullable: true
        notas       nullable: true, size: 0..500
    }
}

