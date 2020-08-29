package com.example.firebaseautenticacao;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class Util {
    public static boolean verificarInternet(Context context){
        //Verifica a conexão utilizando ConnectivityManager e NetworkInfo
        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo informacao = conexao.getActiveNetworkInfo();

        if ( informacao != null && informacao.isConnected()){
            return true;
        }else{
            return false;
        }
    }

    public static void opcoesErro(Context context, String resposta){
        if (resposta.contains("least 6 characters")){
            Toast.makeText(context, "Digite uma senha maior que 6 caracteres", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("address is badly")) {
            Toast.makeText(context, "Digite um e-mail válido", Toast.LENGTH_LONG).show();
        }else if(resposta.contains("interrupted connection")){
            Toast.makeText(context, "Sem conexão com o Firebase", Toast.LENGTH_LONG).show();
        }else if(resposta.contains("password is invalid")){
            Toast.makeText(context, "Senha inválida", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("There is no user")){
            Toast.makeText(context, "Este e-mail não está cadastrado", Toast.LENGTH_LONG).show();
        }
        //--------------- ERROS CADASTRAR USUARIO -----------------

        else if(resposta.contains("address is already")){
            Toast.makeText(context, "E-mail já cadastrado", Toast.LENGTH_LONG).show();
        }else if(resposta.contains("INVALID_EMAIL")){
            Toast.makeText(context, "E-mail inválido", Toast.LENGTH_LONG).show();
        }else if(resposta.contains("EMAIL NOT_FOUND")){
            Toast.makeText(context, "E-mail não cadastrado", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        }
    }
/*Para verificar a conexão de internet em dispositivos novos
    public static boolean statusInternet_MoWi(Context context){
        boolean status = false;
        ConnectivityManager conexao = (ConectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conexao != null){
            //PARA DISPOSITIVOS NOVOS
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities recursosRede = conexao.getNetworkCapabilities(conexao.getActiveNetwork));
                if (recursosRede != null){
                    if (recursosRede.hasTransport(NetworkCapabilites.TRANSPORT_CELLULAR)){
                        //VERIFICAMOS SE DISPOSITIVO TEM 3G
                        return true;
                    } else if (recursosRede.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                        //VERIFICAMOS SE DISPOSITIVO TEM WIFI
                        return true;
                    }

                    //NÃO POSSUI CONEXÃO VÁLIDA
                    return false;
                }
            } else {
                // Para dispositivos antigos
                NetworkInfo informacao = conexao.getActiveNetworkInfo();
                if ( informacao != null && informacao.isConnected()){
                   return true;
                }else{
                    return false;
                }
                return status;
            }
        }
        return false;
    }*/

}
