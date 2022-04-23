package br.ufpe.cin.residencia.banco;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaDAO;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

//TODO Ver anotações no código
public class BancoViewModel extends AndroidViewModel {

    private ContaRepository repository;

    private MutableLiveData<List<Conta>> _contaAtual = new MutableLiveData<>();
    public LiveData<List<Conta>> contaAtual = _contaAtual;

    private MutableLiveData<Conta> _contaAg = new MutableLiveData<>();
    public LiveData<Conta> contaAg = _contaAg;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
    }

    // TODO 11ª Questão - inclusão de métodos para realizar as operações de transferir.
    //  Estes métodos devem usar os métodos de ContaRepository criados em passos anteriores.
    //  O método transferir() tem os parâmetros iguais ao transferir() da classe TransferirActivity.

    public void transferir(String numOrigem, String numDestino, double valor) {
        new Thread(() -> {
            Conta c = repository.buscarPeloNumero(numOrigem);
            Conta x = repository.buscarPeloNumero(numDestino);
            c.debitar(valor);
            x.creditar(valor);
            //TODO Esses métodos salvam no BD os objetos Conta modificados
            repository.atualizar(c);
            repository.atualizar(x);
        }).start();
    }

    // TODO 11ª Questão - inclusão de métodos para creditar. Estes métodos
    //  devem usar os métodos de ContaRepository criados em passos anteriores.
    void creditar(String numeroConta, double valor) {
        new Thread(() -> {
            Conta c = repository.buscarPeloNumero(numeroConta);
            c.creditar(valor);
            //TODO Esses métodos salvam no BD o objeto Conta modificado
            repository.atualizar(c);
        }).start();
    }

    // TODO 11ª Questão - inclusão de métodos para debitar. Estes métodos
    //  devem usar os métodos de ContaRepository criados em passos anteriores.
    void debitar(String numeroConta, double valor) {
        new Thread(() -> {
            Conta c = repository.buscarPeloNumero(numeroConta);
            c.debitar(valor);
            //TODO Esses métodos salvam no BD o objeto Conta modificado.
            repository.atualizar(c);
        }).start();
    }

    // TODO 11ª Questão - inclusão de métodos para buscar pelo nome do cliente. Esses
    //  métodos devem usar os métodos de ContaRepository criados em passos anteriores.
    void buscarPeloNome(String nomeCliente) {
        new Thread(() -> {
            List<Conta> nomeC = repository.buscarPeloNome(nomeCliente);
            _contaAtual.postValue(nomeC);
        }).start();
    }

    // TODO 11ª Questão - inclusão de métodos para buscar pelo CPF do cliente. Estes
    //  métodos devem usar os métodos de ContaRepository criados em passos anteriores.
    void buscarPeloCPF(String cpfCliente) {
        new Thread(() -> {
            List<Conta> numCPF= repository.buscarPeloCPF(cpfCliente);
            _contaAtual.postValue(numCPF);
        }).start();
    }

    // TODO 11ª Questão - inclusão de métodos para buscar pelo número da conta. Estes
    //  métodos devem usar os métodos de ContaRepository criados em passos anteriores.
    void buscarPeloNumero(String numeroConta) {
        new Thread(() -> {
                Conta numC = repository.buscarPeloNumero(numeroConta);
            _contaAg.postValue(numC);
        }).start();
    }
}
