package com.example.conversormoeda;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class RequisicaoHTTP extends AsyncTask<String, Void, Object> {

	@Override
	// primeiro parametro = sigla da moeda
	protected Object doInBackground(String... params) {
		String dados;
		if (params.length > 0) {
			String url = params[0];

			try {
				HttpClient cliente = new DefaultHttpClient();
				HttpGet requisicao = new HttpGet();

				requisicao.setHeader("Content-Type",
						"text/plain; charset=utf-8");
				requisicao.setURI(new URI(url));

				HttpResponse resposta = cliente.execute(requisicao);

				BufferedReader br = new BufferedReader(new InputStreamReader(
						resposta.getEntity().getContent()));

				StringBuffer sb = new StringBuffer();
				while ((dados = br.readLine()) != null) {
					sb.append(dados);
				}
				dados = sb.toString();

				return dados;
			} catch (Exception e) {
				Log.i("EXCEPTION", "ESDFGHJ");
			}
		}
		return null;
	}

}
