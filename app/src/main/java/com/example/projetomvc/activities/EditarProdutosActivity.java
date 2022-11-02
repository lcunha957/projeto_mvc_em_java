package com.example.projetomvc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetomvc.R;
import com.example.projetomvc.controller.ProdutoController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;

public class EditarProdutosActivity extends AppCompatActivity {

    private EditText edtIdProduto;
    private EditText edtNomeProduto;
    private EditText edtPrecoProduto;
    private EditText edtEstoqueProduto;
    private Produto produto;
    private Button btnSalvarAlteracoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produtos);

        edtIdProduto = (EditText) findViewById(R.id.edtIdProduto);
        edtNomeProduto = (EditText) findViewById(R.id.edtNomeProduto);
        edtPrecoProduto = (EditText) findViewById(R.id.edtPrecoProduto);
        edtEstoqueProduto = (EditText) findViewById(R.id.edtQuantidadeProduto);
        btnSalvarAlteracoes = (Button) findViewById(R.id.btnEditarProduto);

        // recuperar os dados do produto
        Bundle bundleDadosProduto = getIntent().getExtras();

        Produto produto = new Produto();
        produto.setId(bundleDadosProduto.getLong("id_produto"));
        produto.setNome(bundleDadosProduto.getString("nome_produto"));
        produto.setPreco(bundleDadosProduto.getDouble("preco_produto"));
        produto.setQuantidadeEmEstoque(bundleDadosProduto.getInt("estoque_produto"));

        // coloca os dados na interface do produto
        this.setDadosProduto(produto);


        this.clickNoBotaoSalvarListener();
    }

    private void setDadosProduto(Produto produto) {
        // passando os dados pro editText para capturar as alterações
        this.edtIdProduto.setText(String.valueOf(produto.getId()));
        this.edtNomeProduto.setText(produto.getNome());
        this.edtPrecoProduto.setText(String.valueOf(produto.getPreco()));
        this.edtEstoqueProduto.setText(String.valueOf(produto.getQuantidadeEmEstoque()));

    }


    private Produto getDadosProdutoDoFormulario() {
        this.produto = new Produto();

        // se o código de barras for preenchido, vai pra coluna id da tabela produto
        if (this.edtIdProduto.getText().toString().isEmpty() == false) {
            long idproduto = Long.parseLong(this.edtIdProduto.getText().toString());
            this.produto.setId(idproduto);
        } else {
            return null;
        }

        // se tiver algo no campo nome do formulário, esse algo vai pra coluna Nome da tabela Produto
        if (this.edtNomeProduto.getText().toString().isEmpty() == false) {
            this.produto.setNome(this.edtNomeProduto.getText().toString());
        } else {
            return null;
        }

        // se tiver algo no campo quantidade do formulário, esse algo vai pra coluna qtde da tabela Produto
        if (this.edtEstoqueProduto.getText().toString().isEmpty() == false) {
            int quantidadeproduto = Integer.parseInt(this.edtEstoqueProduto.getText().toString());
            this.produto.setQuantidadeEmEstoque(quantidadeproduto);
        } else {
            return null;
        }

        // se tiver algo no campo preço do formulário, esse algo vai pra coluna preço da tabela Produto
        if (this.edtPrecoProduto.getText().toString().isEmpty() == false) {
            double precoproduto = Double.parseDouble(this.edtPrecoProduto.getText().toString());
            this.produto.setPreco(precoproduto);
        } else {
            return null;
        }

        return produto;
    }

    private void clickNoBotaoSalvarListener() {

        this.btnSalvarAlteracoes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // pegar os dados digitados no produto
                Produto produtoAAtualizar = getDadosProdutoDoFormulario();

                if (produtoAAtualizar != null) {
                    // preciso chamar a conexão do banco por causa do dao e a controller pra salvar na DAO
                    // produtoController instancia a conexão do banco. A getInstancia pede o contexto que é o uso da classe atual.this
                    ProdutoController produtoController = new ProdutoController(ConexaoSQLite.getInstancia(EditarProdutosActivity.this));
                    boolean atualizou = produtoController.atualizarProdutoController(produtoAAtualizar);

                    if (atualizou == true) {
                        // se idProduto for maior do que zero salva na Controller
                        Toast.makeText(EditarProdutosActivity.this, "Produto atualizado com sucesso!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EditarProdutosActivity.this, "Produto não pode ser atualizado!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //toast como popup de comunicação. Atributos: contexto, texto e tempo
                    Toast.makeText(EditarProdutosActivity.this, "Todos os campos são obrigatórios e devem ser preenchidos!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}