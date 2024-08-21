package br.insper.aposta.aposta;

import br.insper.aposta.aposta.exception.ApostaNotFoundException;
import br.insper.aposta.aposta.exception.PartidaNotFoundException;
import br.insper.aposta.aposta.exception.PartidaUnavailableException;

import br.insper.aposta.common.Erro;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApostaAdvice {

    @ExceptionHandler(ApostaNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Erro apostaNotFoundHandler(ApostaNotFoundException e) {
        Erro erro = new Erro();
        erro.setMensagem(e.getMessage());
        erro.setData(LocalDateTime.now());
        erro.setCodigo(404);
        return erro;
    }

    @ExceptionHandler(PartidaNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Erro partidaNotFoundHandler(PartidaNotFoundException e) {
        Erro erro = new Erro();
        erro.setMensagem(e.getMessage());
        erro.setData(LocalDateTime.now());
        erro.setCodigo(404);
        return erro;
    }

    @ExceptionHandler(PartidaUnavailableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Erro partidaUnavailableHandler(PartidaUnavailableException e) {
        Erro erro = new Erro();
        erro.setMensagem(e.getMessage());
        erro.setData(LocalDateTime.now());
        erro.setCodigo(400);
        return erro;
    }

}
