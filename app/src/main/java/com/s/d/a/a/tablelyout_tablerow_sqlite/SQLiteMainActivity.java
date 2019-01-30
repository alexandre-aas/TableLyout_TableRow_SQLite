package com.s.d.a.a.tablelyout_tablerow_sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SQLiteMainActivity extends AppCompatActivity {
    TextView idProduto;
    EditText nomeProduto;
    EditText quantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_main);

        idProduto   = findViewById(R.id.tvProduto);
        nomeProduto = findViewById(R.id.edtNomeProduto);
        quantidade  = findViewById(R.id.edtQuantidade);

    }

    public void adicionarProduto (View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        int qtde = Integer.parseInt(quantidade.getText().toString());

        Produto produto = new Produto(nomeProduto.getText().toString(), qtde);

        dbHandler.adicionarProduto(produto);
        nomeProduto.setText("");
        quantidade.setText("");
    }

    public void localizarProduto (View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        Produto produto = dbHandler.localizarProduto(nomeProduto.getText().toString());

        if (produto != null) {
            idProduto.setText(String.valueOf(produto.getIDProduto()));

            quantidade.setText(String.valueOf(produto.getQuantidade()));
        } else {
            idProduto.setText("Produto não encontrado.");
        }
    }

    public void deletarProduto (View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        boolean result = dbHandler.deletarProduto(nomeProduto.getText().toString());

        if (result)
        {
            idProduto.setText("Registro deletado.");
            nomeProduto.setText("");
            quantidade.setText("");
        }
        else
            idProduto.setText("Produto não enconttrado.");
    }
}
