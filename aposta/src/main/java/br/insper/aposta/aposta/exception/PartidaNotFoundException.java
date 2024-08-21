package br.insper.aposta.aposta.exception;

public class PartidaNotFoundException extends RuntimeException {
    public PartidaNotFoundException(String mensagem) {
        super(mensagem);
    }

}
