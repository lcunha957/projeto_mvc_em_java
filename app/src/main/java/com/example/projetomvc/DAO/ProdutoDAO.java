package com.example.projetomvc.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;

import java.util.ArrayList;
import java.util.List;

// classe mais baixa do sistema
public class ProdutoDAO {

    // parâmetro final deve ser instanciado no construtor
    private final ConexaoSQLite conexaoSQLite;

    public ProdutoDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    //método de salvar, retorna o id do produto salvo
    public long salvarProdutoDAO(Produto pProduto) {

        // permite abrir a conexão para escrever no banco
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            // Precisa do content values, é um objeto que armazena chave-valor
            ContentValues values = new ContentValues();
            // usa o put pra inserir, depois a chave e por último, o valor.
            values.put("id", pProduto.getId());
            values.put("nome", pProduto.getNome());
            values.put("quantidade_em_estoque", pProduto.getQuantidadeEmEstoque());
            values.put("preco", pProduto.getPreco());

            // insere no banco: tabela, columnhack e contentvalues
            long idProdutoInserido = db.insert("produto", null, values);

            // vai retornar o último id inserido:
            return idProdutoInserido;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // pra fechar a conexão com o banco de dados...o finally é um get executado sempre
            if (db != null) {
                db.close();
            }
        }

        // quando der erro
        return 0;
    }


    // pra lista conseguir puxar os resultados da tabela produto....
    public List<Produto> getListaProdutosDAO() {
        // precisamos ter uma lista de produtos
        List<Produto> listaProdutos = new ArrayList<>();

        // preciso da conexão com o banco de dados
        SQLiteDatabase db = null;

        // preciso do cursor pra ver se tem mais de uma linha, diz qual o registro/tupla que
        // está atualmente no select.
        Cursor cursor;

        // criar a query de consulta:
        String query = "SELECT * FROM produto;";

        // busca no banco de dados...
        try {
            // instancia de leitura do banco de dados
            db = this.conexaoSQLite.getReadableDatabase();

            // cria o cursor e manda a query pra dentro dele, passa rawQuery
            cursor = db.rawQuery(query, null);

            // cursor recebe o retorno do select, retorna a 1a linha encontrada
            if (cursor.moveToFirst()) {

                // Crio o produto que servirá de mula pra carregar os dados
                Produto produtoTemporario = null;

                do {
                    // retorna os demais registros, a cada laço do do eu crio um novo produto e adiciono na lista
                    produtoTemporario = new Produto();
                    // o getLong é o tipo do atributo, e ele passa a posição: 0 para id, 1 pra nome, 2 para quantidade, 3 para preço
                    produtoTemporario.setId(cursor.getLong(0));
                    produtoTemporario.setNome(cursor.getString(1));
                    produtoTemporario.setQuantidadeEmEstoque(cursor.getInt(2));
                    produtoTemporario.setPreco(cursor.getDouble(3));

                    // adiciona na lista de produtos:
                    listaProdutos.add(produtoTemporario);

                } while (cursor.moveToNext()); // while é enquanto tiver registros na linha seguinte; senão ele pára e retorna -1
            }

            // Tudo dando certo, o listaProdutos retorna para a camada de cima: a Controller!
        } catch (Exception e) {
            // só aparece no console do logcat
            Log.d("ERRO LISTA PRODUTOS", "Erro ao retornar a lista de produtos da base de dados");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return listaProdutos;
    }

    // Para saber se o produto existe no banco ou se já foi excluído
    public boolean excluirProdutoDAO(long pIdProduto) {
        SQLiteDatabase db = null;

        try {
            // pra fazer a escrita do banco de dados
            db = this.conexaoSQLite.getWritableDatabase();

            // para deletar, passa a tabela, as cláusulas é o meu filtro e os argumentos para rvitar sql injections
            db.delete(
                    "produto",
                    "id = ?",
                    new String[]{String.valueOf(pIdProduto)}
            );
        } catch (Exception e) {
            Log.d("PRODUTODAO DELETE", "não foi possível deletar produto!");
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return true;

    }


    // Para atualizar o produto no banco de dados:
    public boolean atualizarProdutoDAO(Produto pProduto) {
        SQLiteDatabase db = null;

        try {
            db = this.conexaoSQLite.getWritableDatabase();
            // Precisa de 4 parâmetros: tabela, chave-valor, condição do where, rede de strings
            ContentValues produtoAtributos = new ContentValues();
            //geralmente não se atualiza o id, tem que ser os mesmos nomes da tabela Produto!
            produtoAtributos.put("nome", pProduto.getNome());
            produtoAtributos.put("quantidade_em_estoque", pProduto.getQuantidadeEmEstoque());
            produtoAtributos.put("preco", pProduto.getPreco());
            int atualizou = db.update(
                    "produto",
                    produtoAtributos,
                    "id = ?",
                    new String[]{String.valueOf(pProduto.getId())}
            );

            // é porque realmente atualizou o registro
            if (atualizou > 0) {
                return true;
            }

        } catch (Exception e) {
            Log.d("PRODUTODAO ATUALIZACAO", "não foi possível atualizar o produto!");
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return false;
    }
}
