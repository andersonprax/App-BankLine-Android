package br.ufpe.cin.residencia.banco.conta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.BancoDB;

//TODO - Ver métodos anotados com TODO

public class ContaViewModel extends AndroidViewModel {

    private ContaRepository repository;
    public LiveData<List<Conta>> contas;
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;

    public ContaViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.contas = repository.getContas();
    }

    //TODO Esse método já existia como inserir... eu MODIFIQUEI!

    void adicionar(Conta c) {
        new Thread(() -> repository.adicionar(c)).start();
    }

    //TODO 7ª Questão - inclusão dos métodos atualizar e remover contas no
    // banco de dados, além de um método para buscar pelo número da conta.
    // Estes métodos devem usar os métodos criados no passo anterior.

    void atualizar(Conta c) {
        new Thread(() -> repository.atualizar(c)).start();
    }

    void remover(Conta c) {
        new Thread(() -> repository.remover(c)).start();
    }

    void buscarPeloNumero(String numeroConta) {
        new Thread(()->{
            Conta c = repository.buscarPeloNumero(numeroConta);
        _contaAtual.postValue(c);
        }).start();
    }
}
