package com.conectahub.conectahub_api.service;

// Imports corrigidos para o seu pacote
import com.conectahub.conectahub_api.model.Fornecedor;
import com.conectahub.conectahub_api.repository.FornecedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    // O Service injeta o Repository para poder acessar o banco
    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Método para listar todos (para a tabela da tela)
    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    // Método para buscar um por ID
    public Optional<Fornecedor> buscarPorId(Long id) {
        return fornecedorRepository.findById(id);
    }

    // Método para criar ou atualizar um fornecedor
    // (O botão "+ Cadastrar novo Fornecedor")
    public Fornecedor salvar(Fornecedor fornecedor) {
        // (Aqui poderíamos adicionar lógicas de validação, ex: verificar se o CNPJ já existe)
        return fornecedorRepository.save(fornecedor);
    }

    // Método para deletar (para o botão "Ações")
    public void deletar(Long id) {
        // Verifica se o fornecedor existe antes de tentar deletar
        if (fornecedorRepository.existsById(id)) {
            fornecedorRepository.deleteById(id);
        } else {
            // Lança um erro se tentar deletar um ID que não existe
            throw new RuntimeException("Fornecedor não encontrado com id: " + id);
        }
    }
    
    // Método para atualizar (para o botão "Ações")
    public Fornecedor atualizar(Long id, Fornecedor fornecedorDetails) {
        // 1. Busca o fornecedor no banco
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com id: " + id));

        // 2. Atualiza os dados dele com o que veio na requisição
        fornecedor.setRazaoSocial(fornecedorDetails.getRazaoSocial());
        fornecedor.setCnpj(fornecedorDetails.getCnpj());
        
        // 3. Salva o fornecedor atualizado
        return fornecedorRepository.save(fornecedor);
    }
}