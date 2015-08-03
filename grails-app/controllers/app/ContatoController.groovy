package app

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ContatoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Contato.list(params), model:[contatoCount: Contato.count()]
    }

    def show(Contato contato) {
        respond contato
    }

    def create() {
        respond new Contato(params)
    }

    @Transactional
    def save(Contato contato) {
        if (contato == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contato.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contato.errors, view:'create'
            return
        }

        contato.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contato.label', default: 'Contato'), contato.id])
                redirect contato
            }
            '*' { respond contato, [status: CREATED] }
        }
    }

    def edit(Contato contato) {
        respond contato
    }

    @Transactional
    def update(Contato contato) {
        if (contato == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contato.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contato.errors, view:'edit'
            return
        }

        contato.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contato.label', default: 'Contato'), contato.id])
                redirect contato
            }
            '*'{ respond contato, [status: OK] }
        }
    }

    @Transactional
    def delete(Contato contato) {

        if (contato == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contato.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contato.label', default: 'Contato'), contato.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contato.label', default: 'Contato'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
