package com.example.quizz_v2;

import java.io.Serializable;
import java.util.ArrayList;

//A classe é serializável para poder ser enviada via intents.
//Entretanto, as questões são (nesta versão do app) compartilhadas entre acts via bd

public class ListaQuestoes implements Serializable {
    ArrayList <Questao> mlistaQuestoes= new ArrayList<Questao>();

    void addQuestao(Questao q){
        mlistaQuestoes.add(q);
    }

    public ArrayList<Questao> getMlistaQuestoes() {
        return mlistaQuestoes;
    }

    public void setMlistaQuestoes(ArrayList<Questao> mlistaQuestoes) {
        this.mlistaQuestoes = mlistaQuestoes;
    }
}
