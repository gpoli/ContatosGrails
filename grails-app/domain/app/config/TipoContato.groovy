package app.config

import app.Contato

class TipoContato {

    String nome;
    String descricao

    static hasMany = [
            contatos: Contato
    ]

    static constraints = {
        nome        blank: false
        descricao   nullable: true, size: 0..500
    }

    String toString() {nome}
}


