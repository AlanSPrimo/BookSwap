
package com.projeto.catalogo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Interface pra trabalhar com os dados dos livros, sem precisar fazer SQL na mão
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByCategoria(String categoria);
}
