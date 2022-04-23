package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaAdapter;

//TODO Ver anotações no código
public class PesquisarActivity extends AppCompatActivity {

    BancoViewModel viewModel;
    ContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);
        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);
        RecyclerView rvResultado = findViewById(R.id.rvResultado);
        adapter = new ContaAdapter(getLayoutInflater());
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        RadioButton Nome =(RadioButton) findViewById(R.id.peloNomeCliente);
        RadioButton CPF = (RadioButton) findViewById(R.id.peloCPFcliente);
        RadioButton NumeroDaConta = (RadioButton) findViewById(R.id.peloNumeroConta);

        btnPesquisar.setOnClickListener(
                v -> {
                    String oQueFoiDigitado = aPesquisar.getText().toString();

                    //TODO  13ª Questão - implementar o código que faz busca no banco de dados
                    // de acordo com o tipo de busca escolhido pelo usuário (ver RadioGroup tipoPesquisa)
                    // implementar a busca de acordo com o tipo
                    // de busca escolhido pelo usuário

                    if(oQueFoiDigitado.equals("")){
                        Toast.makeText(this,"Digite as informações",Toast.LENGTH_LONG).show();
                    }
                    if (Nome.isChecked()){
                        viewModel.buscarPeloNome(oQueFoiDigitado);
                    }
                    if (CPF.isChecked()){
                        viewModel.buscarPeloCPF(oQueFoiDigitado);
                    }
                    if (NumeroDaConta.isChecked()){
                        viewModel.buscarPeloNumero(oQueFoiDigitado);
                    }
                }
        );

        //TODO 14ª Questão ao realizar uma busca, atualizar o RecyclerView com os resultados da busca na medida que encontrar algo;
        // atualizar o RecyclerView com resultados da busca na medida que encontrar
        viewModel.contaAtual.observe(
                this,
                atualizadaConta ->{
                    if (atualizadaConta != null){
                        List<Conta>atualizadaListaConta = new ArrayList<>(atualizadaConta);
                        adapter.submitList(atualizadaListaConta);
                    }
                }
        );
        viewModel.contaAg.observe(
                this,
                agoraConta ->{
                    if (agoraConta !=null){
                        List<Conta>agoraAtualConta = new ArrayList<>();
                        agoraAtualConta.add(agoraConta);
                        adapter.submitList(agoraAtualConta);
                    }
                }
        );
    }
}