package com.example.projetomvc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.projetomvc.R;
import com.example.projetomvc.controller.ProdutoController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;

import java.util.List;

public class ActivityVenda extends AppCompatActivity {

    private Spinner spnProdutos;
    private List<Produto> listaProduto;
    private ProdutoController produtoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

        // spinner
        this.produtoController = new ProdutoController(ConexaoSQLite.getInstancia(this));

        // busca a lista de produtos cadastrados no bsnco e jogar na variável de lista
        this.listaProduto = this.produtoController.getListaProdutosController();

        // passando o xml para activity atual
        this.spnProdutos = (Spinner) findViewById(R.id.spnProduto);

        // criar um adapter para listaProduto, usaremos o interno do androidStudio. O spinner item é um item embaixo do outro. A lista
        //de itens é a listaProduto
        ArrayAdapter<Produto> spnProdutoAdapter = new ArrayAdapter<Produto>
                (this, android.R.layout.simple_spinner_item, this.listaProduto);
        // o spnPorudtos vai usar o adapter spnProdutoAdapter. O adapter é quem vai manusear.s
       this.spnProdutos.setAdapter(spnProdutoAdapter);
        //end spinner
    }
}