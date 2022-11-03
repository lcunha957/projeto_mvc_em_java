package com.example.projetomvc.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.ItemDoCarrinho;
import com.example.projetomvc.model.Produto;
import com.example.projetomvc.model.Venda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaDAO {

    private final ConexaoSQLite conexaoSQLite;

    public VendaDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public long salvarVendaDAO(Venda pVenda) {
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            // o id ele cria sozinho
            values.put("data", pVenda.getDataDaVenda().getTime()); // salva data como inteiro
            long idVendaInserido = db.insert("venda", null, values);
            return idVendaInserido;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return 0;
    }

    public boolean salvarItensDaVendaDAO(Venda pVenda) {
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            ContentValues values = null;
            // o id ele cria sozinho
            for (ItemDoCarrinho itemDaVenda : pVenda.getItensDaVenda()) {
                values = new ContentValues();
                // campos vem da conexaosqlite- dbhelper
                values.put("quantidade_vendida", itemDaVenda.getQuantidadeSelecionada());
                values.put("id_produto", itemDaVenda.getIdProduto());
                values.put("id_venda", pVenda.getId());
                db.insert("item_da_venda", null, values);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return false;

    }

    public List<Venda> listarVendasDAO() {
        List<Venda> listaVendas = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor;

        String query =
                "SELECT " +
                        "venda.id," +
                        "venda.data," +
                        "SUM(produto.preco)," +
                        "COUNT(produto.id)" +
                        "FROM venda" +
                        "INNER JOIN item_da_venda ON(venda.id = item_da_venda.id_venda)" +
                        "INNER JOIN produto ON (item_da_venda.id_produto = produto.id)" +
                        "GROUP BY venda.id";

        try {
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                Venda vendaTemp = null;
                do {
                    vendaTemp = new Venda();
                    vendaTemp.setId(cursor.getLong(0));
                    vendaTemp.setDataDaVenda(new Date(cursor.getLong(1)));
                    vendaTemp.setTotalVenda(cursor.getDouble(2));
                    vendaTemp.setQtdeItens(cursor.getInt(3));

                    listaVendas.add(vendaTemp);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO VENDAS", "Erro : " + e.getMessage());
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return listaVendas;

    }
}


