package com.example.projetomvc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetomvc.R;
import com.example.projetomvc.controller.ProdutoController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;

public class Activity_Produto extends AppCompatActivity {

    private EditText edtIdProduto; // código de barras do produto
    private EditText edtNomeProduto;
    private EditText edtQuantidadeProduto;
    private EditText edtPrecoProduto;
    private Button btnSalvarProduto;

    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        // ligando a activity com o xml....
        edtIdProduto = (EditText) findViewById(R.id.edtIdProduto); // código de barras
        edtNomeProduto = (EditText) findViewById(R.id.edtNomeProduto);
        edtQuantidadeProduto = (EditText) findViewById(R.id.edtQuantidadeProduto);
        edtPrecoProduto = (EditText) findViewById(R.id.edtPrecoProduto);
        btnSalvarProduto = (Button) findViewById(R.id.btnSalvarProduto);

        // chamar o método do clique do botão pra ficar escutando:
        this.clickNoBotaoSalvarListener();
    }

    // método pra escutar o clique do usuário, salva o produto; é executado na thread Main do Usuário
    // se colocar muita coisa no onClick, vai travar o aplicativo
    private void clickNoBotaoSalvarListener() {

        this.btnSalvarProduto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // pegar os dados digitados no produto
                Produto produtoACadastrar = getDadosProdutoDoFormulario();

                if (produtoACadastrar != null) {
                    // preciso chamar a conexão do banco por causa do dao e a controller pra salvar na DAO
                    // produtoController instancia a conexão do banco. A getInstancia pede o contexto que é o uso da classe atual.this
                    ProdutoController produtoController = new ProdutoController(ConexaoSQLite.getInstancia(Activity_Produto.this));
                    long idProduto = produtoController.salvarProdutoController(produtoACadastrar);

                    if (idProduto > 0) {
                        // se idProduto for maior do que zero salva na Controller
                        Toast.makeText(Activity_Produto.this, "Produto salvo com sucesso!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Activity_Produto.this, "Produto não pode ser salvo!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // toast como popup de comunicação. Atributos: contexto, texto e tempo
                    Toast.makeText(Activity_Produto.this, "Todos os campos são obrigatórios e devem ser preenchidos!", Toast.LENGTH_LONG).show();
                }
            }
        });
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
        if (this.edtQuantidadeProduto.getText().toString().isEmpty() == false) {
            int quantidadeproduto = Integer.parseInt(this.edtQuantidadeProduto.getText().toString());
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
}