package com.example.firebasecursods.database_lista_funcionario;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.firebasecursods.R;
import com.example.firebasecursods.database_lista_empresa.Empresa;
import com.example.firebasecursods.util.DialogAlerta;
import com.example.firebasecursods.util.DialogProgress;
import com.example.firebasecursods.util.PdfCreator;
import com.example.firebasecursods.util.Util;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseListaFuncionarioActivity extends AppCompatActivity implements View.OnClickListener, RecyclerView_ListaFuncionario.ClickFuncionario, Runnable {

    private LinearLayout linearLayout;
    private ImageView imageView_LimparCampos, imageView_Galeria;
    private EditText editText_Nome, editText_Idade;

    private Button button_Salvar;
    private RecyclerView recyclerView;
    private RecyclerView_ListaFuncionario recyclerView_listaFuncionario;
    private List<Funcionario> funcionarios = new ArrayList<Funcionario>();

    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private Uri uri_imagem = null;
    private ChildEventListener childEventListener;
    private DatabaseReference reference_database;
    private List<String> keys = new ArrayList<String>();

    private Empresa empresa;
    private DialogProgress progress;
    private boolean imagem_Selecionada = false;
    private boolean firebaseOffline = false;
    private Handler handler;
    private Thread thread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_lista_funcionario_activity);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_Database_Funcionario);
        imageView_LimparCampos = (ImageView) findViewById(R.id.imageView_Database_Funcionario_LimparCampos);
        editText_Nome = (EditText) findViewById(R.id.editText_Database_Funcionario_Nome);
        editText_Idade = (EditText) findViewById(R.id.editText_Database_Funcionario_Idade);
        button_Salvar = (Button) findViewById(R.id.button_Database_Funcionario_Salvar);
        imageView_Galeria = (ImageView) findViewById(R.id.imageView_Database_Funcionario_Imagem);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Database_Funcionario_Lista);

        imageView_LimparCampos.setOnClickListener(this);
        button_Salvar.setOnClickListener(this);
        imageView_Galeria.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        empresa = getIntent().getParcelableExtra("empresa");

        handler = new Handler();
        thread = new Thread(this);

        thread.start();



        ativarFirebaseOffline();
        iniciarRecyclerView();
    }
    // ------------------------------------------ INICIAR RECYCLER VIEW ------------------------------------------

    private void iniciarRecyclerView(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_listaFuncionario = new RecyclerView_ListaFuncionario(getBaseContext(), funcionarios, this );
        recyclerView.setAdapter(recyclerView_listaFuncionario);
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
            Toast.makeText(getBaseContext(),"Erro, verifique sua conexão com a internet", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void run() {

        try{

            Thread.sleep(10000);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    conexaoFirebaseBD();
                }
            });

        }catch(InterruptedException e){

        }

    }


    private void conexaoFirebaseBD(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(".info/connected");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean conexao = dataSnapshot.getValue(Boolean.class);

                if (conexao){
                    Toast.makeText(getBaseContext(), "Há conexão com o Banco de Dados", Toast.LENGTH_LONG).show();
                } else {
                    if(Util.statusInternet(getBaseContext())){
                        Toast.makeText(getBaseContext(), "Bloqueio ao Banco de Dados", Toast.LENGTH_LONG).show();
                    }else{

                        //Toast.makeText(getBaseContext(), "Sem conexão com a Internet", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    // ------------------------------------------ TRATAMENTO DE CLICKS ------------------------------------------

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_Database_Funcionario_LimparCampos:
                limparCampos();
                break;
            case R.id.button_Database_Funcionario_Salvar:
                buttonSalvar();
                //salvarDadosStorage();
                break;
            case R.id.imageView_Database_Funcionario_Imagem:

                obterImagem_Galeria();
                break;
        }
    }


    private void buttonSalvar(){
        String nome = editText_Nome.getText().toString();
        String idade_String = editText_Idade.getText().toString();

        if(Util.verificarCampos(getBaseContext(), nome, idade_String)){
            int idade = Integer.parseInt(idade_String);
            if (imagem_Selecionada){
                salvarDadosStorage(nome, idade);
            }else{
                DialogAlerta alerta = new DialogAlerta("Imagem - Erro", "É obrigatório escolher uma imagem para salvar os dados do Funcionário");
                alerta.show(getFragmentManager(),"1");
            }
        }
    }

    // ------------------------------------------ MENU ------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_database_lista_funcionario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.item_esconder_layout:
                linearLayout.setVisibility(View.GONE);
                return true;
            case R.id.item_mostrar_layout:
                linearLayout.setVisibility(View.VISIBLE);
                return true;
            case R.id.item_criar_pdf_funcionario:
                itemCriarPdf();


                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void itemCriarPdf(){
        if (funcionarios.size() > 0){

            new GerarPDF().execute();

        }else{
            DialogAlerta alerta = new DialogAlerta("PDF - Erro", "Não existem funcionários para gerar o relatório");
            alerta.show(getFragmentManager(),"1");
        }
    }

    @Override
    public void click_Funcionario(Funcionario funcionario) {

        funcionario.setId_empresa(empresa.getId());

        Intent intent = new Intent(getBaseContext(), DatabaseListaFuncionarioDadosActivity.class);
        intent.putExtra("funcionario", funcionario);
        startActivity(intent);
    }

    // ------------------------------------------ OBTER IMAGEM DA GALERIA ------------------------------------------

    private void obterImagem_Galeria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent,"Escolha uma Imagem"), 0);
    }

    // ----------------------------- RESPOSTAS DE COMUNICACAO --------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 0){
                if (data != null){

                    uri_imagem = data.getData();
                    Glide.with(getBaseContext()).asBitmap().load(uri_imagem).listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Toast.makeText(getBaseContext(), "Falha ao selecionar imagem", Toast.LENGTH_LONG).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                            imagem_Selecionada = true;

                            return false;
                        }
                    }).into(imageView_Galeria);
                } else {
                    Toast.makeText(getBaseContext(), "Erro ao carregar imagem", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    // ----------------------------- LIMPAR CAMPOS --------------------------

    private void limparCampos(){

        editText_Nome.setText("");
        editText_Idade.setText("");
        uri_imagem = null;
        imagem_Selecionada = false;
        imageView_Galeria.setImageResource(R.drawable.ic_galeria_24dp);

    }

    // ----------------------------- SALVAR DADOS --------------------------

    private void salvarDadosStorage(final String nome, final int idade){

        progress = new DialogProgress();
        progress.show(getFragmentManager(), "2");
        StorageReference reference = storage.getReference().child("BD").child("Empresas").child(empresa.getNome());
        final StorageReference nome_imagem = reference.child("CursoFirebase"+System.currentTimeMillis()+".jpg");

        Glide.with(getBaseContext()).asBitmap().load(uri_imagem).apply(new RequestOptions().override(1024, 768)).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                Toast.makeText(getBaseContext(),"Erro ao transformar imagem", Toast.LENGTH_LONG).show();
                progress.dismiss();
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                //Comprimir imagem
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                //Mantendo a qualidade
                resource.compress(Bitmap.CompressFormat.PNG, 70, bytes);

                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes.toByteArray());
                try{
                    bytes.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                UploadTask uploadTask = nome_imagem.putStream(inputStream);

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>(){

                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        return nome_imagem.getDownloadUrl();
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            progress.dismiss();
                            Uri uri = task.getResult();
                            String url_imagem = uri.toString();
                            salvarDadosDatabase(nome, idade, url_imagem);
                            Toast.makeText(getBaseContext(), "Sucesso ao realizar upload-storage", Toast.LENGTH_LONG).show();

                        } else {
                            progress.dismiss();
                            Toast.makeText(getBaseContext(), "Erro ao realizar upload-storage", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                return false;
            }
        }).submit();
    }

    private void salvarDadosDatabase(String nome, int idade, String urlimagem){
        progress = new DialogProgress();
        progress.show(getFragmentManager(), "2");

        Funcionario funcionario = new Funcionario(nome, idade, urlimagem);

        DatabaseReference databaseReference = database.getReference().child("BD").child("Empresas").child(empresa.getId()).child("Funcionarios");
        databaseReference.push().setValue(funcionario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    progress.dismiss();
                    Toast.makeText(getBaseContext(), "Sucesso ao realizar upload-database", Toast.LENGTH_LONG).show();

                } else {
                    progress.dismiss();
                    Toast.makeText(getBaseContext(), "Erro ao realizar upload-database", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    //---------------------------- GERAR PDF -------------------------------
    private void gerarPdf() throws DocumentException, FileNotFoundException {



        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


        String nome_Arquivo = diretorio.getPath()+"/"+"RelatorioFuncionarios"+System.currentTimeMillis()+".pdf";


        File pdf = new File(nome_Arquivo);


        OutputStream outputStream =  new FileOutputStream(pdf);


        Document document = new Document();

        PdfCreator event = new PdfCreator();

        PdfWriter writer = PdfWriter.getInstance(document,outputStream);

        writer.setBoxSize("box_a", new Rectangle(36,54,559,788));
        writer.setPageEvent(event);

        document.open();

        Font font = new Font(Font.FontFamily.HELVETICA,20,Font.BOLD);
        Font font_dados = new Font(Font.FontFamily.HELVETICA,20,Font.NORMAL);


        Paragraph paragraph = new Paragraph("Relatório de funcionários \n" + empresa.getNome() + "\n\n",font);

        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(30f);
        table.setSpacingAfter(30f);

        for (Funcionario funcionario: funcionarios){
            String dados = "Nome: "+funcionario.getNome()+"\n\nIdade: "+funcionario.getIdade();
            PdfPCell cell = new PdfPCell(new Paragraph(dados, font_dados));
            cell.setPadding(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

        }

        document.add(table);

        document.close();

        visualizarPDF(pdf);


    }


    private void visualizarPDF(File pdf) {
        PackageManager packageManager = getPackageManager();
        Intent itent = new Intent(Intent.ACTION_VIEW);
        itent.setType("application/pdf");
        List<ResolveInfo> lista = packageManager.queryIntentActivities(itent, PackageManager.MATCH_DEFAULT_ONLY);

        if (lista.size() > 0) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(getBaseContext(), "com.example.firebasecursods", pdf);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        } else {
            DialogAlerta dialogAlerta = new DialogAlerta("Erro ao abrir PDF", "Não foi detectado nenhum leitor de PDF no seu dispositivo.");
            dialogAlerta.show(getFragmentManager(), "3");
        }
    }

    // ----------------------------- OUVINTE --------------------------


    private void ouvinte(){

        //Query busca1 = database.getReference().child("BD").child("Empresas")
                //.child(empresa.getId()).child("Funcionarios").orderByChild("nome").equalTo("Alexandre Machado");
//        Query busca1 = database.getReference().child("BD").child("Empresas")
//                .child(empresa.getId()).child("Funcionarios").orderByChild("nome").startAt("A");

//        Query busca1 = database.getReference().child("BD").child("Empresas")
//                .child(empresa.getId()).child("Funcionarios").orderByChild("idade").startAt(18);
//        Query busca1 = database.getReference().child("BD").child("Empresas")
//                .child(empresa.getId()).child("Funcionarios").orderByChild("idade").startAt(18).endAt(22);

//        Query busca1 = database.getReference().child("BD").child("Empresas")
//                .child(empresa.getId()).child("Funcionarios").orderByChild("idade").endAt(22);
        reference_database = database.getReference().child("BD").child("Empresas").child(empresa.getId()).child("Funcionarios");
        if (childEventListener == null){
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //DataSnapshot.key pega o nome da pasta.
                    // Nesse caso o nome da pasta será o ID.
                    String key = dataSnapshot.getKey();
                    keys.add(key);
                    Funcionario funcionario = dataSnapshot.getValue(Funcionario.class);
                    funcionario.setId(key);
                    funcionarios.add(funcionario);
                    recyclerView_listaFuncionario.notifyDataSetChanged();


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    String key = dataSnapshot.getKey();

                    int index = keys.indexOf(key);

                    Funcionario funcionario = dataSnapshot.getValue(Funcionario.class);

                    funcionario.setId(key);

                    funcionarios.set(index, funcionario);

                    //Notificar que houve alteração
                    recyclerView_listaFuncionario.notifyDataSetChanged();


                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    String key = dataSnapshot.getKey();
                    int index = keys.indexOf(key);

                    funcionarios.remove(index);
                    keys.remove(index);
                    recyclerView_listaFuncionario.notifyItemRemoved(index);
                    recyclerView_listaFuncionario.notifyItemChanged(index, funcionarios.size());

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            reference_database.addChildEventListener(childEventListener);
            //busca1.addChildEventListener(childEventListener);
        }


    }



    //---------------------------------------- CICLOS DE VIDA DA ACTIVITY -----------------------------
    @Override
    protected void onStart() {
        super.onStart();
        ouvinte();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (childEventListener != null){

            reference_database.removeEventListener(childEventListener);
        }
    }


    //---------------------------------------- CRIANDO THREAD ASYNCTASK -----------------------------
    private class GerarPDF extends AsyncTask<Void, Void, Void>{

        private DialogProgress dialogProgress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogProgress = new DialogProgress();
            dialogProgress.show(getFragmentManager(), "2");
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                gerarPdf();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialogProgress.dismiss();
        }
    }
}
