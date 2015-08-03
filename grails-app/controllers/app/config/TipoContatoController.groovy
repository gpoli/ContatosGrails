package app.config

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TipoContatoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TipoContato.list(params), model:[tipoContatoCount: TipoContato.count()]
    }

    def show(TipoContato tipoContato) {
        respond tipoContato
    }

    def create() {
        respond new TipoContato(params)
    }

    @Transactional
    def save(TipoContato tipoContato) {
        if (tipoContato == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tipoContato.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoContato.errors, view:'create'
            return
        }

        tipoContato.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tipoContato.label', default: 'TipoContato'), tipoContato.id])
                redirect tipoContato
            }
            '*' { respond tipoContato, [status: CREATED] }
        }
    }

    def edit(TipoContato tipoContato) {
        respond tipoContato
    }

    @Transactional
    def update(TipoContato tipoContato) {
        if (tipoContato == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tipoContato.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoContato.errors, view:'edit'
            return
        }

        tipoContato.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoContato.label', default: 'TipoContato'), tipoContato.id])
                redirect tipoContato
            }
            '*'{ respond tipoContato, [status: OK] }
        }
    }

    @Transactional
    def delete(TipoContato tipoContato) {

        if (tipoContato == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tipoContato.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoContato.label', default: 'TipoContato'), tipoContato.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoContato.label', default: 'TipoContato'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
