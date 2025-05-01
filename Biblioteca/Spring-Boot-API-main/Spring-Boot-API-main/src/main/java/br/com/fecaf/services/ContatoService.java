package br.com.fecaf.services;


import br.com.fecaf.model.Contato;
import br.com.fecaf.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    // Método para listar Contatos
    public List<Contato> listarContatos() {
        return contatoRepository.findAll();
    }

    // Método para Salvar novo Contato
    public Contato salvarContato (Contato contato) {
        return contatoRepository.save(contato);
    }

    // Método para Deletar Contato
    public void deletarContato (int id) {
        contatoRepository.deleteById(id);
    }




}
