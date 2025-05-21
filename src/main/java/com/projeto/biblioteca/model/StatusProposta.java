package com.projeto.biblioteca.model;

public enum StatusProposta {
    PENDENTE,   // Proposta enviada, aguardando resposta do receptor
    ACEITA,     // Receptor aceitou a proposta, livros reservados
    REJEITADA,  // Receptor recusou a proposta
    CANCELADA,  // Propositor cancelou antes da resposta
    CONCLUIDA   // Troca efetivada (livros trocaram de dono)
}