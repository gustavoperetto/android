package com.example.aula;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aula.model.CEP;
import com.example.aula.service.HttpService;
import com.google.gson.JsonNull;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etCep = findViewById(R.id.etMain_cep);
        final TextView tvResposta = findViewById(R.id.etMain_resposta);

        Button botao = findViewById(R.id.btnMain_buscarCep);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCep.getText().length() == 8) {
                    if (etCep.getText().toString().length() > 0 && !etCep.getText().toString().equals("") && etCep.getText().toString().length() == 8) {
                        HttpService service = new HttpService(etCep.getText().toString());
                        try {
                            CEP retorno = service.execute().get();
                            if (retorno != null) {
                                if (retorno.getCep() != null) {
                                    tvResposta.setText(retorno.toString());
                                } else if (retorno.getCep() == null) {
                                    etCep.setError("Cep não encontrado");
                                    tvResposta.setText(null);
                                }
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (etCep.getText().length() < 8) {
                    etCep.setError("Minimo 8 digitos");
                }
            }
        });
    }
}
