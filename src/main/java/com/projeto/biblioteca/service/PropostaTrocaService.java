package com.projeto.biblioteca.service;

import com.projeto.biblioteca.dto.LivroResumoDTO;
import com.projeto.biblioteca.dto.PropostaTrocaRequestDTO;
import com.projeto.biblioteca.dto.PropostaTrocaResponseDTO;
import com.projeto.biblioteca.dto.UsuarioResumoDTO;
import com.projeto.biblioteca.model.*; // Inclui Livro, Usuario, PropostaTroca, StatusProposta
import com.projeto.biblioteca.repository.LivroRepository;
import com.projeto.biblioteca.repository.PropostaTrocaRepository;
import com.projeto.biblioteca.repository.UsuarioRepository;
import jakarta.validation.Valid; // Para validação do DTO, se aplicável diretamente no serviço
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropostaTrocaService {

    private static final Logger logger = LoggerFactory.getLogger(PropostaTrocaService.class);

    @Autowired
    private PropostaTrocaRepository propostaTrocaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    // Helper para converter Entidade PropostaTroca para DTO
    private PropostaTrocaResponseDTO toResponseDTO(PropostaTroca proposta) {
        PropostaTrocaResponseDTO dto = new PropostaTrocaResponseDTO();
        dto.setId(proposta.getId());

        // Tratamento para evitar NullPointerException se alguma entidade relacionada for nula (embora não devesse ser o caso com JOIN FETCH)
        if (proposta.getLivroOfertado() != null) {
            dto.setLivroOfertado(new LivroResumoDTO(proposta.getLivroOfertado().getId(), proposta.getLivroOfertado().getTitulo(), proposta.getLivroOfertado().getAutor()));
        }
        if (proposta.getLivroDesejado() != null) {
            dto.setLivroDesejado(new LivroResumoDTO(proposta.getLivroDesejado().getId(), proposta.getLivroDesejado().getTitulo(), proposta.getLivroDesejado().getAutor()));
        }
        if (proposta.getPropositor() != null) {
            dto.setPropositor(new UsuarioResumoDTO(proposta.getPropositor().getId(), proposta.getPropositor().getNome()));
        }
        if (proposta.getReceptor() != null) {
            dto.setReceptor(new UsuarioResumoDTO(proposta.getReceptor().getId(), proposta.getReceptor().getNome()));
        }

        dto.setStatus(proposta.getStatus());
        dto.setDataProposta(proposta.getDataProposta());
        dto.setDataAtualizacaoStatus(proposta.getDataAtualizacaoStatus());
        return dto;
    }

    private PropostaTroca getPropostaOuLancaExcecao(Long propostaId) {
        // Usar o método do repositório que faz JOIN FETCH para garantir que os dados relacionados estejam carregados
        return propostaTrocaRepository.findByIdWithDetails(propostaId)
                .orElseThrow(() -> {
                    logger.warn("Proposta de troca com ID {} não encontrada.", propostaId);
                    return new IllegalArgumentException("Proposta de troca não encontrada.");
                });
    }

    @Transactional
    public PropostaTrocaResponseDTO criarProposta(@Valid PropostaTrocaRequestDTO requestDTO, String emailPropositor) {
        logger.info("Usuário {} está criando uma nova proposta de troca. Livro Ofertado ID: {}, Livro Desejado ID: {}",
                emailPropositor, requestDTO.getLivroOfertadoId(), requestDTO.getLivroDesejadoId());

        Usuario propositor = usuarioRepository.findByEmail(emailPropositor)
                .orElseThrow(() -> new UsernameNotFoundException("Propositor com email " + emailPropositor + " não encontrado."));

        Livro livroOfertado = livroRepository.findById(requestDTO.getLivroOfertadoId())
                .orElseThrow(() -> new IllegalArgumentException("Livro ofertado com ID " + requestDTO.getLivroOfertadoId() + " não encontrado."));

        Livro livroDesejado = livroRepository.findById(requestDTO.getLivroDesejadoId())
                .orElseThrow(() -> new IllegalArgumentException("Livro desejado com ID " + requestDTO.getLivroDesejadoId() + " não encontrado."));

        // Validações
        if (!livroOfertado.getProprietario().equals(propositor)) {
            throw new IllegalArgumentException("Você não é o proprietário do livro ofertado.");
        }
        if (livroDesejado.getProprietario().equals(propositor)) {
            throw new IllegalArgumentException("Você não pode propor uma troca por um livro que já é seu.");
        }
        if (!livroOfertado.isDisponivel()) {
            throw new IllegalStateException("Seu livro '" + livroOfertado.getTitulo() + "' não está disponível para troca.");
        }
        if (!livroDesejado.isDisponivel()) {
            throw new IllegalStateException("O livro desejado '" + livroDesejado.getTitulo() + "' não está disponível para troca no momento.");
        }

        Usuario receptor = livroDesejado.getProprietario();
        if (receptor == null) { // Checagem de segurança, embora proprietario seja non-null no Livro
            throw new IllegalStateException("O livro desejado não possui um proprietário definido.");
        }


        propostaTrocaRepository.findByLivroOfertadoIdAndLivroDesejadoIdAndPropositorIdAndReceptorIdAndStatus(
                        livroOfertado.getId(), livroDesejado.getId(), propositor.getId(), receptor.getId(), StatusProposta.PENDENTE)
                .ifPresent(p -> {
                    throw new IllegalStateException("Você já possui uma proposta pendente para trocar estes mesmos livros com este usuário.");
                });

        PropostaTroca novaProposta = new PropostaTroca(livroOfertado, livroDesejado, propositor, receptor);

        logger.info("Marcando livro ofertado (ID: {}) como indisponível.", livroOfertado.getId());
        livroOfertado.setDisponivel(false);
        livroRepository.save(livroOfertado);

        PropostaTroca propostaSalva = propostaTrocaRepository.save(novaProposta);
        logger.info("Nova proposta de troca (ID: {}) criada com sucesso entre {} e {}.",
                propostaSalva.getId(), propositor.getEmail(), receptor.getEmail());
        return toResponseDTO(propostaSalva);
    }


    @Transactional
    public PropostaTrocaResponseDTO aceitarProposta(Long propostaId, String emailReceptor) {
        logger.info("Usuário {} tentando aceitar proposta ID: {}", emailReceptor, propostaId);
        PropostaTroca proposta = getPropostaOuLancaExcecao(propostaId);
        Usuario receptor = usuarioRepository.findByEmail(emailReceptor)
                .orElseThrow(() -> new UsernameNotFoundException("Receptor com email " + emailReceptor + " não encontrado."));

        if (!proposta.getReceptor().equals(receptor)) {
            throw new SecurityException("Você não tem permissão para aceitar esta proposta.");
        }
        if (proposta.getStatus() != StatusProposta.PENDENTE) {
            throw new IllegalStateException("Esta proposta não está mais pendente (Status atual: " + proposta.getStatus() + ").");
        }

        proposta.setStatus(StatusProposta.ACEITA);

        logger.info("Marcando livro desejado (ID: {}) como indisponível pois a proposta {} foi aceita.", proposta.getLivroDesejado().getId(), proposta.getId());
        proposta.getLivroDesejado().setDisponivel(false);
        livroRepository.save(proposta.getLivroDesejado());

        PropostaTroca propostaSalva = propostaTrocaRepository.save(proposta);
        logger.info("Proposta ID: {} aceita por {}.", propostaId, emailReceptor);
        return toResponseDTO(propostaSalva);
    }

    @Transactional
    public PropostaTrocaResponseDTO rejeitarProposta(Long propostaId, String emailReceptor) {
        logger.info("Usuário {} tentando rejeitar proposta ID: {}", emailReceptor, propostaId);
        PropostaTroca proposta = getPropostaOuLancaExcecao(propostaId);
        Usuario receptor = usuarioRepository.findByEmail(emailReceptor)
                .orElseThrow(() -> new UsernameNotFoundException("Receptor com email " + emailReceptor + " não encontrado."));

        if (!proposta.getReceptor().equals(receptor)) {
            throw new SecurityException("Você não tem permissão para rejeitar esta proposta.");
        }
        if (proposta.getStatus() != StatusProposta.PENDENTE) {
            throw new IllegalStateException("Esta proposta não está mais pendente (Status atual: " + proposta.getStatus() + ").");
        }

        proposta.setStatus(StatusProposta.REJEITADA);

        logger.info("Tornando livro ofertado (ID: {}) disponível novamente pois a proposta {} foi rejeitada.", proposta.getLivroOfertado().getId(), proposta.getId());
        proposta.getLivroOfertado().setDisponivel(true);
        livroRepository.save(proposta.getLivroOfertado());

        PropostaTroca propostaSalva = propostaTrocaRepository.save(proposta);
        logger.info("Proposta ID: {} rejeitada por {}.", propostaId, emailReceptor);
        return toResponseDTO(propostaSalva);
    }

    @Transactional
    public PropostaTrocaResponseDTO cancelarProposta(Long propostaId, String emailPropositor) {
        logger.info("Usuário {} tentando cancelar proposta ID: {}", emailPropositor, propostaId);
        PropostaTroca proposta = getPropostaOuLancaExcecao(propostaId);
        Usuario propositor = usuarioRepository.findByEmail(emailPropositor)
                .orElseThrow(() -> new UsernameNotFoundException("Propositor com email " + emailPropositor + " não encontrado."));

        if (!proposta.getPropositor().equals(propositor)) {
            throw new SecurityException("Você não tem permissão para cancelar esta proposta.");
        }
        if (proposta.getStatus() != StatusProposta.PENDENTE) {
            throw new IllegalStateException("Esta proposta não pode mais ser cancelada (Status atual: " + proposta.getStatus() + ").");
        }

        proposta.setStatus(StatusProposta.CANCELADA);

        logger.info("Tornando livro ofertado (ID: {}) disponível novamente pois a proposta {} foi cancelada.", proposta.getLivroOfertado().getId(), proposta.getId());
        proposta.getLivroOfertado().setDisponivel(true);
        livroRepository.save(proposta.getLivroOfertado());

        PropostaTroca propostaSalva = propostaTrocaRepository.save(proposta);
        logger.info("Proposta ID: {} cancelada por {}.", propostaId, emailPropositor);
        return toResponseDTO(propostaSalva);
    }


    @Transactional
    public PropostaTrocaResponseDTO concluirTroca(Long propostaId, String emailUsuarioAcao) {
        logger.info("Tentando concluir troca para proposta ID: {} pelo usuário: {}", propostaId, emailUsuarioAcao);

        PropostaTroca proposta = getPropostaOuLancaExcecao(propostaId);
        logger.debug("Proposta encontrada: ID {}", proposta.getId());

        Usuario usuarioAcao = usuarioRepository.findByEmail(emailUsuarioAcao)
                .orElseThrow(() -> {
                    logger.warn("Usuário da ação {} não encontrado ao tentar concluir troca {}", emailUsuarioAcao, propostaId);
                    return new UsernameNotFoundException("Usuário da ação não encontrado.");
                });

        if (!proposta.getPropositor().equals(usuarioAcao) && !proposta.getReceptor().equals(usuarioAcao)) {
            logger.warn("Usuário {} não autorizado a concluir proposta {}", emailUsuarioAcao, propostaId);
            throw new SecurityException("Você não tem permissão para concluir esta proposta.");
        }
        if (proposta.getStatus() != StatusProposta.ACEITA) {
            logger.warn("Tentativa de concluir proposta {} com status {} (esperado ACEITA)", propostaId, proposta.getStatus());
            throw new IllegalStateException("Apenas propostas aceitas podem ser concluídas.");
        }

        Livro livroOfertado = proposta.getLivroOfertado();
        Livro livroDesejado = proposta.getLivroDesejado();
        Usuario propositorOriginal = proposta.getPropositor();
        Usuario receptorOriginal = proposta.getReceptor();

        logger.info("Livro Ofertado ID: {}, Título: '{}', Proprietário Original ID: {}",
                livroOfertado.getId(), livroOfertado.getTitulo(),
                (livroOfertado.getProprietario() != null ? livroOfertado.getProprietario().getId() : "NULO"));
        logger.info("Livro Desejado ID: {}, Título: '{}', Proprietário Original ID: {}",
                livroDesejado.getId(), livroDesejado.getTitulo(),
                (livroDesejado.getProprietario() != null ? livroDesejado.getProprietario().getId() : "NULO"));
        logger.info("Propositor Original ID: {}, Nome: {}", propositorOriginal.getId(), propositorOriginal.getNome());
        logger.info("Receptor Original ID: {}, Nome: {}", receptorOriginal.getId(), receptorOriginal.getNome());

        logger.debug("Atualizando proprietário do Livro Ofertado (ID: {}) para Receptor (ID: {})", livroOfertado.getId(), receptorOriginal.getId());
        livroOfertado.setProprietario(receptorOriginal);

        logger.debug("Atualizando proprietário do Livro Desejado (ID: {}) para Propositor (ID: {})", livroDesejado.getId(), propositorOriginal.getId());
        livroDesejado.setProprietario(propositorOriginal);

        livroOfertado.setDisponivel(true);
        livroDesejado.setDisponivel(true);
        logger.debug("Livro Ofertado (ID: {}) e Livro Desejado (ID: {}) definidos como disponíveis.", livroOfertado.getId(), livroDesejado.getId());

        logger.info("Salvando Livro Ofertado (ID: {}) com novo proprietário (ID: {})", livroOfertado.getId(), livroOfertado.getProprietario().getId());
        Livro livroOfertadoSalvo = livroRepository.save(livroOfertado);
        logger.info("Salvando Livro Desejado (ID: {}) com novo proprietário (ID: {})", livroDesejado.getId(), livroDesejado.getProprietario().getId());
        Livro livroDesejadoSalvo = livroRepository.save(livroDesejado);

        if (livroOfertadoSalvo.getProprietario().equals(receptorOriginal)) {
            logger.info("CONFIRMADO: Livro Ofertado (ID: {}) agora pertence ao Receptor (ID: {}).", livroOfertadoSalvo.getId(), receptorOriginal.getId());
        } else {
            logger.error("ERRO NA TROCA (Livro Ofertado): Esperado prop. {}, mas é {}.", receptorOriginal.getId(), livroOfertadoSalvo.getProprietario().getId());
        }

        if (livroDesejadoSalvo.getProprietario().equals(propositorOriginal)) {
            logger.info("CONFIRMADO: Livro Desejado (ID: {}) agora pertence ao Propositor (ID: {}).", livroDesejadoSalvo.getId(), propositorOriginal.getId());
        } else {
            logger.error("ERRO NA TROCA (Livro Desejado): Esperado prop. {}, mas é {}.", propositorOriginal.getId(), livroDesejadoSalvo.getProprietario().getId());
        }

        proposta.setStatus(StatusProposta.CONCLUIDA);
        logger.info("Atualizando status da Proposta (ID: {}) para CONCLUIDA.", proposta.getId());
        PropostaTroca propostaSalva = propostaTrocaRepository.save(proposta);

        logger.info("Troca para proposta ID: {} concluída com sucesso pelo usuário {}.", propostaId, emailUsuarioAcao);
        return toResponseDTO(propostaSalva);
    }

    @Transactional(readOnly = true)
    public List<PropostaTrocaResponseDTO> getPropostasRecebidas(String emailReceptor) {
        Usuario receptor = usuarioRepository.findByEmail(emailReceptor)
                .orElseThrow(() -> new UsernameNotFoundException("Receptor não encontrado."));
        return propostaTrocaRepository.findByReceptor(receptor).stream()
                .filter(p -> p.getStatus() == StatusProposta.PENDENTE)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PropostaTrocaResponseDTO> getPropostasEnviadas(String emailPropositor) {
        Usuario propositor = usuarioRepository.findByEmail(emailPropositor)
                .orElseThrow(() -> new UsernameNotFoundException("Propositor não encontrado."));
        return propostaTrocaRepository.findByPropositor(propositor).stream()
                // Filtra para mostrar pendentes ou aceitas, já que outras vão para o histórico
                .filter(p -> p.getStatus() == StatusProposta.PENDENTE || p.getStatus() == StatusProposta.ACEITA)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PropostaTrocaResponseDTO> getHistoricoTrocas(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        List<StatusProposta> statusesHistorico = Arrays.asList(
                StatusProposta.CONCLUIDA,
                StatusProposta.REJEITADA,
                StatusProposta.CANCELADA
        );
        return propostaTrocaRepository.findByUsuarioAndStatusIn(usuario, statusesHistorico).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}