package com.example.firebaseautenticacao;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastrarActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_Email, editText_Senha, editText_SenhaRepetir;
    private Button button_Cadastrar, button_Cancelar;
    private FirebaseAuth auth;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        editText_Email = (EditText) findViewById(R.id.editText_EmailCadastro);
        editText_Senha = (EditText) findViewById(R.id.editText_SenhaCadastro);
        editText_SenhaRepetir = (EditText) findViewById(R.id.editText_SenhaRepetirCadastro);

        button_Cadastrar = (Button) findViewById(R.id.button_CadastrarUsuario);
        button_Cancelar = (Button) findViewById(R.id.button_Cancelar);

        button_Cadastrar.setOnClickListener(this);
        //button_Cancelar.setOnClickListener(this);

        //Autenticação firebase
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_CadastrarUsuario:
                cadastrar();
                break;
        }
    }

    private void cadastrar(){
        //Criar usuário com email e senha = ADICONAR USUARIO AUTENTICAÇÃO FIREBASE
        //addOnCompleteListener = tratamento de erro(E=mail completo)
        String email = editText_Email.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();
        String confirmaSenha = editText_SenhaRepetir.getText().toString().trim();

        //Tratamento de erros
        // Se algum dos campos estiverem vazios
        if (email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()) {
            Toast.makeText(getBaseContext(), "Erro - preencha os campos vazios", Toast.LENGTH_LONG).show();
        }else{
            //Verifica se as senhas sao iguais
            if (senha.contentEquals(confirmaSenha)){
                if (Util.verificarInternet(this)) {
                    criarUsuario(email, senha);
                }else{
                    Toast.makeText(getBaseContext(), "Erro - Verifique sua conexão com a internet", Toast.LENGTH_LONG).show();

                }
            }else{
                Toast.makeText(getBaseContext(), "Erro - Senhas diferentes", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void criarUsuario(String email, String senha){
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            //Firebase responde se houve sucesso ou erro através da task
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();

                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(getBaseContext(), "Acesse seu e-mail e valide seu cadastro.", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getBaseContext(), "Falha ao enviar e-mail de confirmação. Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

//                    startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
//                    Toast.makeText(getBaseContext(), "Cadastro Efetuado com sucesso", Toast.LENGTH_LONG).show();
//                    finish();
                }else{
                    String resposta = task.getException().toString();
                    Util.opcoesErro(getBaseContext(), resposta);
                }
            }
        });
    }

}

