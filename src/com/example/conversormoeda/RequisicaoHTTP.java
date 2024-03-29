package com.example.conversormoeda;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class RequisicaoHTTP extends AsyncTask<String, Void, Object> {
	
	private Context contexto;
	// tempo limite de 10 segundos para conexao
	static final int TEMPO_LIMITE_CONEXAO = 5000;
	
	
	public RequisicaoHTTP(Context c){
		this.contexto = c;
	}
	

	@Override
	// primeiro parametro = sigla da moeda
	protected Object doInBackground(String... params) {
		String dados;
		if (params.length > 0) {
			String url = params[0];

			try {
				// Definindo tempo limite para tentativa de conexao
				final HttpParams httpParam = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParam,
						TEMPO_LIMITE_CONEXAO);
				HttpConnectionParams.setSoTimeout(httpParam,
						TEMPO_LIMITE_CONEXAO);

				HttpClient cliente = new DefaultHttpClient(httpParam);
				HttpGet requisicao = new HttpGet();

				requisicao.setHeader("Content-Type",
						"text/plain; charset=utf-8");
				requisicao.setURI(new URI(url));
				
				Log.i("EXCEPTION1", "msg");
				HttpResponse resposta = cliente.execute(requisicao);
				Log.i("EXCEPTION2", "msg");

				BufferedReader br = new BufferedReader(new InputStreamReader(
						resposta.getEntity().getContent()));

				StringBuffer sb = new StringBuffer();
				while ((dados = br.readLine()) != null) {
					sb.append(dados);
				}
				dados = sb.toString();

				return dados;
			} catch (Exception e) {
				Log.i("EXCEPTION3", "msg");
				AlertDialog alerta;
				AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

				builder.setTitle("Erro de conex�o");
				builder.setMessage("Ocorreu um erro durante a conex�o com o servidor. Por favor, tente novamente mais tarde");
				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Apenas volta para o programa.
							}
						});

				alerta = builder.create();
				alerta.show();
			}
		}
		return null;
	}

}
