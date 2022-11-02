package com.example.projetomvc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.projetomvc.R;
import com.example.projetomvc.model.Produto;

public class Activity_Produto extends AppCompatActivity {

     private EditText edtIdProduto;
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

    }

    private Produto getDadosProdutoDoFormulario() {
     this.produto = new Produto();

     // se o código de barras for preenchido, vai pra coluna id da tabela produto
       if(this.edtIdProduto.getText().toString().isEmpty() == false){
         long idproduto = Long.parseLong(this.edtIdProduto.getText().toString());
         this.produto.setId(idproduto);
       }else{
           return null;
       }

       // se tiver algo no campo nome do formulário, esse algo vai pra coluna Nome da tabela Produto
        if (this.edtNomeProduto.getText().toString().isEmpty() == false){
         this.produto.setNome(this.edtNomeProduto.getText().toString());
        } else{
            return null;
        }

        // se tiver algo no campo quantidade do formulário, esse algo vai pra coluna qtde da tabela Produto
        if(this.edtQuantidadeProduto.getText().toString().isEmpty() == false){
            int quantidadeproduto = Integer.parseInt(this.edtQuantidadeProduto.getText().toString());
            this.produto.setQuantidadeEmEstoque(quantidadeproduto);
        } else{
            return null;
        }

        // se tiver algo no campo preço do formulário, esse algo vai pra coluna preço da tabela Produto
        if(this.edtPrecoProduto.getText().toString().isEmpty() == false){
            double precoproduto = Double.parseDouble(this.edtPrecoProduto.getText().toString());
            this.produto.setPreco(precoproduto);
        } else{
            return null;
        }

        return produto;
    }
}