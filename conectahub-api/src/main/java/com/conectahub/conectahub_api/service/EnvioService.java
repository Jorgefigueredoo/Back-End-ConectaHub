package com.conectahub.conectahub_api.service;

import com.conectahub.conectahub_api.dto.CriarEnvioRequestDTO;
import com.conectahub.conectahub_api.dto.DetalhesEnvioDTO;
import com.conectahub.conectahub_api.model.Agricultor;
import com.conectahub.conectahub_api.model.Envio;
import com.conectahub.conectahub_api.model.HistoricoEnvio;
import com.conectahub.conectahub_api.model.Semente;
import com.conectahub.conectahub_api.model.StatusEnvio;
import com.conectahub.conectahub_api.repository.AgricultorRepository;
import com.conectahub.conectahub_api.repository.EnvioRepository;
import com.conectahub.conectahub_api.repository.HistoricoEnvioRepository;
import com.conectahub.conectahub_api.repository.SementeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private SementeRepository sementeRepository;

    @Autowired
    private AgricultorRepository agricultorRepository;

    @Autowired
    private HistoricoEnvioRepository historicoEnvioRepository;

    /**
     * Cria um novo envio (Status: CRIADO / Pedido Enviado)
     */
    @Transactional
    public Envio criarNovoEnvio(CriarEnvioRequestDTO request) {
        
        Agricultor agricultor = agricultorRepository.findById(request.getAgricultorId())
            .orElseThrow(() -> new RuntimeException("Agricultor não encontrado"));

        Semente semente = sementeRepository.findWithLockingById(request.getSementeId())
            .orElseThrow(() -> new RuntimeException("Semente não encontrada"));

        if (semente.getQuantidadeKg().compareTo(request.getQuantidadeKg()) < 0) {
            throw new RuntimeException("Estoque insuficiente.");
        }

        semente.setQuantidadeKg(semente.getQuantidadeKg().subtract(request.getQuantidadeKg()));
        sementeRepository.save(semente);

        Envio novoEnvio = new Envio();
        novoEnvio.setAgricultor(agricultor);
        novoEnvio.setSemente(semente);
        novoEnvio.setQuantidadeEnviadaKg(request.getQuantidadeKg());
        novoEnvio.setStatus(StatusEnvio.CRIADO);
        novoEnvio.setCodigoLote(request.getCodigoLote());
        novoEnvio.setDataCriacao(LocalDateTime.now());
        
        Envio envioSalvo = envioRepository.save(novoEnvio);

        registrarHistorico(envioSalvo, StatusEnvio.CRIADO, "Lote gerado no Armazém central");

        return envioSalvo;
    }

    public DetalhesEnvioDTO buscarDetalhesDoEnvio(String codigoLote) {
        Envio envio = envioRepository.findByCodigoLote(codigoLote)
            .orElseThrow(() -> new RuntimeException("Lote não encontrado: " + codigoLote));
        
        List<HistoricoEnvio> historico = historicoEnvioRepository.findByEnvioIdOrderByDataHoraAsc(envio.getId());

        return new DetalhesEnvioDTO(envio, historico);
    }

    /**
     * Atualiza para "EM ROTA DE ENTREGA" (EM_TRANSITO)
     * ESTE É O MÉTODO QUE ESTAVA FALTANDO
     */
    public Envio marcarComoEmTransito(Long envioId) {
        Envio envio = envioRepository.findById(envioId)
            .orElseThrow(() -> new RuntimeException("Envio não encontrado"));

        // Só atualiza se não estiver entregue
        if (envio.getStatus() != StatusEnvio.ENTREGUE && envio.getStatus() != StatusEnvio.EM_TRANSITO) {
            envio.setStatus(StatusEnvio.EM_TRANSITO);
            envioRepository.save(envio);
            
            registrarHistorico(envio, StatusEnvio.EM_TRANSITO, "Saiu para entrega (Em Rota)");
        }
        return envio;
    }

    /**
     * Atualiza para "ENTREGUE"
     */
    public Envio marcarComoEntregue(Long envioId) {
        Envio envio = envioRepository.findById(envioId)
            .orElseThrow(() -> new RuntimeException("Envio não encontrado"));

        if (envio.getStatus() != StatusEnvio.ENTREGUE) {
            envio.setStatus(StatusEnvio.ENTREGUE);
            envioRepository.save(envio);

            registrarHistorico(envio, StatusEnvio.ENTREGUE, "Entrega confirmada no destino.");
        }
        return envio;
    }

    // Método auxiliar para evitar repetição de código
    private void registrarHistorico(Envio envio, StatusEnvio status, String descricao) {
        HistoricoEnvio historico = new HistoricoEnvio();
        historico.setEnvio(envio);
        historico.setStatus(status);
        historico.setDescricao(descricao);
        historico.setDataHora(LocalDateTime.now());
        historicoEnvioRepository.save(historico);
    }
}