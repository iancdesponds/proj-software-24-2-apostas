package br.insper.aposta.aposta;

import br.insper.aposta.aposta.exception.ApostaNotFoundException;
import br.insper.aposta.aposta.exception.PartidaNotFoundException;
import br.insper.aposta.aposta.exception.PartidaUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class ApostaService {

    @Autowired
    private ApostaRepository apostaRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void salvar(Aposta aposta) {
        aposta.setId(UUID.randomUUID().toString());

        try {
            PartidaResponse partida = restTemplate.getForObject(
                    "http://localhost:8080/partida/" + aposta.getIdPartida(),
                    PartidaResponse.class);
            if (partida.getStatus().equals("AGENDADA")) {
                aposta.setStatus("REALIZADA");
                apostaRepository.save(aposta);
            } else if (partida.getStatus().equals("REALIZADA")) {
                throw new PartidaUnavailableException("Aposta não pode ser realizada, pois a partida já foi realizada");
            }
        } catch (Exception e) {
            if (e.getMessage().contains("404")) {
                throw new PartidaNotFoundException("Partida não encontrada");
            }
            throw new PartidaUnavailableException(e.getMessage());
        }
    }

    public List<Aposta> listar(String status) {
        if (status != null && !status.isEmpty()) {
            return apostaRepository.findByStatus(status);
        } else {
            return apostaRepository.findAll();
        }
    }

    public Aposta obterPorId(String id) {
        Aposta aposta = apostaRepository.findById(id)
                .orElseThrow(() -> new ApostaNotFoundException("Aposta não encontrada"));

        if (aposta.getStatus().equals("REALIZADA")) {
            // Faça a requisição GET para obter os dados da partida
            String url = "http://localhost:8080/partida/" + aposta.getIdPartida();
            PartidaResponse partidaResponse = restTemplate.getForObject(url, PartidaResponse.class);

            if (partidaResponse != null && partidaResponse.getPlacarMandante() != null && partidaResponse.getPlacarVisitante() != null) {
                String resultadoAposta = aposta.getResultado(); // Mandante, Visitante ou Empate

                if ((partidaResponse.getPlacarMandante() > partidaResponse.getPlacarVisitante() && resultadoAposta.equalsIgnoreCase("MANDANTE")) ||
                        (partidaResponse.getPlacarVisitante() > partidaResponse.getPlacarMandante() && resultadoAposta.equalsIgnoreCase("VISITANTE")) ||
                        (partidaResponse.getPlacarMandante().equals(partidaResponse.getPlacarVisitante()) && resultadoAposta.equalsIgnoreCase("EMPATE"))) {

                    aposta.setStatus("GANHOU");
                } else {
                    aposta.setStatus("PERDEU");
                }

                apostaRepository.save(aposta);
            } else {
                return aposta;
            }
        }
        return aposta;
    }
}