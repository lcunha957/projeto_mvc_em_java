package com.example.projetomvc.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexaoSQLite  extends SQLiteOpenHelper {

    // deixa claro que essa instância que implementa o Sincroton, instancia 1x no ciclo de vida do sistema.
    private static ConexaoSQLite INSTANCIA_CONEXAO;
    private static final int VERSAO_DB = 1;
    private static final String NOME_DB = "bl_produtos_app";

    // construtor máximo com super
    public ConexaoSQLite(@Nullable Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
    }

    // MÁGICA DO SINCROTON
    public static ConexaoSQLite getInstancia(Context context) {
      if(INSTANCIA_CONEXAO == null) {
          INSTANCIA_CONEXAO = new ConexaoSQLite(context);
      }
      return INSTANCIA_CONEXAO;
    }

    // ao criar a aplicação
    @Override
    public void onCreate(SQLiteDatabase db) {
     // cria tabelas senao existir

        String sqlTabelaProduto =
                "CREATE TABLE IF NOT EXISTS produto" +
                        "(" +
                         "id INTEGER PRIMARY KEY," +
                         "nome TEXT," +
                         "quantidade_em_estoque INTEGER," +
                         "preco REAL" +
                         ")";

        db.execSQL(sqlTabelaProduto);

    }

    // ao atualizar a versão do banco de dados
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
