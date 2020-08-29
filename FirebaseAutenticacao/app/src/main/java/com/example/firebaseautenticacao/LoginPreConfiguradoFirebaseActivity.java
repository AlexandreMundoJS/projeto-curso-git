package com.example.firebaseautenticacao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

public class LoginPreConfiguradoFirebaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pre_configurado_firebase);

        loginpreConfiguradoFirebase();

    }

    private void loginpreConfiguradoFirebase(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.mundojs)
                        .build(),
                1999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1999){
            if( resultCode == RESULT_OK){
                //Sucesso
                finish();
                startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
            } else {
                Toast.makeText(getBaseContext(), "Erro ao fazer login", Toast.LENGTH_LONG).show();
            }
        }
    }
}
