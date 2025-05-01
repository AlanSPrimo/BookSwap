package br.com.fecaf.repository;

import br.com.fecaf.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository  extends JpaRepository<Contato, Integer> {
}
