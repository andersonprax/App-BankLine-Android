package br.ufpe.cin.residencia.banco.conta;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//TODO - Ver anotações no código

@Dao
public interface ContaDAO {

    //TODO - esse método adicionar (Insert) já existia.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void adicionar(Conta c);

    // TODO - 5ª Questão - Inclusão métodos para atualizar e remover contas no banco de dados.

    @Update
    void atualizar(Conta c);

    @Delete
    void remover(Conta c);

    //TODO esses código já existia

    @Query("SELECT * FROM contas ORDER BY numero ASC")
    LiveData<List<Conta>> contas();

    //TODO 5ª Questão - Inclusão de métodos para buscar pelo número da conta, nome do cliente e CPF do cliente

    @Query("SELECT * FROM contas WHERE numero = :numero")
    Conta buscarPeloNumero(String numero);

    @Query("SELECT * FROM contas WHERE nomeCliente LIKE :nome")
    List<Conta> buscarPeloNome(String nome);

    @Query("SELECT * FROM contas WHERE cpfCliente = :cpf")
    List<Conta> buscarPeloCPF(String cpf);

    //TODO 11ª Questão - A transferência é tirar valor de uma conta e adicionar em outra.

    @Update
    void trasnferir(Conta c);

}
