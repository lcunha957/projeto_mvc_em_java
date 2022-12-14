package com.example.projetomvc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetomvc.R;
import com.example.projetomvc.adapters.AdapterListaProdutos;
import com.example.projetomvc.controller.ProdutoController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Convert2Lambda")
public class ListarProdutosActivity extends AppCompatActivity {

    // criando o listView pra listar nas telas
    private ListView lsvProdutos;
    private List<Produto> produtoList;
    // o adapter que liga a listView com a lista!
    private AdapterListaProdutos adapterListaProdutos;
    private  ProdutoController produtoController;

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
        this.produtoController = new ProdutoController(ConexaoSQLite.getInstancia(ListarProdutosActivity.this));
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


        // Recuperar o produto que o usuário selecionou:
        this.lsvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {

                Produto produtoSelecionado = (Produto) adapterListaProdutos.getItem(posicao);
                //Toast.makeText(ListarProdutosActivity.this, "Produto:" + produtoSelecionado.getNome(), Toast.LENGTH_LONG).show();

                // Criando o diálogo pra escolher: excluir, editar ou cancelar o popup, é o JOptionPanel do Java
                AlertDialog.Builder janelaDeEscolha = new AlertDialog.Builder(ListarProdutosActivity.this);

                janelaDeEscolha.setTitle("Escolha:");
                janelaDeEscolha.setMessage("O que deseja fazer com o produto selecionado? ");

                //criação dos botões
                janelaDeEscolha.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        // sai do popup e some da tela do usuario
                        dialogInterface.cancel();
                    }
                });


                janelaDeEscolha.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        // faz o método de excluir o produto no banco de dados
                        boolean excluiu = produtoController.excluirProdutoController(produtoSelecionado.getId());
                        //fecha o diálogo antes de conferir no banco se realmente excluiu
                        dialogInterface.cancel();
                        // retornos se deu certo ou não a exclusão no banco.
                        if (excluiu == true) {
                            // atualiza a lista com as remoções
                            adapterListaProdutos.removerProduto(posicao);
                            // avisa o usuário que a exclusão deu certo..
                            Toast.makeText(ListarProdutosActivity.this, "Produto excluído com sucesso!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ListarProdutosActivity.this, "Erro ao excluir o produto!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                janelaDeEscolha.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        // enviar dados entre activities, o bundle é um pacote que contém os dados.
                        // O bundle vai dentro da intent, o bundle funciona como chave-valor pra ser recuperado na interface seguinte.
                        Bundle bundleDadosProduto = new Bundle();
                        bundleDadosProduto.putLong("idProduto", produtoSelecionado.getId());
                        bundleDadosProduto.putString("nome_produto", produtoSelecionado.getNome());
                        bundleDadosProduto.putDouble("preco_produto", produtoSelecionado.getPreco());
                        bundleDadosProduto.putInt("estoque_produto", produtoSelecionado.getQuantidadeEmEstoque());

                        // cria a intent pra carregar o bundle
                        Intent intentEditarProdutos = new Intent(ListarProdutosActivity.this, EditarProdutosActivity.class);
                        // setar os parâmetros pro bundle
                        intentEditarProdutos.putExtras(bundleDadosProduto);
                        // vamos começar a intent pra ir pra tela de editar produtos:
                        startActivity(intentEditarProdutos);

                    }
                });
                // chama a janela e vai entrar uma janela vazia
                janelaDeEscolha.create().show();
            }
        });



    }

    // executa o evento de clique do botão atualizar
    public void eventAtualizarProdutos(View view){
        // chamar update do adapterProdutos
        this.adapterListaProdutos.atualizar(this.produtoController.getListaProdutosController());
    }

}