package com.example.projetomvc.DAO;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;

// classe mais baixa do sistema
public class ProdutoDAO {

    // parâmetro final deve ser instanciado no construtor
    private final ConexaoSQLite conexaoSQLite;

    public ProdutoDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    //método de salvar, retorna o id do produto salvo
    public long salvarProdutoDAO(Produto pProduto){

        // permite abrir a conexão para escrever no banco
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            // Precisa do content values, é um objeto que armazena chave-valor
            ContentValues values = new ContentValues();
            // usa o put pra inserir, depois a chave e por último, o valor.
            values.put("id", pProduto.getId());
            values.put("nome", pProduto.getNome());
            values.put("quantidade_em_estoque",pProduto.getQuantidadeEmEstoque());
            values.put("preco", pProduto.getPreco());

            // insere no banco: tabela, columnhack e contentvalues
        long idProdutoInserido = db.insert("produto",null, values);

        // vai retornar o último id inserido:
            return idProdutoInserido;

        }catch (Exception e){
            e.printStackTrace();
        }

        // quando der erro
        return 0;
    }
}
