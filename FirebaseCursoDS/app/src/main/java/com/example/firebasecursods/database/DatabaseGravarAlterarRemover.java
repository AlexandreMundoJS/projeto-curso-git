package com.example.firebasecursods.database;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasecursods.R;
import com.example.firebasecursods.util.DialogAlerta;
import com.example.firebasecursods.util.DialogProgress;
import com.example.firebasecursods.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DatabaseGravarAlterarRemover extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_NomePasta, editText_Nome, editText_Idade;
    private Button button_Salvar, button_Alterar, button_Remover;
    private FirebaseDatabase database;
    private boolean firebaseOffline = false;

    private DialogProgress progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_gravar_alterar_remover_activity);

        editText_NomePasta = (EditText) findViewById(R.id.editText_Database_GravarAlterarRemover_NomePasta);
        editText_Nome = (EditText) findViewById(R.id.editText_Database_GravarAlterarRemover_Nome);
        editText_Idade = (EditText) findViewById(R.id.editText_Database_GravarAlterarRemover_Idade);

        button_Salvar = (Button) findViewById(R.id.button_Database_GravarAlterarRemover_Salvar);
        button_Alterar = (Button) findViewById(R.id.button_Database_GravarAlterarRemover_Alterar);
        button_Remover = (Button) findViewById(R.id.button_Database_GravarAlterarRemover_Remover);

        button_Salvar.setOnClickListener(this);
        button_Alterar.setOnClickListener(this);
        button_Remover.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        //ativarFirebaseOffline();
    }

    // ------------------------------ MANTÉM O APP FUNCIONANDO OFFLINE -------------------------
    private void ativarFirebaseOffline(){
        try{
            if(!firebaseOffline){
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                firebaseOffline = true;
            }else{
                // firebase funcionando offline
            }
        }catch(Exception e){
            Toast.makeText(getBaseContext(),"Erro", Toast.LENGTH_LONG).show();
        }

    }
    //------------------------------ TRATAMENTO DE CLICKS ------------------------------
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_Database_GravarAlterarRemover_Salvar:
                buttonSalvar();
                break;
            case R.id.button_Database_GravarAlterarRemover_Alterar:
                buttonAlterar();
                break;
            case R.id.button_Database_GravarAlterarRemover_Remover:
                buttonRemover();
                break;
        }
    }
    //------------------------------ SALVAR, ALTERAR E REMOVER DADOS ------------------------------
    private void buttonSalvar(){
        String nome = editText_Nome.getText().toString();
        String idade_String = editText_Idade.getText().toString();

        if(Util.verificarCampos(getBaseContext(), nome, idade_String)){
            int idade = Integer.parseInt(idade_String);

            salvarDados(nome, idade);
        }

    }

    private void buttonAlterar(){
        String nome = editText_Nome.getText().toString();
        String idade_String = editText_Idade.getText().toString();

        if(Util.verificarCampos(getBaseContext(), nome, idade_String)){
            int idade = Integer.parseInt(idade_String);

            alterarDados(nome, idade);
        }

    }

    private void buttonRemover(){
        String nome_Pasta = editText_NomePasta.getText().toString();

        if (nome_Pasta.isEmpty()){
            removerDados(nome_Pasta);
        }else{
            Toast.makeText(getBaseContext(), "Preencha o campo com o nome da pasta que você quer alterar", Toast.LENGTH_LONG).show();

        }
    }

    //------------------------------ SALVAR, ALTERAR E REMOVER DADOS ------------------------------

    private void salvarDados(String nome, int idade){
        progress = new DialogProgress();
        progress.show(getFragmentManager(), "1");
        DatabaseReference reference = database.getReference().child("BD").child("Gerentes");
        Gerente gerente = new Gerente(nome, idade, false);

        reference.push().setValue(gerente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Gravado com sucesso!", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }else{
                    Toast.makeText(getBaseContext(), "Erro ao gravar dados!", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            }
        });

    }

    private void alterarDados(String nome, int idade){
        progress = new DialogProgress();
        progress.show(getFragmentManager(), "1");
        String nome_Pasta = editText_NomePasta.getText().toString();

        if (nome_Pasta.isEmpty()){
            DatabaseReference reference = database.getReference().child("BD").child("Gerentes");
            Gerente gerente = new Gerente(nome, idade, false);

            Map<String, Object> atualizacao = new HashMap<>();
            atualizacao.put(nome_Pasta, gerente);
            reference.updateChildren(atualizacao).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getBaseContext(), "Alterado com sucesso!", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }else{
                        Toast.makeText(getBaseContext(), "Erro ao alterar dados!", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                }
            });
        }else{
            progress.dismiss();
            DialogAlerta alerta = new DialogAlerta("Erro", "Preencha o campo com o nome da pasta que você quer excluir.");
            alerta.show(getFragmentManager(),"1");
            //Toast.makeText(getBaseContext(), "Preencha o campo com o nome da pasta que você quer excluir", Toast.LENGTH_LONG).show();

        }
    }


    private void removerDados(String nome_Pasta){
        progress = new DialogProgress();
        progress.show(getFragmentManager(), "1");
        DatabaseReference reference = database.getReference().child("BD").child("Gerentes");

        reference.child(nome_Pasta).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Excluído com sucesso!", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }else{
                    DialogAlerta alerta = new DialogAlerta("Erro", "Preencha o campo com o nome da pasta que você quer excluir.");
                    alerta.show(getFragmentManager(),"1");
                    progress.dismiss();
                }
            }
        });

    }
}
