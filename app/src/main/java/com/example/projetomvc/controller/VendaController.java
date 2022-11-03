package com.example.projetomvc.controller;

// não vai precisar deletar a venda

import com.example.projetomvc.DAO.VendaDAO;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Venda;

public class VendaController {

      private final VendaDAO vendaDAO;


    public VendaController(ConexaoSQLite pConexaoSQLite) {
        vendaDAO =  new VendaDAO(pConexaoSQLite);
    }

    public long salvarVendaController(Venda pVenda) {
        return vendaDAO.salvarVendaDAO(pVenda);
    }

    public boolean salvarItensVendaController( Venda pVenda){
        return vendaDAO.salvarItensDaVenda(pVenda);
    }
}
