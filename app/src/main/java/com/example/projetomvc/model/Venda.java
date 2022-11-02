package com.example.projetomvc.model;

import java.util.Date;
import java.util.List;

public class Venda {

    private long id;
    private Date dataDaVenda;
    private String nomeDoCliente; // tarefa pra casa, colocar o nome do cliente e salvar no banco
    private List<ItemDoCarrinho> itensDaVenda;

    public Venda() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(Date dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    public String getNomeDoCliente() {
        return nomeDoCliente;
    }

    public void setNomeDoCliente(String nomeDoCliente) {
        this.nomeDoCliente = nomeDoCliente;
    }

    public List<ItemDoCarrinho> getItensDaVenda() {
        return itensDaVenda;
    }

    public void setItensDaVenda(List<ItemDoCarrinho> itensDaVenda) {
        this.itensDaVenda = itensDaVenda;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", dataDaVenda=" + dataDaVenda +
                ", nomeDoCliente='" + nomeDoCliente + '\'' +
                '}';
    }
}
