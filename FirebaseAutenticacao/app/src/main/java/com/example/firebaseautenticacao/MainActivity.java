package com.example.firebaseautenticacao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView  cardView_LoginFacebook, cardView_LoginGoogle, cardView_LoginAnonimo, cardView_LoginEmail, cardView_LoginCadastrar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GoogleSignInClient googleSignInClient;

    // Gerenciamento de respostas do Facebook
    private CallbackManager callbackManager;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView_LoginFacebook = (CardView) findViewById(R.id.cardView_LoginFacebook);
        cardView_LoginGoogle = (CardView) findViewById(R.id.cardView_LoginGoogle);
        cardView_LoginEmail = (CardView) findViewById(R.id.cardView_LoginEmail);
        cardView_LoginCadastrar = (CardView) findViewById(R.id.cardView_LoginCadastrar);
        cardView_LoginAnonimo = (CardView) findViewById(R.id.cardView_LoginAnonimo);


        cardView_LoginFacebook.setOnClickListener(this);
        cardView_LoginGoogle.setOnClickListener(this);
        cardView_LoginEmail.setOnClickListener(this);
        cardView_LoginCadastrar.setOnClickListener(this);
        cardView_LoginAnonimo.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        servicosAutenticacao();
        servicosGoogle();
        servicosFacebook();

    }
    //--------------------- SERVIÇOS LOGIN ------------------
    private void servicosFacebook(){

        callbackManager = CallbackManager.Factory.create();

        // Códigos Login Facebook
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Capturar token de acesso ao facebook
                AccessToken count = loginResult.getAccessToken();
                adicionarContaFacebookaoFirebase(count);

            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(),"Cancelado" ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                String resultado = error.getMessage();
                Util.opcoesErro(getBaseContext(), resultado);
            }
        });

    }

    private void servicosGoogle(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    //Monitora o estado do usuario
    private void servicosAutenticacao(){
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null && user.isEmailVerified()){
                    //Toast.makeText(getBaseContext(),"Usuário "+user.getEmail() + " está logado" ,Toast.LENGTH_LONG).show();
                }else{

                }
            }
        };
    }

    //-------------------TRATAMENTO DE CLICKS--------------------
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardView_LoginFacebook:
                signInFacebook();
                break;
            case R.id.cardView_LoginGoogle:
                signInGoogle();
                break;
            case R.id.cardView_LoginEmail:
                signInEmail();
                break;
            case R.id.cardView_LoginCadastrar:
                startActivity(new Intent(this, CadastrarActivity.class));
                break;
            case R.id.cardView_LoginAnonimo:
                signInAnonimo();
                break;
        }
    }

    //--------------------------------------METODOS DE LOGIN------------------------------------
    private void signInFacebook(){
        //Permissão de leitura de informações de usuário com LoginFacebook
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));

    }

    private void signInGoogle(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null){
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 555);
        } else {
             // já existe alguém conectado pelo google
            Toast.makeText(getBaseContext(), "Usuário já logado", Toast.LENGTH_LONG).show();
            //startActivity(new Intent(getBaseContext(), PrincipalActivity.class));

        }

    }

    private void signInEmail(){
        user = auth.getCurrentUser();
        if (user != null && user.isEmailVerified()){
            finish();
            startActivity(new Intent(this, PrincipalActivity.class));
        }  else{
            startActivity(new Intent(this, LoginEmailActivity.class));

        }
//        if (user == null){
//
//            startActivity(new Intent(this, LoginEmailActivity.class));
//        }else{
//            finish();
//            startActivity(new Intent(this, PrincipalActivity.class));
//        }
    }

    private void signInAnonimo(){

        acessarContaAnonimaaoFirebase();

    }


    //------------------------------ AUTENTICAÇÃO NO FIREBASE ----------------------------------


    private void adicionarContaFacebookaoFirebase(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
                        } else {
                            String resultado = task.getException().toString();
                            Util.opcoesErro(getBaseContext(), resultado);
                        }

                        // ...
                    }
                });
    }

    private void adicionarContaGoogleaoFirebase(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
                        } else {
                            String resultado = task.getException().toString();
                            Util.opcoesErro(getBaseContext(), resultado);
                        }

                        // ...
                    }
                });
    }

    private void acessarContaAnonimaaoFirebase(){
        auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                    startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
                                } else {
                                    String resultado = task.getException().toString();
                                    Util.opcoesErro(getBaseContext(), resultado);
                                }

                                // ...
                            }
                        });
    }

    //--------------------------------MÉTODOS DA ACTIVITY-------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Armazena os dados de resposta do facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 555){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                adicionarContaGoogleaoFirebase(account);
            }catch(ApiException e){
                String resultado = e.getMessage();
                Util.opcoesErro(getBaseContext(), resultado);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
