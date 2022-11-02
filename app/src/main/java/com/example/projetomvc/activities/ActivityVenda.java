package com.example.projetomvc.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetomvc.R;
import com.example.projetomvc.adapters.AdapterItensDoCarrinho;
import com.example.projetomvc.controller.ProdutoController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.ItemDoCarrinho;
import com.example.projetomvc.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ActivityVenda extends AppCompatActivity {

    private Spinner spnProdutos;
    private List<Produto> listaProduto;
    private ProdutoController produtoController;
    private EditText quantidadeItem; // pra pegar quantos itens for adicionados ao carrinho


    // carrinho de compras
    private ListView lsvCarrinhoCompras;
    private List<ItemDoCarrinho> listaItensDoCarrinho;
    private AdapterItensDoCarrinho adpItemDoCarrinho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

        // spinner
        this.produtoController = new ProdutoController(ConexaoSQLite.getInstancia(this));

        // busca a lista de produtos cadastrados no bsnco e jogar na variável de lista
        this.listaProduto = this.produtoController.getListaProdutosController();

        // passando o xml para activity atual
        this.spnProdutos = (Spinner) this.findViewById(R.id.spnProduto);

        // criar um adapter para listaProduto, usaremos o interno do androidStudio. O spinner item é um item embaixo do outro. A lista
        //de itens é a listaProduto
        ArrayAdapter<Produto> spnProdutoAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, this.listaProduto);
        // o spnPorudtos vai usar o adapter spnProdutoAdapter. O adapter é quem vai manusear.s
        this.spnProdutos.setAdapter(spnProdutoAdapter);
        //end spinner

        this.quantidadeItem = (EditText) this.findViewById(R.id.edtQuantidadeProduto);

        // variaveis e objetos do carrinho de compras
        this.lsvCarrinhoCompras = (ListView) this.findViewById(R.id.lsvProdutos);
        this.listaItensDoCarrinho = new ArrayList<>();
        this.adpItemDoCarrinho = new AdapterItensDoCarrinho(ActivityVenda.this, this.listaItensDoCarrinho);
        this.lsvCarrinhoCompras.setAdapter(this.adpItemDoCarrinho);

        // para remover o item do carrinho:
        this.lsvCarrinhoCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int posicao, long id) {
                final ItemDoCarrinho itemDoCarrinho = (ItemDoCarrinho) adpItemDoCarrinho.getItem(posicao);
                AlertDialog.Builder janelaDeEscolha = new AlertDialog.Builder(ActivityVenda.this);
                janelaDeEscolha.setTitle("Escolha");
                janelaDeEscolha.setMessage(" Deseja remover o item " + itemDoCarrinho.getNome() + "?");

                janelaDeEscolha.setNegativeButton("Não", null);
                janelaDeEscolha.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean excluiu = false;

                        adpItemDoCarrinho.removerItemDoCarrinho(posicao);

                        if (!excluiu) {
                            Toast.makeText(ActivityVenda.this, "Item :  " + itemDoCarrinho.getNome() + "  foi excluido do carrinho com sucesso!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                janelaDeEscolha.create().show();

            }
        });


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
        if (this.quantidadeItem.getText().toString().equals("")) {
            quantidadeProduto = 1;

        } else {
            quantidadeProduto = Integer.parseInt(this.quantidadeItem.getText().toString());

        }

        itemDoCarrinho.setNome(produtoSelecionado.getNome());
        itemDoCarrinho.setQuantidadeSelecionada(quantidadeProduto);
        itemDoCarrinho.setPrecoProduto(produtoSelecionado.getPreco());
        itemDoCarrinho.setprecoDoItemDaVenda(itemDoCarrinho.getPrecoProduto() * itemDoCarrinho.getQuantidadeSelecionada());

        // para adicionar o produto no clique do botão...
        this.adpItemDoCarrinho.addItemDoCarrinho(itemDoCarrinho);


    }
}