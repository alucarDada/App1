package com.example.emedel.app1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.health.PackageHealthStats;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private String cadena;
    private ImageButton btnCall;
    private ImageButton btnCamera;
    private ImageButton btnWeb;
    private final int PHONE_CALL_CODE = 100;
    private final int PICTURE_FROM_CAMARA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            /*Toma el TextView, guarda la cadena que le enviaron del activity anterior y despues se lo setea al TextView
            TextView textView = (TextView) findViewById(R.id.txtNombre);
            String cadena = bundle.getString("id");
            textView.setText(cadena);
            */
            //Hace lo mismo que arriba
            cadena = bundle.getString("id");
            btnCall = (ImageButton) findViewById(R.id.imgBtnCall);
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cadena != null && !cadena.isEmpty()) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            olderVersion(cadena);
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                        }
                    }
                }

                public void olderVersion(String phone) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                    if (checkPermission(Manifest.permission.CALL_PHONE)) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(SecondActivity.this, "No tienes permisos de hacer llamadas desde aqui", Toast.LENGTH_LONG).show();
                    }
                }
            });
            btnWeb = (ImageButton) findViewById(R.id.imgBtnWeb);
            btnWeb.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String web = cadena;//textView.getText().toString();
                    if (web != null && !web.isEmpty()){
                        if(!web.contains("http://")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + web));
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
                            startActivity(intent);
                        }
                    }
                }
                public void algo(){

                }
            });
            btnCamera = (ImageButton) findViewById(R.id.imgBtnCamera);
            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent,PICTURE_FROM_CAMARA);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //Estamos en el caso de llamada
            case PHONE_CALL_CODE:
                /*String permission = permissions[0];
                int result = grantResults[0];*/
                //Comprobamos que el permiso sea el de CALL_PHONE
                //if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    //Comprobamos si ha sido afectado o denegado el permiso
                    //if (result == PackageManager.PERMISSION_GRANTED) {
                        String phone = cadena;
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(SecondActivity.this,"No tiene permiso para realizar llamadas",Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(SecondActivity.this,phone,Toast.LENGTH_LONG).show();
                        startActivity(intentCall);
                    /*}else{
                        Toast.makeText(SecondActivity.this,"No tiene permiso para realizar llamadas",Toast.LENGTH_LONG).show();
                    }
                }*/
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICTURE_FROM_CAMARA:
                if(resultCode == Activity.RESULT_OK){
                    String result = data.toUri(0);
                    Toast.makeText(this,"El result es: "+result,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    //Corrobora que tengamos este permiso
    private boolean checkPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result== PackageManager.PERMISSION_GRANTED;
    }

}