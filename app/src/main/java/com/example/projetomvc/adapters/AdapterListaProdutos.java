package com.example.projetomvc.adapters;

/* Essa classe terá todos os métodos para inserir, atualizar, remover e criar
 os produtos na ListView que serão exibidos para o Usuário.
 Esta classe está ligada com os arquivos: layout_produto.xml
 e ListarProdutosActivity.java */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projetomvc.R;
import com.example.projetomvc.model.Produto;

import java.util.List;

public class AdapterListaProdutos extends BaseAdapter {

    private Context context;
    private List<Produto> produtoList;

    // Construtor

    public AdapterListaProdutos(Context context, List<Produto> produtoList) {
        this.context = context;
        this.produtoList = produtoList;
    }

    // tamanho da lista , temos que ver a quantidade de produtos da lista criada
    @Override
    public int getCount() {
        return this.produtoList.size();
    }

    // pega o item de acordo com a posição ao qual se encontra
    @Override
    public Object getItem(int posicao) {
        return this.produtoList.get(posicao);
    }

    // retorna o item pelo id, no caso pelo i
    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    public void removerProduto(int posicao){
      this.produtoList.remove(posicao);
      // comunica a todos que a lista mudou de tamanho e atualiza na interface;
      notifyDataSetChanged();
    }

    // retorna os componentes do layout_produto
    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {

        // inflate é pra carregar o layout
        View v = View.inflate(this.context, R.layout.layout_produto, null);

       TextView  tvNomeProduto = (TextView) v.findViewById(R.id.tvNomeProduto);
        TextView tvPrecoProduto = (TextView) v.findViewById(R.id.tvPrecoProduto);
        TextView tvEstoqueProduto = (TextView) v.findViewById(R.id.tvEstoqueProduto);

        // alterando os nomes dos campos pro que vier do banco e exige os resultados formatados pela posição na lista!:
        tvNomeProduto.setText(this.produtoList.get(posicao).getNome());
        tvPrecoProduto.setText(String.valueOf(this.produtoList.get(posicao).getPreco()));
        tvEstoqueProduto.setText(String.valueOf(this.produtoList.get(posicao).getQuantidadeEmEstoque()));

        // retorna a view!
        return v;
    }
}
