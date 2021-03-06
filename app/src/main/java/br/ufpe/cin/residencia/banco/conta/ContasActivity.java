package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class ContasActivity extends AppCompatActivity {
    ContaAdapter adapter;
    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);
        //TODO Esse RecyclerView que usa um Adapter para mostrar uma lista de contas.
        RecyclerView recyclerView = findViewById(R.id.rvContas);
        adapter = new ContaAdapter(getLayoutInflater());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        LiveData<List<Conta>> contas;

        Button adicionarConta = findViewById(R.id.btn_Adiciona);
        adicionarConta.setOnClickListener(
                v -> startActivity(new Intent(this, AdicionarContaActivity.class))
        );

        //TODO 1ª Questão - Recuperar Dados do BD usando o atributo contas
        // (com tipo LiveData<List<Conta>>) de ContaViewModel para fazer isto.
        viewModel.contas.observe(
               this,
                newListConta -> {
                   List<Conta> novaLista = new ArrayList<>(newListConta);
                   adapter.submitList(novaLista);
               }
        );
    }

    //TODO Neste arquivo ainda falta implementar o código que atualiza a lista de contas automaticamente na tela
}