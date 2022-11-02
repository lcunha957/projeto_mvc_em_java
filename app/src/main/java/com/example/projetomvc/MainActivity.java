package com.example.projetomvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetomvc.activities.Activity_Produto;

public class MainActivity extends AppCompatActivity {

    private Button btnCadastroProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnCadastroProdutos = (Button) findViewById(R.id.btnCadastroProdutos);

        this.btnCadastroProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Activity_Produto.class);
                startActivity(intent);
            }
        });

    }
}