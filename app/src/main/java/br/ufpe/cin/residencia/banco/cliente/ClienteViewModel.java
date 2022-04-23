package br.ufpe.cin.residencia.banco.cliente;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.BancoDB;
import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

public class ClienteViewModel extends AndroidViewModel {

    private ContaRepository repository;
    public LiveData<List<Cliente>> clientes;
    private MutableLiveData<Cliente> _clienteAtual = new MutableLiveData<>();
    public LiveData<Cliente> clienteAtual = _clienteAtual;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        //this.clientes = repository.getClientes();
    }

    void buscarPeloNome(String nomeCliente) {

    }

    void buscarPeloCPF(String cpfCliente) {

    }
}
