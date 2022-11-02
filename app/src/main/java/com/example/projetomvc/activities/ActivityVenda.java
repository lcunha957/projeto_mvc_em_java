package com.example.projetomvc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetomvc.R;
import com.example.projetomvc.controller.ProdutoController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.ItemDoCarrinho;
import com.example.projetomvc.model.Produto;

import java.util.List;

public class ActivityVenda extends AppCompatActivity {

    private Spinner spnProdutos;
    private List<Produto> listaProduto;
    private ProdutoController produtoController;
    private Button btnAddProduto;
    private Button btnFinalizarVenda;
    private EditText quantidadeItem; // pra pegar quantos itens for adicionados ao carrinho


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

        this.btnAddProduto = (Button) findViewById(R.id.btnAddProduto);
        this.btnFinalizarVenda = (Button) findViewById(R.id.btnFinalizarVenda);
        this.quantidadeItem = (EditText) findViewById(R.id.edtQuantidadeProduto);
    }

    // ao clicar no botão, pega os dados do produto e coloca no item de venda do carrinho
    public void eventAddProduto(View view) {
        ItemDoCarrinho itemDoCarrinho = new ItemDoCarrinho();
        // pegar o produto selecionado pelo spinner
        Produto produtoSelecionado = (Produto) this.spnProdutos.getSelectedItem();
        // passa os dados do produto da tabela produto
        Toast.makeText(this, produtoSelecionado.toString() + " - " + produtoSelecionado.getPreco() + " - " + produtoSelecionado.getQuantidadeEmEstoque(), Toast.LENGTH_LONG).show();
        // pra conseguir pegar a quantidade de cada item:
        int quantidadeProduto = 0;
        // senão for digitado nada no formulário:
        if(this.quantidadeItem.getText().toString().equals("")) {
            quantidadeProduto = 1;
        } else{
            quantidadeProduto = Integer.parseInt(this.quantidadeItem.getText().toString());
        }

        itemDoCarrinho.setNome(produtoSelecionado.getNome());
        itemDoCarrinho.setQuantidadeSelecionada(quantidadeProduto);
        itemDoCarrinho.setPrecoProduto(produtoSelecionado.getPreco());
        itemDoCarrinho.setprecoDoItemDaVenda(itemDoCarrinho.getPrecoProduto() * itemDoCarrinho.getQuantidadeSelecionada());
       

    }
}