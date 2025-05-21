package com.projeto.biblioteca.repository;

import com.projeto.biblioteca.model.PropostaTroca;
import com.projeto.biblioteca.model.StatusProposta;
import com.projeto.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Importar @Param
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropostaTrocaRepository extends JpaRepository<PropostaTroca, Long> {

    // Para buscar propostas onde o usuário é o propositor
    @Query("SELECT pt FROM PropostaTroca pt " +
            "LEFT JOIN FETCH pt.livroOfertado lo LEFT JOIN FETCH lo.proprietario " + // Usar LEFT JOIN FETCH se proprietario puder ser nulo (não é o caso aqui) ou só JOIN FETCH
            "LEFT JOIN FETCH pt.livroDesejado ld LEFT JOIN FETCH ld.proprietario " +
            "LEFT JOIN FETCH pt.propositor " +
            "LEFT JOIN FETCH pt.receptor " +
            "WHERE pt.propositor = :usuario")
    List<PropostaTroca> findByPropositor(@Param("usuario") Usuario usuario);

    // Para buscar propostas onde o usuário é o receptor
    @Query("SELECT pt FROM PropostaTroca pt " +
            "LEFT JOIN FETCH pt.livroOfertado lo LEFT JOIN FETCH lo.proprietario " +
            "LEFT JOIN FETCH pt.livroDesejado ld LEFT JOIN FETCH ld.proprietario " +
            "LEFT JOIN FETCH pt.propositor " +
            "LEFT JOIN FETCH pt.receptor " +
            "WHERE pt.receptor = :usuario")
    List<PropostaTroca> findByReceptor(@Param("usuario") Usuario usuario);

    Optional<PropostaTroca> findByLivroOfertadoIdAndLivroDesejadoIdAndPropositorIdAndReceptorIdAndStatus(
            Long livroOfertadoId, Long livroDesejadoId, Long propositorId, Long receptorId, StatusProposta status);

    @Query("SELECT pt FROM PropostaTroca pt " +
            "LEFT JOIN FETCH pt.livroOfertado lo LEFT JOIN FETCH lo.proprietario " +
            "LEFT JOIN FETCH pt.livroDesejado ld LEFT JOIN FETCH ld.proprietario " +
            "LEFT JOIN FETCH pt.propositor " +
            "LEFT JOIN FETCH pt.receptor " +
            "WHERE (pt.propositor = :usuario OR pt.receptor = :usuario) " +
            "AND pt.status IN :statuses")
    List<PropostaTroca> findByUsuarioAndStatusIn(@Param("usuario") Usuario usuario, @Param("statuses") List<StatusProposta> statuses);

    // AJUSTE CRÍTICO AQUI: garantir que os proprietários dos livros sejam carregados
    @Query("SELECT pt FROM PropostaTroca pt " +
            "LEFT JOIN FETCH pt.livroOfertado lo LEFT JOIN FETCH lo.proprietario " + // Adicionado FETCH para proprietario do livroOfertado
            "LEFT JOIN FETCH pt.livroDesejado ld LEFT JOIN FETCH ld.proprietario " + // Adicionado FETCH para proprietario do livroDesejado
            "LEFT JOIN FETCH pt.propositor " +
            "LEFT JOIN FETCH pt.receptor " +
            "WHERE pt.id = :id")
    Optional<PropostaTroca> findByIdWithDetails(@Param("id") Long id);
}