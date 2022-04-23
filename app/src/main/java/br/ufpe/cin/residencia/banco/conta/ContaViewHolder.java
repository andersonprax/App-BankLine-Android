package br.ufpe.cin.residencia.banco.conta;

import static br.ufpe.cin.residencia.banco.conta.EditarContaActivity.KEY_NUMERO_CONTA;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class ContaViewHolder  extends RecyclerView.ViewHolder {
    TextView nomeCliente = null;
    TextView infoConta = null;
    ImageView icone = null;

    public ContaViewHolder(@NonNull View linha) {
        super(linha);
        this.nomeCliente = linha.findViewById(R.id.nomeCliente);
        this.infoConta = linha.findViewById(R.id.infoConta);
        this.icone = linha.findViewById(R.id.icone);
    }

    void bindTo(Conta c) {
        this.nomeCliente.setText(c.nomeCliente);
        this.infoConta.setText(c.numero + " | " + "Saldo atual: R$" + NumberFormat.getCurrencyInstance().format(c.saldo));
        //TODO Falta atualizar a imagem de acordo com o valor do saldo atual
        //TODO 2ª Questão - a imagem que é mostrada na lista de contas não é alterada
        // caso o saldo esteja negativo. Inclua o código correspondente na função bindTo;
        if (c.saldo >= 0){
            this.icone.setImageResource(R.drawable.ok);
        }else{
            this.icone.setImageResource(R.drawable.delete);
        }
        this.addListener(c.numero);
    }

    public void addListener(String numeroConta) {
        this.itemView.setOnClickListener(
                v -> {
                    Context c = this.itemView.getContext();
                    Intent i = new Intent(c, EditarContaActivity.class);
                    //TODO Está especificando a Activity mas não está passando o número da conta pelo Intent
                    //Código que mostra o número da conta no intent
                    i.putExtra("numeroDaConta", numeroConta);
                    c.startActivity(i);
                }
        );
    }
}
