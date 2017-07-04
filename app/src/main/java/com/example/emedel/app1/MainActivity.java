package com.example.emedel.app1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Obtiene el Boton btnAceptar
        btnAceptar=(Button) findViewById(R.id.btnAceptar)
        Modifica el evento OnClick del Boton btnAceptar
        setOnClickListener(new View.OnClickListener() {...}
        */
        findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Caja texto dice: " + ((EditText)findViewById(R.id.cajaTexto)).getText(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                String phone = ((EditText)findViewById(R.id.cajaTexto)).getText().toString();
                intent.putExtra("id",phone);
                startActivity(intent);
            }
        });
        //Toast.makeText(this, "HI", Toast.LENGTH_SHORT).show();
    }
}
