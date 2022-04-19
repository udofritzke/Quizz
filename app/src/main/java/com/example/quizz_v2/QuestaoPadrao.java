package com.example.quizz_v2;

import java.util.UUID;

/*
Classe de modelo de domínio que importa dados de questões/afirmações do arquivo res/values/strings.xml
 */
public class QuestaoPadrao {
    private UUID mId;
    private int mIdAfirmacao;
    private boolean mCorreta;

    public QuestaoPadrao(int idAfirmacao, boolean correta) {
        mIdAfirmacao = idAfirmacao;
        mCorreta = correta;
        mId = UUID.randomUUID();
    }

    public UUID getId(){return mId;};
    
    public int getIdAfirmacao() {
        return mIdAfirmacao;
    }

    public void setIdAfirmacao(int idAfirmacao) {
        mIdAfirmacao = idAfirmacao;
    }

    public boolean isCorreta() {
        return mCorreta;
    }

    public void setCorreta(boolean correta) {
        mCorreta = correta;
    }
}
