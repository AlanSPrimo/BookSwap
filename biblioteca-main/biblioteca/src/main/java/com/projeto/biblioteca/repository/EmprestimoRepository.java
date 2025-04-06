package com.projeto.biblioteca.repository;

import com.projeto.biblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByUsuarioId(Long usuarioId);
    List<Emprestimo> findByDevolvidoFalse();
}