package com.s.d.a.a.tablelyout_tablerow_sqlite;

//import com.ebookfrenzy.database.provider.MyContentProvider;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.ContentResolver;

public class DBHandler  extends SQLiteOpenHelper{
   private ContentResolver contentResolver;

    private static final int VERSAO_BANCO_DE_DADOS = 1;
    private static final String NOME_BANCO_DE_DADOS = "produtoDB.db";
    public static final String TABELA_PRODUTO = "produto";

    public static final String ID_PRODUTO = "idProduto";
    public static final String NOME_PRODUTO = "nomeProduto";
    public static final String QTDE = "qtde";

    public DBHandler(Context context, String NomeBancoDados,
                       SQLiteDatabase.CursorFactory factory, int versaoBancoDados) {
        super(context, NOME_BANCO_DE_DADOS, factory, VERSAO_BANCO_DE_DADOS);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CRIAR_TABELA_PRODUTO = "CREATE TABLE " +
                TABELA_PRODUTO + "("
                + ID_PRODUTO + " INTEGER PRIMARY KEY," + NOME_PRODUTO
                + " TEXT," + QTDE + " INTEGER" + ")";
        db.execSQL(CRIAR_TABELA_PRODUTO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRODUTO);
        onCreate(db);
    }

    public void adicionarProduto(Produto produto) {

        ContentValues valor = new ContentValues();
        valor.put(NOME_PRODUTO, produto.getNomeProduto());
        valor.put(QTDE, produto.getQuantidade());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABELA_PRODUTO, null, valor);
        db.close();

        //contentResolver.insert(MyContentProvider.CONTENT_URI, values);

    }

    public Produto localizarProduto(String nomeProduto) {
        /**String[] projection = {COLUMN_ID,
         COLUMN_PRODUCTNAME, COLUMN_QUANTITY };

         String selection = "productname = \"" + productname + "\"";

         Cursor cursor = myCR.query(MyContentProvider.CONTENT_URI,
         projection, selection, null,
         null);

         Product product = new Product();

         if (cursor.moveToFirst()) {
         cursor.moveToFirst();
         product.setID(Integer.parseInt(cursor.getString(0)));
         product.setProductName(cursor.getString(1));
         product.setQuantity(Integer.parseInt(cursor.getString(2)));
         cursor.close();
         } else {
         product = null;
         }*/

        String consulta = "SELECT * FROM " + TABELA_PRODUTO + " WHERE " + NOME_PRODUTO
                + " = \"" + nomeProduto + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(consulta, null);
        Produto produto = new Produto();

        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            produto.setIDProduto(Integer.parseInt(cursor.getString(0)));
            produto.setNomeProduto(cursor.getString(1));

            produto.setQuantidade(Integer.parseInt(cursor.getString(2)));

            cursor.close();
        }else{
            produto = null;
        }

        db.close();

        return produto;

    }

    public boolean deletarProduto(String nomeProduto) {
        /**boolean result = false;

        String selection = "productname = \"" + productname + "\"";

        int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI,
                selection, null);

        if (rowsDeleted > 0)
            result = true;

        return result;*/

        boolean resultado = false;

        String consulta = "SELECT * FROM " + TABELA_PRODUTO + " WHERE " + NOME_PRODUTO
                + " = \"" + nomeProduto + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(consulta, null);
        Produto produto = new Produto();

        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            produto.setIDProduto(Integer.parseInt(cursor.getString(0)));
            db.delete(TABELA_PRODUTO, ID_PRODUTO + " = ?", new String[] { String.valueOf(produto.getIDProduto()) });

            cursor.close();
            return true;
        }

        db.close();

        return resultado;

    }
}
