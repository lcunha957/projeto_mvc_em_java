package com.example.projetomvc.DAO;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetomvc.dbHelper.ConexaoSQLite;
import com.example.projetomvc.model.Produto;
import com.example.projetomvc.model.Venda;

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

}


