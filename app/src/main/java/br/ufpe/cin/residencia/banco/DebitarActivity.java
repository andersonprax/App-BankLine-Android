package br.ufpe.cin.residencia.banco;

import static br.ufpe.cin.residencia.banco.MainActivity.KEY_TOTAL_B;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.conta.Conta;

//TODO Ver anotações no código
public class DebitarActivity extends AppCompatActivity {

    BancoViewModel viewModel;
    //TODO 15ª Questão
    private int totalB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacoes);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        TextView tipoOperacao = findViewById(R.id.tipoOperacao);
        EditText numeroContaOrigem = findViewById(R.id.numeroContaOrigem);
        TextView labelContaDestino = findViewById(R.id.labelContaDestino);
        EditText numeroContaDestino = findViewById(R.id.numeroContaDestino);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);

        labelContaDestino.setVisibility(View.GONE);
        numeroContaDestino.setVisibility(View.GONE);

        valorOperacao.setHint(valorOperacao.getHint() + " debitado");
        tipoOperacao.setText("DEBITAR");
        btnOperacao.setText("Debitar");

        //TODO 15ª Questão
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        totalB = preferences.getInt(KEY_TOTAL_B, 0);

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    String valOperacao = valorOperacao.getText().toString();

                    //TODO lembrar de implementar validação do número da conta e do valor da
                    // operação, antes de efetuar a operação de débito. O método abaixo está
                    // sendo chamado, mas precisa ser implementado para funcionar.

                    if (numOrigem.length() == 0 || valOperacao.length() == 0) {
                        Toast.makeText(this, "Por favor, preencha TODOS os campos!", Toast.LENGTH_SHORT).show();
                    } else if (valOperacao.length() == 0 || Double.valueOf(valOperacao) <= 0) {
                        Toast.makeText(this, "Digite o valor da operação positivo e com pelo menos 1 digito", Toast.LENGTH_SHORT).show();
                    } else if (numOrigem.length() == 0 || Double.valueOf(numOrigem) <= 0) {
                        Toast.makeText(this, "Digite o número da conta positivo e com pelo menos 1 digito", Toast.LENGTH_SHORT).show();
                    } else {
                        double valor = Double.valueOf(valOperacao);
                        viewModel.debitar(numOrigem, valor);
                        //TODO 15ª Questão
                        totalB -= valor;
                        preferences
                                .edit()
                                .putInt(KEY_TOTAL_B, totalB)
                                .apply();
                        finish();
                    }
                }
        );
    }
}