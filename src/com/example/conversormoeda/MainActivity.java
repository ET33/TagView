package com.example.conversormoeda;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemSelectedListener {
	Spinner spin_moedas;
	String sigla;
	static final String MOEDA_ALVO = "BRL";
	static final String METHOD = "POST";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Adicionando "escutador" para o spinner
		spin_moedas = (Spinner) findViewById(R.id.sp_moeda);
		spin_moedas.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.moedas, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_moedas.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// Item selecionado
		String item = parent.getItemAtPosition(position).toString();

		// Obter somente a sigla da moeda de troca
		this.sigla = item.substring(0, 3);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	public void sendRequest(View view) {
		EditText edValor = (EditText) findViewById(R.id.edit_valor);
		String valor = edValor.getText().toString();
		if (valor.isEmpty()) {
			Toast.makeText(getApplicationContext(), "Valor n�o informado", 2)
					.show();
			return;
		}

		// URL da API
		String url = "https://rate-exchange.herokuapp.com/fetchRate?from="
				+ sigla + "&to=" + MOEDA_ALVO;
		try {

			// Obtem-se o JSon a partir da classe auxiliar que faz a requisicao
			// � APIc
			String json = (String) new RequisicaoHTTP(getApplicationContext())
					.execute(url).get();


			Gson gson = new Gson();

			// Converte o json obtido para classe Moeda
			Moeda moeda = gson.fromJson(json, Moeda.class);

			// Converte o valor para reais
			BigDecimal resultado = new BigDecimal(valor);
			BigDecimal rate = new BigDecimal(String.valueOf(moeda.getRate()));

			resultado = resultado.multiply(rate);

			// Formata saida para 2 decimais
			resultado = resultado.setScale(2, RoundingMode.CEILING);

			// Informa a conversao para o usuario
			TextView valorFinal = (TextView) findViewById(R.id.txt_resultado);
			valorFinal.setText(valor + " " + sigla + " = " + resultado + " "
					+ MOEDA_ALVO);

		} catch (Exception e) {

		}

	}

}
