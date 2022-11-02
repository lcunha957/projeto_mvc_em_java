package com.example.projetomvc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.projetomvc.R;

public class Activity_Produto extends AppCompatActivity {

     private EditText edtNomeProduto;
     private EditText edtQuantidadeProduto;
     private EditText edtPrecoProduto;
     private Button btnSalvarProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

       // ligando a activity com o xml....
        edtNomeProduto = (EditText) findViewById(R.id.edtNomeProduto);
        edtQuantidadeProduto = (EditText) findViewById(R.id.edtQuantidadeProduto);
        edtPrecoProduto = (EditText) findViewById(R.id.edtPrecoProduto);
        btnSalvarProduto = (Button) findViewById(R.id.btnSalvarProduto);

    }
}