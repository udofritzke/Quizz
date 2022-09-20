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

        mListaQuestoes = new ListaQuestoes();
    }

    public void mudaParaMainActivity(View v) {
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