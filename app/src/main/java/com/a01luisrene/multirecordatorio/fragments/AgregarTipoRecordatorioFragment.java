package com.a01luisrene.multirecordatorio.fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AgregarTipoRecordatorioFragment extends Fragment implements View.OnClickListener {

    private DataBaseManagerRecordatorios manager;
    CircleImageView civ_imagen;
    EditText et_titulo_recordatorio;
    String fecha;
    Button bt_guardar_tipo_recordatorio, bt_seleccionar_imagen;
    public static final int PROTECCION = 0;
    public static final int REQUEST_CODE_GALLERY = 999;



    public AgregarTipoRecordatorioFragment() {
        // Required empty public constructor
    }

    public static AgregarTipoRecordatorioFragment crear() {
        return new AgregarTipoRecordatorioFragment();
    }

    public static AgregarTipoRecordatorioFragment newInstance(String param1, String param2) {
        AgregarTipoRecordatorioFragment fragment = new AgregarTipoRecordatorioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_agregar_tipo_recordatorio, container, false);

        //Asignamos nuestro manager que contiene nuestros metodos CRUD
        manager = new DataBaseManagerRecordatorios(getActivity());

        //Asignado los id a las variables
        civ_imagen = (CircleImageView) v.findViewById(R.id.civ_imagen);
        bt_seleccionar_imagen = (Button) v.findViewById(R.id.bt_seleccionar_imagen);
        et_titulo_recordatorio = (EditText) v.findViewById(R.id.et_titulo_recordatorio);
        bt_guardar_tipo_recordatorio = (Button) v.findViewById(R.id.bt_guardar_tipo_recordatorio);

        //Obtener la fecha del sistema
        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);
        int hora = calendar.get(Calendar.HOUR);
        int minuto = calendar.get(Calendar.MINUTE);
        int segundos = calendar.get(Calendar.SECOND);

        fecha = String.valueOf(dia)
                + "/" + String.valueOf(mes)
                + "/" + String.valueOf(anio)
                + " " + String.valueOf(hora)
                + ":" + String.valueOf(minuto)
                + ":" + segundos;

        //Evento clic para el boton Guardar, seleccionar imagen
        bt_seleccionar_imagen.setOnClickListener(this);
        bt_guardar_tipo_recordatorio.setOnClickListener(this);

        Picasso.with(getContext()).load(R.drawable.ic_image_150dp).into(civ_imagen);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_seleccionar_imagen:
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
                break;
            case R.id.bt_guardar_tipo_recordatorio:
                try{
                    manager.insertarTipoRecordatorio(
                            null,
                            imageViewToByte(civ_imagen),
                            et_titulo_recordatorio.getText().toString(),
                            PROTECCION,
                            fecha);
                    Toast.makeText(getActivity(), getString(R.string.mensaje_agregado_satisfactoriamente), Toast.LENGTH_SHORT).show();
                    et_titulo_recordatorio.setText("");
                    Picasso.with(getContext()).load(R.drawable.ic_image_150dp).into(civ_imagen);
                }catch (Exception e){
                    //Mensaje de error
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static byte[] imageViewToByte(CircleImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getActivity(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                civ_imagen.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
