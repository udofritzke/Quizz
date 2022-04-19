package com.example.quizz_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class CadastroActivity extends AppCompatActivity {
    ListaQuestoes mListaQuestoes;
    TextView mTextViewAfirmacao;
    CheckBox mCheckBox;
    QuestaoDB mQuestoesDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Intent intent = getIntent();
        //mListaQuestoes = (ListaQuestoes) intent.getSerializableExtra("questoes");
        //Lista de questões virá apenas no sentido cadastro->main
        mListaQuestoes = new ListaQuestoes();
    }

    public void mudaParaMainActivity(View v) {
        //Comunicação entre acts via bd (não via intents)
        /*
        ListaQuestoes listaQuestoes = new ListaQuestoes();
        if (mQuestoesDb == null) {
            mQuestoesDb = new QuestaoDB(getBaseContext());
        }
        Cursor cursor = mQuestoesDb.queryQuestao(null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                String questao = cursor.getString(3);
                listaQuestoes.addQuestao(new Questao(questao, true));
                cursor.moveToNext();
                Log.d("cadastro", questao);
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("questoes", listaQuestoes);
        */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void cadastraQuestao(View v) {
        mTextViewAfirmacao = (TextView) findViewById(R.id.edição_texto_questão);
        mCheckBox = (CheckBox) findViewById(R.id.afirmacao_correta);
        if (mTextViewAfirmacao != null && mCheckBox != null) {
            Log.d("cadastro", "salvando no SQLite");
            CharSequence txt = mTextViewAfirmacao.getText();
            boolean eh_correta;
            if (mCheckBox.isChecked()) eh_correta = true;
            else eh_correta = false;
            Questao q = new Questao(txt.toString(), eh_correta);

            mListaQuestoes.addQuestao(q);
            // insere questões no SQLite
            if (mQuestoesDb == null) {
                mQuestoesDb = new QuestaoDB(getBaseContext());
            }
            mQuestoesDb.addQuestao(q);
            Log.d("cadastro", "salvou no SQLite: " + q.getAfirmacao()+" "+q.isCorreta());

        } else
            Log.d("cadastro", "não salvou no SQLite");
    }

    public void removeCadastros(View v) {
        if (mQuestoesDb == null) {
            mQuestoesDb = new QuestaoDB(getBaseContext());
        }
        mQuestoesDb.removeBanco();
        Log.d("cadastro", "removeu o banco");
    }
}