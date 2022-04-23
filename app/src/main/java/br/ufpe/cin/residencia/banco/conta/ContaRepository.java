package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

//TODO - Ver anotações no código

public class ContaRepository {

    private ContaDAO dao;
    private LiveData<List<Conta>> contas;


    public ContaRepository(ContaDAO dao) {
        this.dao = dao;
        this.contas = dao.contas();
    }

    public LiveData<List<Conta>> getContas() {
        return contas;
    }

    //TODO 11ª Questão

    @WorkerThread
    void transferir(Conta c, String numOrigem, String numDestino, double valor) {
        dao.trasnferir(c);
    }

    //TODO - Método adicionar já existia como inserir... Eu MODIFIQUEI!

    @WorkerThread
    void adicionar(Conta c) {
        dao.adicionar(c);
    }

    // TODO 6ª questão - Inclusão de métodos para atualizar e remover contas no banco de dados, além
    //  de métodos para buscar pelo número da conta, pelo nome do cliente e pelo CPF do cliente.
    //  Estes métodos devem usar os métodos criados no passo anterior

    @WorkerThread
    public void atualizar(Conta c) {
        dao.atualizar(c);
    }

    @WorkerThread
    void remover(Conta c) {
        dao.remover(c);
    }

    @WorkerThread
    public Conta buscarPeloNumero(String numeroConta) {
        return dao.buscarPeloNumero(numeroConta);
    }

    @WorkerThread
    public List<Conta> buscarPeloNome(String nomeCliente) {
        return dao.buscarPeloNome(nomeCliente);
    }

    @WorkerThread
    public List<Conta> buscarPeloCPF(String cpfCliente) {
        return dao.buscarPeloCPF(cpfCliente);
    }

}
