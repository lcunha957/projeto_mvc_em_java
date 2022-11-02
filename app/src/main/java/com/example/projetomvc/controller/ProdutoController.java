package com.example.projetomvc.controller;

// interface de controle entre a view ou model e a DAO;

import com.example.projetomvc.DAO.ProdutoDAO;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;

public class ProdutoController {

    // cria o objeto ProdutoDAO pra conseguir chamar depois os métodos da DAO e
    // manipular indiretamente o banco de dados.

    private final  ProdutoDAO produtoDAO;

    // construtor
    public ProdutoController(ConexaoSQLite pConexaoSQLite){
     produtoDAO = new ProdutoDAO(pConexaoSQLite);
    }

    // os tipos de retorno e os parâmetros devem ser iguais
    public long salvarProdutoController(Produto pProduto){
      return this.produtoDAO.salvarProdutoDAO(pProduto);
    }
}