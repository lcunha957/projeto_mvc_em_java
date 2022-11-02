package com.example.projetomvc.adapters;

/* Essa classe terá todos os métodos para inserir, atualizar, remover e criar
 os produtos na ListView que serão exibidos para o Usuário.
 Esta classe está ligada com os arquivos: layout_produto.xml
 e ListarProdutosActivity.java */

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projetomvc.R;
import com.example.projetomvc.model.ItemDoCarrinho;
import com.example.projetomvc.model.Produto;

import java.util.List;

public class AdapterItensDoCarrinho extends BaseAdapter {

    private Context context;
    private List<ItemDoCarrinho> itensDoCarrinho;

    // Construtor

    public AdapterItensDoCarrinho(Context context, List<ItemDoCarrinho> itensDoCarrinho) {
        this.context = context;
        this.itensDoCarrinho = itensDoCarrinho; // é a responsável por exibir a lista no listView.
    }

    // tamanho da lista , temos que ver a quantidade de produtos da lista criada
    @Override
    public int getCount() {
        return this.itensDoCarrinho.size();
    }

    // pega o item de acordo com a posição ao qual se encontra
    @Override
    public Object getItem(int posicao) {
        return this.itensDoCarrinho.get(posicao);
    }

    // retorna o item pelo id, no caso pelo i
    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    public boolean removerItemDoCarrinho(int posicao) {
        this.itensDoCarrinho.remove(posicao);
        // comunica a todos que a lista mudou de tamanho e atualiza na interface;
        Log.d("ITEM DEL CARRINHO", "item deletado do carrinho com sucesso!");
        notifyDataSetChanged();

        return true;
    }

    // retorna os componentes do layout_produto
    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {

        // inflate é pra carregar o layout
        View v = View.inflate(this.context, R.layout.layout_carrinho_produtos, null);

        TextView tvNomeProduto = (TextView) v.findViewById(R.id.tvNomeProduto);
        TextView tvPrecoProduto = (TextView) v.findViewById(R.id.tvPrecoProduto);
        TextView tvQuantidadeSelecionada = (TextView) v.findViewById(R.id.tvQuantidadeProduto);
        TextView tvValorItem = (TextView) v.findViewById(R.id.tvValorTotalItem);

        // alterando os nomes dos campos pro que vier do banco e exige os resultados formatados pela posição na lista!:
        tvNomeProduto.setText(this.itensDoCarrinho.get(posicao).getNome());
        tvPrecoProduto.setText(String.valueOf(this.itensDoCarrinho.get(posicao).getPrecoProduto()));
        tvQuantidadeSelecionada.setText(String.valueOf(this.itensDoCarrinho.get(posicao).getQuantidadeSelecionada()));
        tvValorItem.setText(String.valueOf(this.itensDoCarrinho.get(posicao).getprecoDoItemDaVenda()));

        // retorna a view!
        return v;
    }

    // Adicionar um item ao carrinho de compras
    public void addItemDoCarrinho(ItemDoCarrinho pItemDoCarrinho) {
        this.itensDoCarrinho.add(pItemDoCarrinho);
        Log.d("ITEM ADD CARRINHO", "item adicionado ao carrinho com sucesso!");
        this.notifyDataSetChanged();
    }

    // pega a lista do listView de listar_produtos e atualiza os produtos do adapter
    public void atualizar(List<ItemDoCarrinho> pItensDoCarrinho) {
        this.itensDoCarrinho.clear();
        // a lista do adapter vai receber uma lista com novos produtos
        this.itensDoCarrinho = pItensDoCarrinho;
        // notifica o usuário e a interface da mudança
        this.notifyDataSetChanged();
    }
}
