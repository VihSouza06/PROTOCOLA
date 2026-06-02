package com.example.aep2b.enums;

public enum Categoria {
    ILUMINACAO("Iluminação"),
    BURACO("Buraco"),
    LIMPEZA("Limpeza"),
    SAUDE("Saúde"),
    SEGURANCA_ESCOLAR("Segurança Escolar");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
