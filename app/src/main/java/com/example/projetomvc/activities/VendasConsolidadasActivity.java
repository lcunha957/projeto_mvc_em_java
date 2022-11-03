package com.example.projetomvc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.projetomvc.R;
import com.example.projetomvc.adapters.AdapterListaDasVendas;
import com.example.projetomvc.controller.VendaController;
import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Venda;

import java.util.List;

public class VendasConsolidadasActivity extends AppCompatActivity {

    private ListView lsvVendas;
    private List<Venda> listaDeVendasFeitas;
    private AdapterListaDasVendas adapterListaDasVendas;
    private VendaController vendaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas_consolidadas);

        this.vendaController = new VendaController(ConexaoSQLite.getInstancia(VendasConsolidadasActivity.this));
        listaDeVendasFeitas = this.vendaController.listarVendasController();

        this.lsvVendas = (ListView) findViewById(R.id.lsvMinhasVendas);

        this.adapterListaDasVendas = new AdapterListaDasVendas(VendasConsolidadasActivity.this, listaDeVendasFeitas);

         this.lsvVendas.setAdapter(this.adapterListaDasVendas);



    }
}