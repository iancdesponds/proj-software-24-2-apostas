package br.insper.aposta.aposta.exception;


public class ApostaNotFoundException extends RuntimeException {

    public ApostaNotFoundException(String mensagem) {
        super(mensagem);
    }

}
