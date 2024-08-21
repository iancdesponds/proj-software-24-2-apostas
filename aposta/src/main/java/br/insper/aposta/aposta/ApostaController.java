package br.insper.aposta.aposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aposta")
public class ApostaController {
    @Autowired
    private ApostaService apostaService;

    @GetMapping
    public List<Aposta> listar(@RequestParam(required = false) String status) {
        return apostaService.listar(status);
    }

    @PostMapping
    public void Salvar(@RequestBody Aposta aposta) {
        apostaService.salvar(aposta);
    }

    @GetMapping("/{id}")
    public Aposta obterPorId(@PathVariable("id") String id) {
        return apostaService.obterPorId(id);
    }
}
