package com.s.d.a.a.sqlite_database.provider;

import com.s.d.a.a.tablelyout_tablerow_sqlite.DBHandler;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;

public class AadsContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.s.d.a.a.sqlite_database.provider.AadsContentProvider";
    public static final String TABELA_PRODUTO = "produto";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABELA_PRODUTO);

    public static final int PRODUTOS = 1;
    public static final int ID_PRODUTO = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, TABELA_PRODUTO, PRODUTOS);
        sURIMatcher.addURI(AUTHORITY, TABELA_PRODUTO + "/#",
                ID_PRODUTO);
    }

    DBHandler aadsDHandler;

    public AadsContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase dbProduto = aadsDHandler.getWritableDatabase();
        int registrosADeletados = 0;

        switch (uriType) {
            case PRODUTOS:
                registrosADeletados = dbProduto.delete(aadsDHandler.TABELA_PRODUTO,
                        selection,
                        selectionArgs);
                break;

            case ID_PRODUTO:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    registrosADeletados = dbProduto.delete(aadsDHandler.TABELA_PRODUTO,
                            aadsDHandler.ID_PRODUTO + "=" + id,
                            null);
                } else {
                    registrosADeletados = dbProduto.delete(aadsDHandler.TABELA_PRODUTO,
                            aadsDHandler.ID_PRODUTO + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("URI inválida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return registrosADeletados;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase dbProduto = aadsDHandler.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case PRODUTOS:
                id = dbProduto.insert(aadsDHandler.TABELA_PRODUTO, null, values);
                break;
            default:
                throw new IllegalArgumentException("URI inválida: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(TABELA_PRODUTO + "/" + id);
    }

    @Override
    public boolean onCreate() {
        aadsDHandler = new DBHandler(getContext(), null, null, 1);

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder consultaSQL = new SQLiteQueryBuilder();
        consultaSQL.setTables(aadsDHandler.TABELA_PRODUTO);

        int tipoDoURI = sURIMatcher.match(uri);

        switch (tipoDoURI) {
            case ID_PRODUTO:
                consultaSQL.appendWhere(aadsDHandler.ID_PRODUTO + "="
                        + uri.getLastPathSegment());
                break;
            case PRODUTOS:
                break;
            default:
                throw new IllegalArgumentException("URI inválida.");
        }

        Cursor cursor = consultaSQL.query(aadsDHandler.getReadableDatabase(),
                projection, selection, selectionArgs, null, null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int tipoURI = sURIMatcher.match(uri);
        SQLiteDatabase dbProduto = aadsDHandler.getWritableDatabase();
        int registrosAtualizados = 0;

        switch (tipoURI) {
            case PRODUTOS:
                registrosAtualizados =
                        dbProduto.update(aadsDHandler.TABELA_PRODUTO,
                                values,
                                selection,
                                selectionArgs);
                break;
            case ID_PRODUTO:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    registrosAtualizados =
                            dbProduto.update(aadsDHandler.TABELA_PRODUTO,
                                    values,
                                    aadsDHandler.ID_PRODUTO + "=" + id, null);
                } else {
                    registrosAtualizados =
                            dbProduto.update(aadsDHandler.TABELA_PRODUTO,
                                    values,
                                    aadsDHandler.ID_PRODUTO + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("URI inválida: "
                        + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return registrosAtualizados;
    }
}
