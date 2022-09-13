package com.example.quizz_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* A fazer:
    1- (resolvido)) Quando retorna da tela de cadastro após remover o banco o app falha
    2- (resolvido) quando vai para a activity de cadastro (mudaParaCadastroActivity()) sem passar uma lista como intent o app falha
    3- (resolvido) app falha quando se navega na main act sem questões cadastradas
    4- (resolvido) Verificação da correção da resposta sempre retorna "acertou" quando se pressiona Verdadeiro e
       "errou" quando se pressiona Falso
    5- (resolvido: comunicação via bd ao inves de intents) como verificar se a act veio de uma inicialização do app ou de uma outra act
 */

/* commit 2022-2 a */

public class MainActivity<mudaParaCadastroActivity> extends AppCompatActivity {
    private TextView mTextViewAfirmacao;
    private Button mBotaoVerdadeiro;
    private Button mBotaoFalso;
    private Button mBotaoProximo;

    QuestaoDB mQuestoesDb;
    private int mIndiceQuestaoAtual = 0;
    ListaQuestoes mListaQuestoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIndiceQuestaoAtual = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Comunicação entre activities via bd (não via intents)
        //Intent intent = getIntent();
        //mListaQuestoes = (ListaQuestoes) intent.getSerializableExtra("questoes");

        boolean haQuestoes = buscaQuestoesNoBD();
        apresentaBotoes(haQuestoes);

        /* Atualiza visão de texto */
        if (mListaQuestoes != null)
            atualizaTextoAfirmacao();

        /* define tratador do botão Verdadeiro */
        mBotaoVerdadeiro = (Button) findViewById(R.id.botao_verdadeiro);
        // utilização de classe anônima interna
        mBotaoVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaResposta(true);
            }
        });

        /* define tratador do botão Falso */
        mBotaoFalso = (Button) findViewById(R.id.botao_falso);
        // utilização de classe anônima interna
        mBotaoFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaResposta(false);
            }
        });
        mBotaoProximo = (Button) findViewById(R.id.botao_proximo);
        mBotaoProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListaQuestoes.getMlistaQuestoes().size() > 0) {
                    mIndiceQuestaoAtual = (mIndiceQuestaoAtual + 1) % mListaQuestoes.getMlistaQuestoes().size();
                    atualizaTextoAfirmacao();
                }
            }
        });
    }

    void mostraToastAcertou() {
        int id_toast = R.string.texto_acertou;
        Toast.makeText(this, id_toast, Toast.LENGTH_SHORT).show();
    }

    void mostraToastErrou() {
        int id_toast = R.string.texto_errou;
        Toast.makeText(this, id_toast, Toast.LENGTH_SHORT).show();
    }

    void atualizaTextoAfirmacao() {
        if (mListaQuestoes != null) {
            ArrayList<Questao> al = mListaQuestoes.getMlistaQuestoes();
            if (al != null) {
                if (al.size() > 0) {
                    Questao q = al.get(mIndiceQuestaoAtual);
                    if (q != null) {
                        mTextViewAfirmacao = (TextView) findViewById(R.id.view_texto_da_afirmação);
                        mTextViewAfirmacao.setText(q.getAfirmacao());
                        Log.d("main", q.getAfirmacao());
                    } else Log.d("main", "não há questão");
                } else {
                    mTextViewAfirmacao = (TextView) findViewById(R.id.view_texto_da_afirmação);
                    mTextViewAfirmacao.setText("Não há questões cadastradas");
                    Log.d("main", "Bd questões vazio");
                }
            } else Log.d("main", "não array list");
        } else Log.d("main", "não há mListaQuestoes");
    }

    private void verificaResposta(boolean respostaPressionada) {
        if (mListaQuestoes != null) {
            ArrayList<Questao> al = mListaQuestoes.getMlistaQuestoes();
            if (al.size() > 0) {
                boolean respostaCorreta = mListaQuestoes.getMlistaQuestoes().get(mIndiceQuestaoAtual).isCorreta();
                if (respostaPressionada == respostaCorreta) {
                    mostraToastAcertou();
                } else
                    mostraToastErrou();
            }
        }
    }

    // tratador do botão para mudança para a activity de cadastro
    public void mudaParaCadastroActivity(View v) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    boolean buscaQuestoesNoBD() {
        boolean haQuestoes = false;
        // se não houver questões iniciadas em memória, busca no banco
        if (mListaQuestoes == null) {
            mListaQuestoes = new ListaQuestoes();
            if (mQuestoesDb == null) {
                mQuestoesDb = new QuestaoDB(getBaseContext());
            }
            Cursor cursor = mQuestoesDb.queryQuestao(null, null);

            if (cursor.getCount() > 0) {
                haQuestoes = true;
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    String afirmacao = cursor.getString(3);
                    int ehcorreta = cursor.getInt(2);
                    mListaQuestoes.addQuestao(new Questao(afirmacao, ehcorreta == 0 ? false : true));
                    cursor.moveToNext();
                    Log.d("main", afirmacao + " " + ehcorreta);
                }
            } else haQuestoes = false;
        }
        return haQuestoes;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("main", "onStart");
    }

    void apresentaBotoes(boolean haQuestoes) {
        if (haQuestoes == false) {
            mBotaoFalso = (Button) findViewById(R.id.botao_falso);
            mBotaoFalso.setVisibility(View.GONE);
            mBotaoVerdadeiro = (Button) findViewById(R.id.botao_verdadeiro);
            mBotaoVerdadeiro.setVisibility(View.GONE);
            mBotaoProximo = (Button) findViewById(R.id.botao_proximo);
            mBotaoProximo.setVisibility(View.GONE);
        } else {
            mBotaoFalso = (Button) findViewById(R.id.botao_falso);
            mBotaoFalso.setVisibility(View.VISIBLE);
            mBotaoVerdadeiro = (Button) findViewById(R.id.botao_verdadeiro);
            mBotaoVerdadeiro.setVisibility(View.VISIBLE);
            mBotaoProximo = (Button) findViewById(R.id.botao_proximo);
            mBotaoProximo.setVisibility(View.VISIBLE);
        }
    }
}