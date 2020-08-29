package com.example.firebasecursods;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.firebasecursods.database.DatabaseGravarAlterarRemover;
import com.example.firebasecursods.database.DatabaseLerDadosActivity;
import com.example.firebasecursods.database_lista_empresa.DatabaseListaEmpresaActivity;
import com.example.firebasecursods.storage.StorageDownloadActivity;
import com.example.firebasecursods.storage.StorageUploadActivity;
import com.example.firebasecursods.util.Permissao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardView_Storage_Download, cardView_Storage_Upload, cardView_Database_LerDados, cardView_Database_GravarAlterarExcluir, cardView_Empresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView_Storage_Download = (CardView) findViewById(R.id.cardView_Storage_Download);
        cardView_Storage_Upload = (CardView) findViewById(R.id.cardView_Storage_Upload);
        cardView_Database_LerDados = (CardView) findViewById(R.id.cardView_Database_LerDados);
        cardView_Database_GravarAlterarExcluir = (CardView) findViewById(R.id.cardView_Database_GravarAlterarExcluir);
        cardView_Empresa = (CardView) findViewById(R.id.cardView_Empresas);

        cardView_Storage_Download.setOnClickListener(this);
        cardView_Storage_Upload.setOnClickListener(this);
        cardView_Database_LerDados.setOnClickListener(this);
        cardView_Database_GravarAlterarExcluir.setOnClickListener(this);
        cardView_Empresa.setOnClickListener(this);

        permissao();

    }
    // ---------------- TRATAMENTO DE CLICKS ----------------
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cardView_Storage_Download:
                Intent intent = new Intent(getBaseContext(), StorageDownloadActivity.class);
                startActivity(intent);
                break;
            case R.id.cardView_Storage_Upload:
                startActivity(new Intent(getBaseContext(), StorageUploadActivity.class));
                break;
            case R.id.cardView_Database_LerDados:
                startActivity(new Intent(getBaseContext(), DatabaseLerDadosActivity.class));
                break;
            case R.id.cardView_Database_GravarAlterarExcluir:
                startActivity(new Intent(getBaseContext(), DatabaseGravarAlterarRemover.class));
                break;
            case R.id.cardView_Empresas:
                startActivity(new Intent(getBaseContext(), DatabaseListaEmpresaActivity.class));

                break;
        }
    }

    // ---------------- PERMISSÃO DO USUÁRIO ----------------

    private void permissao(){
        String permissoes [] = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        Permissao.permissao(this, 0, permissoes);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result: grantResults){
            if (result == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Aceite as permissões para o aplicativo funcionar corretamente.", Toast.LENGTH_LONG).show();
                finish();
                break;
            }
        }
    }
}
