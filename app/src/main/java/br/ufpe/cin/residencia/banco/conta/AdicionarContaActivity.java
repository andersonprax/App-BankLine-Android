package br.ufpe.cin.residencia.banco.conta;

import static br.ufpe.cin.residencia.banco.MainActivity.KEY_TOTAL_B;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.R;

//TODO Ver anotações com TODO no código
public class AdicionarContaActivity extends AppCompatActivity {

    ContaViewModel viewModel;
    //TODO 15ª Questão
    private int totalB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);

        btnAtualizar.setText("Inserir");
        btnRemover.setVisibility(View.GONE);

        //TODO 15ª Questão
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        totalB = preferences.getInt(KEY_TOTAL_B, 0);

        btnAtualizar.setOnClickListener(
                v -> {
                    String numeroConta = campoNumero.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();

                    //TODO: 4ª Questão - Incluir as validações das informações digitadas antes
                    // de criar um objeto Conta no banco de dados. Se todas as validações
                    // passarem, aí sim cria a Conta conforme linha abaixo:

                    //switch (cpfCliente.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4")){}

                    if (numeroConta.length() == 0 && saldoConta.length() == 0 && nomeCliente.length() == 0 && cpfCliente.length() == 0){
                        Toast.makeText(this, "Por favor, preencha TODOS os campos!", Toast.LENGTH_SHORT).show(); }
                    else if (saldoConta.length() == 0 && nomeCliente.length() == 0 && cpfCliente.length() == 0){
                        Toast.makeText(this, "Por favor, preencha os campos nome, CPF e saldo", Toast.LENGTH_SHORT).show(); }
                    else if (saldoConta.length() == 0 && cpfCliente.length() == 0){
                        Toast.makeText(this, "Por favor, preencha os campos CPF e saldo", Toast.LENGTH_SHORT).show(); }
                    else if (nomeCliente.length() == 0 && cpfCliente.length() == 0){
                        Toast.makeText(this, "Por favor, preencha os campos nome e CPF", Toast.LENGTH_SHORT).show(); }
                    else if (nomeCliente.length() == 0 && saldoConta.length() == 0){
                        Toast.makeText(this, "Por favor, preencha os campos nome e saldo", Toast.LENGTH_SHORT).show(); }
                    else if (numeroConta.length() == 0 || Double.valueOf(numeroConta) < 0 || numeroConta == ",") {
                        Toast.makeText(this, "Por favor digite o número da conta positivo e com pelo menos 1 digito", Toast.LENGTH_SHORT).show(); }
                    else if(saldoConta.length() == 0 || saldoConta == " ") {
                        Toast.makeText(this, "Por favor digite o saldo da conta", Toast.LENGTH_SHORT).show(); }
                    else if(nomeCliente.length() == 0 || nomeCliente == " ") {
                        Toast.makeText(this, "Por favor digite o nome do cliente", Toast.LENGTH_SHORT).show(); }
                    else if(cpfCliente.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4").length() < 14 ||
                            cpfCliente.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4").length() > 14 ||
                            cpfCliente.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4") == "-" ||
                            cpfCliente.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4") == " " ||
                            cpfCliente.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4") == "." ||
                            cpfCliente.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4") == ",") {
                        Toast.makeText(this, "Por favor digite um CPF válido e com 11 dígitos", Toast.LENGTH_SHORT).show(); }
                    else {
                        //TODO - Criação do objeto Conta
                        Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);
                        //TODO: Uso do método adicionar do ContaViewModel para salvar o objeto conta no Banco de Dados
                        viewModel.adicionar(c);
                        //TODO 15ª Questão
                        totalB += c.saldo;
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