package com.conectahub.conectahub_api.service;

import com.conectahub.conectahub_api.dto.CriarEnvioRequestDTO;
import com.conectahub.conectahub_api.dto.DetalhesEnvioDTO;
import com.conectahub.conectahub_api.model.*;
import com.conectahub.conectahub_api.model.StatusEnvio;
import com.conectahub.conectahub_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Esta é a lógica de negócio mais crítica.
     * Ela CRIA o envio, BAIXA o stock e REGISTA o histórico.
     * @Transactional garante que ou tudo funciona, ou nada é salvo no banco.
     */
    @Transactional
    public Envio criarNovoEnvio(CriarEnvioRequestDTO request) {
        
        // 1. Validar e buscar o agricultor
        Agricultor agricultor = agricultorRepository.findById(request.getAgricultorId())
            .orElseThrow(() -> new RuntimeException("Agricultor não encontrado"));

        // 2. Buscar e TRAVAR a Semente no banco para esta transação
        Semente semente = sementeRepository.findWithLockingById(request.getSementeId())
            .orElseThrow(() -> new RuntimeException("Semente não encontrada"));

        // 3. Verificar o stock
        if (semente.getQuantidadeKg().compareTo(request.getQuantidadeKg()) < 0) {
            throw new RuntimeException("Stock insuficiente para " + semente.getTipoSemente());
        }

        // 4. Se tiver stock, dar baixa
        semente.setQuantidadeKg(semente.getQuantidadeKg().subtract(request.getQuantidadeKg()));
        sementeRepository.save(semente); // Salva o novo valor do stock

        // 5. Criar o novo envio (Lote)
        Envio novoEnvio = new Envio();
        novoEnvio.setAgricultor(agricultor);
        novoEnvio.setSemente(semente);
        novoEnvio.setQuantidadeEnviadaKg(request.getQuantidadeKg());
        novoEnvio.setStatus(StatusEnvio.CRIADO);
        novoEnvio.setCodigoLote(request.getCodigoLote()); // Usamos o código da tela
        
        Envio envioSalvo = envioRepository.save(novoEnvio);

        // 6. Criar o primeiro registo no histórico (visto na timeline)
        HistoricoEnvio historicoInicial = new HistoricoEnvio(
            envioSalvo, 
            StatusEnvio.CRIADO, 
            "Lote gerado no Armazém central"
        );
        historicoEnvioRepository.save(historicoInicial);

        return envioSalvo;
    }

    /**
     * Busca o envio E o seu histórico (para a tela "Detalhes do lote")
     */
    public DetalhesEnvioDTO buscarDetalhesDoEnvio(String codigoLote) {
        // 1. Busca o envio pelo código
        Envio envio = envioRepository.findByCodigoLote(codigoLote)
            .orElseThrow(() -> new RuntimeException("Lote não encontrado: " + codigoLote));
        
        // 2. Busca o histórico desse envio
        List<HistoricoEnvio> historico = historicoEnvioRepository.findByEnvioIdOrderByDataHoraAsc(envio.getId());

        // 3. Retorna o DTO combinado
        return new DetalhesEnvioDTO(envio, historico);
    }
}