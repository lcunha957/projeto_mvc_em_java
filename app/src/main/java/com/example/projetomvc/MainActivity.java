package com.example.projetomvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetomvc.activities.ActivityVenda;
import com.example.projetomvc.activities.Activity_Produto;
import com.example.projetomvc.activities.ListarProdutosActivity;
import com.example.projetomvc.activities.VendasConsolidadasActivity;
import com.example.projetomvc.controller.ProdutoController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;

public class MainActivity extends AppCompatActivity {

    private Button btnCadastroProdutos;
    private Button btnListarProdutos;
    private Button btnVender;
    private Button btnMinhasVendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // chama o getInstancia pra verificar se o banco de dados foi ligado, precisa do contexto this;
        ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstancia(this);

        /* TESTE MANUAL
        //produto na mão aqui e mando pras camadas debaixo, seriam os produtos que viriam do formulário:
        Produto produto1 = new Produto();
        produto1.setId(123455); // como se fosse um código de barras
        produto1.setNome("Computador");
        produto1.setQuantidadeEmEstoque(100);
        produto1.setPreco(1500);

        // passa o produto pra controller, que depois vai pra DAO:
        // aqui, cria o objeto produtoController e passa a conexao criada após o setContentView
        ProdutoController produtoController = new ProdutoController(conexaoSQLite);
        // a controller vai salvar o produto repassando
        // para DAO se comunicar com o banco de dados e salvar efetivamente no banco de dados.
      long resultado = produtoController.salvarProdutoController(produto1);

      // dando o print vai ver que está tudo certo, retorna o id do resultado inserido.
      System.out.println("resultado = " + resultado); */


        this.btnCadastroProdutos = (Button) findViewById(R.id.btnCadastroProdutos);
        this.btnListarProdutos = (Button) findViewById(R.id.btnListarProdutos);
        this.btnVender = (Button) findViewById(R.id.btnVender);
        this.btnMinhasVendas = (Button) findViewById(R.id.btnMinhasVendas);

        this.btnCadastroProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Produto.class);
                startActivity(intent);
            }
        });



        this.btnListarProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent tem como parâmetros na instanciação: atividade atual.this(famoso contexto), atividade para onde quero ir.class
                Intent intentLista = new Intent(MainActivity.this, ListarProdutosActivity.class);
                startActivity(intentLista);
            }
        });


        this.btnVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVendas = new Intent(MainActivity.this, ActivityVenda.class);
                startActivity(intentVendas);
            }
        });


        this.btnMinhasVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRelatorioMinhasVendas = new Intent(MainActivity.this, VendasConsolidadasActivity.class);
                startActivity(intentRelatorioMinhasVendas);
            }
        });
    }
}