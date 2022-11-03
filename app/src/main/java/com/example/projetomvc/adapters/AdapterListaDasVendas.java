package com.example.projetomvc.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projetomvc.R;
import com.example.projetomvc.model.Produto;
import com.example.projetomvc.model.Venda;

import java.util.List;

public class AdapterListaDasVendas  extends BaseAdapter{

    private Context context;
    private List<Venda> vendaList;

    // Construtor

    public AdapterListaDasVendas(Context context, List<Venda> vendaList) {
        this.context = context;
        this.vendaList = vendaList; // é a responsável por exibir a lista no listView.
    }

    // tamanho da lista , temos que ver a quantidade de produtos da lista criada
    @Override
    public int getCount() {
        return this.vendaList.size();
    }

    // pega o item de acordo com a posição ao qual se encontra
    @Override
    public Object getItem(int posicao) {
        return this.vendaList.get(posicao);
    }

    // retorna o item pelo id, no caso pelo i
    @Override
    public long getItemId(int posicao) {
        return posicao;
    }


    // retorna os componentes do layout_produto
    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {

        // inflate é pra carregar o layout
        View v = View.inflate(this.context, R.layout.layout_minhasvendas, null);

        TextView  tvDataVenda = (TextView) v.findViewById(R.id.tvDataVenda);
        TextView tvPrecoTotal = (TextView) v.findViewById(R.id.tvTotalVenda);
        TextView tvQtdeItens = (TextView) v.findViewById(R.id.tvQtdeItens);

        // alterando os nomes dos campos pro que vier do banco e exige os resultados formatados pela posição na lista!:
        tvDataVenda.setText(this.vendaList.get(posicao).getDataDaVenda().toString());
        tvPrecoTotal.setText(String.valueOf(this.vendaList.get(posicao).getTotalVenda()));
        tvQtdeItens.setText(String.valueOf(this.vendaList.get(posicao).getQtdeItens()));



        // retorna a view!
        return v;
    }
    // pega a lista do listView de listar_minhasvendas e atualiza os produtos do adapter
    public void atualizar(List<Venda> pVendas){
        this.vendaList.clear();
        // a lista do adapter vai receber uma lista com novos produtos
        this.vendaList = pVendas;
        // notifica o usuário e a interface da mudança
       this.notifyDataSetChanged(); 
    }

}
