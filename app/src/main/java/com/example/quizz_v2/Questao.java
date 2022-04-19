package com.example.quizz_v2;

import java.io.Serializable;
import java.util.UUID;
/*
Classe de modelo de domínio utilizada para cadastro e recuperação de questões do SQLite
 */
public class Questao implements Serializable {
    private UUID mId;
    private String mAfirmacao;
    private boolean mCorreta;

    public Questao(String afirmacao, boolean correta) {
        mAfirmacao = afirmacao;
        mCorreta = correta;
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getAfirmacao() {
        return mAfirmacao;
    }

    public void setAfirmacao(String afirmacao) {
        mAfirmacao = afirmacao;
    }

    public boolean isCorreta() {
        return mCorreta;
    }

    public void setCorreta(boolean correta) {
        mCorreta = correta;
    }
}
