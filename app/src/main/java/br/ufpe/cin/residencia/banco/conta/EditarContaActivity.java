package br.ufpe.cin.residencia.banco.conta;

import static br.ufpe.cin.residencia.banco.MainActivity.KEY_TOTAL_B;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.R;

//TODO Ver anotações com TODO no código
public class EditarContaActivity extends AppCompatActivity {

    ContaViewModel viewModel;
    public static final String KEY_NUMERO_CONTA = "numeroDaConta";
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
        campoNumero.setEnabled(false);

        //TODO - Esse código já existia

        Intent i = getIntent();
        String numeroConta = i.getStringExtra(KEY_NUMERO_CONTA);

        //TODO 8ª questão - Usar o número da conta passado via Intent para recuperar informações da conta,
        // ou seja, incluindo a funcionalidade de recuperar as informações da conta de acordo com o número
        // passado pelo Intent recebido aqui no EditarContaActivity, atualiza-se os campos do formulário.

        if (numeroConta!=null){
            viewModel.buscarPeloNumero(numeroConta);
        }
        else{
            Toast.makeText(this,"Inclua um número de conta válido para buscar informações da conta", Toast.LENGTH_SHORT).show();
            finish();
        }

        viewModel.contaAtual.observe(
                this,
                conta -> {
                    campoNumero.setText(conta.numero);
                    campoSaldo.setText(String.valueOf(conta.saldo));
                    campoNome.setText(conta.nomeCliente);
                    campoCPF.setText(conta.cpfCliente);
                }
        );

        //TODO 15ª Questão
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        totalB = preferences.getInt(KEY_TOTAL_B, 0);

        //TODO Esse código já existia.
        btnAtualizar.setText("Editar");
        btnAtualizar.setOnClickListener(
                v -> {
                    String numConta = campoNumero.getText().toString(); //TODO criei para não reclamar a falta dele no parâmetro do construtor.
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    //TODO: Incluir validações aqui, antes de criar um objeto Conta.
                    // Se todas as validações passarem, aí sim monta um objeto Conta.
                    //TODO: chamar o método que vai atualizar a conta no Banco de Dados
                    // 9ª Questão - inclusão a funcionalidade de validar as informações
                    // digitadas antes de atualizar a Conta no banco de dados.

                    if (saldoConta.length() == 0 && nomeCliente.length() == 0 && cpfCliente.length() == 0){
                        Toast.makeText(this, "Por favor, preencha os campos", Toast.LENGTH_SHORT).show(); }
                    else if (saldoConta.length() == 0 && cpfCliente.length() == 0){
                        Toast.makeText(this, "Por favor, preencha os campos CPF e saldo", Toast.LENGTH_SHORT).show(); }
                    else if (saldoConta.length() == 0 && nomeCliente.length() == 0){
                        Toast.makeText(this, "Por favor, preencha os campos nome e saldo", Toast.LENGTH_SHORT).show(); }
                    else if (nomeCliente.length() == 0 && cpfCliente.length() == 0){
                        Toast.makeText(this, "Por favor, preencha os campos nome e CPF", Toast.LENGTH_SHORT).show(); }
                    else if (nomeCliente.length() == 0 ) {
                        Toast.makeText(this, "Por favor digite o número da conta", Toast.LENGTH_SHORT).show(); }
                    else if (saldoConta.length() == 0) {
                        Toast.makeText(this, "Por favor digite o saldo da conta", Toast.LENGTH_SHORT).show(); }
                    else if (cpfCliente.length() < 11 || cpfCliente.length() > 11) {
                        Toast.makeText(this, "Por favor digite o CPF do cliente com 11 dígitos", Toast.LENGTH_SHORT).show(); }
                    else{
                        //TODO - Criação do objeto Conta
                        Conta c = new Conta(numConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);
                        //TODO Uso do método atualizar do ContaViewModel para armazenar o objeto conta atualizado no BD.
                        viewModel.atualizar(c);
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

        //TODO implementando remoção da conta
        btnRemover.setOnClickListener(v -> {
            // TODO - 10ª Questão - Uso do ContaViewModel para remover o objeto do BD.
            Conta c = viewModel.contaAtual.getValue();
            viewModel.remover(c);
            //TODO 15ª Questão
            totalB -= c.saldo;
            preferences
                    .edit()
                    .putInt(KEY_TOTAL_B, totalB)
                    .apply();
            finish();
        });

    }
}