package br.insper.aposta.aposta.exception;

public class PartidaUnavailableException extends RuntimeException {
    public PartidaUnavailableException(String mensagem) {
        super(mensagem);
    }
}