package com.example.projetomvc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.projetomvc.R;
import com.example.projetomvc.adapters.AdapterListaProdutos;
import com.example.projetomvc.controller.ProdutoController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ListarProdutosActivity extends AppCompatActivity {

    // criando o listView pra listar nas telas
    private ListView lsvProdutos;
    private List<Produto> produtoList;
    // o adapter que liga a listView com a lista!
    private AdapterListaProdutos adapterListaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produtos);


        // TODO: buscar os produtos do banco!
          /*
        // Criando produtos na unha .....
        Produto p = new Produto();
        p.setId(654654);
        p.setNome("Guaraná");
        p.setPreco(2);
        p.setQuantidadeEmEstoque(100);

        // enquanto não tiver os produtos do banco ele cria uma lista vazia!
        this.produtoList = new ArrayList<>();

        // adicionando o guaraná na lista várias vezes pra testar a lista:
        this.produtoList.add(p);
        this.produtoList.add(p);
        this.produtoList.add(p);
        this.produtoList.add(p);
        this.produtoList.add(p);
        this.produtoList.add(p);
        this.produtoList.add(p);
        this.produtoList.add(p); */

        // Processo automatizado para buscar os produtos do banco de dados;

        // Cria a instância de controller:
        ProdutoController produtoController =new ProdutoController(ConexaoSQLite.getInstancia(ListarProdutosActivity.this));
        // retorna a lista de produtos na view.
        produtoList = produtoController.getListaProdutosController();

        // Como é observado adiante: O produtoList passa os dados ao adapter, e o adapter chama o listView
        // o lsvProdutos que veio junto com o id, é da activity_listar_produtos.xml
        // o lsvProdutos está dentro da ListView, e a ListView está dentro do activity_listar_produtos.xml
        this.lsvProdutos = (ListView) findViewById(R.id.lsvProdutos);

        // criando o adapter do listview, recebendo como parâmetros: a lista atual da activity (contexto) e qual a lista
        // que a gente quer que ele caregue, no caso ele deve carregar o produtoList.
        this.adapterListaProdutos = new AdapterListaProdutos(ListarProdutosActivity.this, produtoList);

        this.lsvProdutos.setAdapter(this.adapterListaProdutos);


    }
}