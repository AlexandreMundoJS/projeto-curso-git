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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_Email, editText_Senha;
    private Button button_Login, button_RecuperarSenha;
    private FirebaseAuth auth;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginemail);

        editText_Email = (EditText) findViewById(R.id.editText_EmailLogin);
        editText_Senha = (EditText) findViewById(R.id.editText_SenhaLogin);
        button_Login = (Button) findViewById(R.id.button_OkLogin);
        button_RecuperarSenha = (Button) findViewById(R.id.button_Recuperar);

        button_Login.setOnClickListener(this);
        button_RecuperarSenha.setOnClickListener(this);

        //Acesso aos recursos de autenticação do firebase
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_OkLogin:
                loginEmail();
                break;
            case R.id.button_Recuperar:
                recuperarSenha();
                break;
        }
    }

    private void recuperarSenha(){
        String email = editText_Email.getText().toString().trim();

        if (email.isEmpty()){
            Toast.makeText(getBaseContext(), "Insira o seu e-mail para recuperar sua senha", Toast.LENGTH_LONG).show();
        } else {
            enviarEmail(email);
        }

    }

    private void enviarEmail(String email){

        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getBaseContext(),"Enviamos uma mensagem para o seu e-mail com um link para redefinir a senha", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String erro = e.toString();
                Util.opcoesErro(getBaseContext(), erro);
            }
        });

    }

    private void loginEmail(){

        String email = editText_Email.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()){
            Toast.makeText(getBaseContext(), "Insira os campos obrigatórios", Toast.LENGTH_LONG).show();
        }else{
            if (Util.verificarInternet(this)){
                //getSystemService só pode ser invocado em uma activity
                ConnectivityManager conexao = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                confirmarLoginEmail(email, senha);
            }else{
                Toast.makeText(getBaseContext(), "Erro - Verifique sua conexão com a internet", Toast.LENGTH_LONG).show();
            }
        }

    }


    private void confirmarLoginEmail(String email, String senha){
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            //Task responde pelo firebase
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null && user.isEmailVerified()){
                        startActivity(new Intent(getBaseContext(),PrincipalActivity.class));
                        Toast.makeText(getBaseContext(), "Usuário logado com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(getBaseContext(), "Seu e-mail ainda não foi verificado.", Toast.LENGTH_LONG).show();

                    }

                }else{
                    String resposta = task.getException().toString();
                    Util.opcoesErro(getBaseContext(), resposta);
                    //Toast.makeText(getBaseContext(), "Erro ao logar usuário", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
