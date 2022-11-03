package com.example.projetomvc.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetomvc.R;
import com.example.projetomvc.adapters.AdapterItensDoCarrinho;
import com.example.projetomvc.controller.ProdutoController;
import com.example.projetomvc.controller.VendaController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.ItemDoCarrinho;
import com.example.projetomvc.model.Produto;
import com.example.projetomvc.model.Venda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityVenda extends AppCompatActivity {

    private Spinner spnProdutos;
    private List<Produto> listaProduto;
    private ProdutoController produtoController;
    private EditText quantidadeItem; // pra pegar quantos itens for adicionados ao carrinho
    private TextView tvTotalVenda;

    // carrinho de compras
    private ListView lsvCarrinhoCompras;
    private List<ItemDoCarrinho> listaItensDoCarrinho;
    private AdapterItensDoCarrinho adpItemDoCarrinho;

    // controllers
    private VendaController vendaController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

        // inicializando a controller da venda:
        this.vendaController = new VendaController(ConexaoSQLite.getInstancia(this));

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

        this.tvTotalVenda = (TextView) this.findViewById(R.id.tvTotalVenda);

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
                       // quando estiver dentro da interface, não pode instanciar com o this!
                        double totalVenda = calcularTotalVenda(listaItensDoCarrinho);
                        atualizarValorTotalVenda(totalVenda);

                        if (!excluiu) {
                            Toast.makeText(ActivityVenda.this, "Item :  " + itemDoCarrinho.getNome() + "  foi excluido do carrinho com sucesso!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                janelaDeEscolha.create().show();


            }
        });


    }

    // clique no botão finalizarvenda para o evento de fechamento de venda
    public void eventFecharVenda(View view){
        Venda vendaFechada = this.criarVenda();

        if(this.salvarVenda(vendaFechada) == true) {
            Toast.makeText(this, "Venda fechada com sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Erro no fechamento de venda!", Toast.LENGTH_LONG).show();
        }


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
        itemDoCarrinho.setIdProduto(produtoSelecionado.getId()); // pra identificar o produto que está sendo selecionado
        itemDoCarrinho.setQuantidadeSelecionada(quantidadeProduto);
        itemDoCarrinho.setPrecoProduto(produtoSelecionado.getPreco());
        itemDoCarrinho.setprecoDoItemDaVenda(itemDoCarrinho.getPrecoProduto() * itemDoCarrinho.getQuantidadeSelecionada());

        // para adicionar o produto no clique do botão...
        this.adpItemDoCarrinho.addItemDoCarrinho(itemDoCarrinho);

        // para atualizar o total da venda:
        double totalVenda = this.calcularTotalVenda(this.listaItensDoCarrinho);
         this.atualizarValorTotalVenda(totalVenda);

    }


    // método para calcular o total da venda:
    private double calcularTotalVenda(List<ItemDoCarrinho> pListaItensDoCarrinho){

        double totalVenda = 0.0d;
          // tipo do item: lista que vai varrer
        for (ItemDoCarrinho itemDoCarrinho: pListaItensDoCarrinho) {
          totalVenda += itemDoCarrinho.getprecoDoItemDaVenda();
          Log.d("PRODUTOS ADICIONADOS:", itemDoCarrinho.toString());

        }

        return totalVenda;
    }

    // atualizar o valor da venda
    private void atualizarValorTotalVenda(double pValorTotal){
        this.tvTotalVenda.setText(String.valueOf(pValorTotal));
    }

    // pra criar a venda
    private Venda criarVenda() {
        Venda venda = new Venda();
        venda.setDataDaVenda(new Date());
        venda.setItensDaVenda(this.listaItensDoCarrinho);

        return venda;
    }

    // pra salvar a venda
    private boolean salvarVenda(Venda pVenda){
        Log.d("VENDA", pVenda.getDataDaVenda().toString());
        if(vendaController.salvarVendaController(pVenda) > 0 ){
           return true;
        }
        return false;
    }


}