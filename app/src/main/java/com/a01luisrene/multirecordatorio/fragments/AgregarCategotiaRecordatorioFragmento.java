package com.a01luisrene.multirecordatorio.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AgregarCategotiaRecordatorioFragmento extends Fragment implements View.OnClickListener {
    public static final String FRAGMENT_AGREGAR_CATEGORIA_RECORDATORIOS = "fragment_agregar_categoria_recordatorios";
    private static final String IMAGE_SELECT_ALL_TYPE = "image/*";
    private static final String REGEX_LATINOS = "^[a-zA-Z0-9 áÁéÉíÍóÓúÚñÑüÜ]+$";
    private static final int PROTECCION = 0;
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int READ_STORAGE_PERMISSION = 2;
    private CircleImageView mCivImagen;
    private EditText mEtTituloRecordatorio;
    private TextInputLayout mTilTituloTipoRecordatorio;
    private String mFecha;
    private Button mBtGuardarTipoRecordatorio, mBtSeleccionarImagen;
    private Uri mUri;
    private String mRutaImagen;
    private DataBaseManagerRecordatorios mManager;

    public AgregarCategotiaRecordatorioFragmento() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_agregar_categoria_recordatorios, container, false);

        //Asignamos nuestro manager que contiene nuestros metodos CRUD
        mManager = new DataBaseManagerRecordatorios(getActivity());

        //Asignado los id a las variables
        mCivImagen = (CircleImageView) v.findViewById(R.id.civ_categoria_recordatorio);
        mBtSeleccionarImagen = (Button) v.findViewById(R.id.bt_seleccionar_imagen);
        mEtTituloRecordatorio = (EditText) v.findViewById(R.id.et_titulo_recordatorio);
        mBtGuardarTipoRecordatorio = (Button) v.findViewById(R.id.bt_guardar_tipo_recordatorio);
        mTilTituloTipoRecordatorio = (TextInputLayout) v.findViewById(R.id.til_titulo_tipo_recordatorio);

        mEtTituloRecordatorio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTilTituloTipoRecordatorio.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Evento clic para el boton Guardar, seleccionar imagen
        mBtSeleccionarImagen.setOnClickListener(this);
        mBtGuardarTipoRecordatorio.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_seleccionar_imagen:
                 //si la API 23 a mas
                 if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                     //Habilitar permisos para la version de API 23 a mas
                    if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        //solicitar permiso
                        if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                            mostrarExplicacion(REQUEST_CODE_GALLERY);
                        }else{

                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION);
                        }

                    }else{
                        verGaleria();
                    }

                }else{

                     verGaleria();
                }
                break;

            case R.id.bt_guardar_tipo_recordatorio:
                try{
                    validarDatos();
                }catch (Exception e){
                    //Mensaje de error
                    mostrarMensaje("Error:" + e.getMessage(), 0);
                }
                break;
        }
    }

    private void validarDatos() {
        //Almacena el valor del campo titulo tipo recordatorio
        String tituloTipoRecordatorio = mEtTituloRecordatorio.getText().toString();
        String titulo = mTilTituloTipoRecordatorio.getEditText().getText().toString();
        boolean bolTituloTipoRecordatorio = esTituloRecordatorioValido(titulo);

        //Condicionamos a true
        if (bolTituloTipoRecordatorio) {
            mManager.insertarTipoRecordatorio(
                    null,
                    mRutaImagen,
                    tituloTipoRecordatorio,
                    PROTECCION,
                    fechaHora());
            //Mensaje de save registro
            mostrarMensaje(getString(R.string.mensaje_agregado_satisfactoriamente), 1);
            mCivImagen.setImageResource(R.drawable.ic_image_150dp);
            mEtTituloRecordatorio.setText("");
            mRutaImagen = null;
        }

    }

    private boolean esTituloRecordatorioValido(String nombre){
        Pattern patron = Pattern.compile(REGEX_LATINOS);
        if (!patron.matcher(nombre).matches() || nombre.length() > 50) {
            mTilTituloTipoRecordatorio.setError(getString(R.string.error_titulo_tipo_recordatorio));
            return false;
        } else {
            mTilTituloTipoRecordatorio.setError(null);
        }
        return true;
    }

    public void verGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType(IMAGE_SELECT_ALL_TYPE);
        startActivityForResult(intent.createChooser(intent, getString(R.string.seleccionar_app_imagen)), REQUEST_CODE_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(requestCode == READ_STORAGE_PERMISSION) {
                verGaleria();
            }
        }else {
            mostrarMensaje(getString(R.string.permiso_denegado), 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            mUri = data.getData();
            File imageFile = new File(getRealPathFromURI(mUri));
            String foto = imageFile.getPath();
            //Guarda la ruta de la imagen seleccionada en un cadena
            mRutaImagen = foto.toString();
            //Muestro la imagen seleccionada
            Picasso.with(getContext()).load(new File(mRutaImagen)).error(R.drawable.ic_image_150dp).noFade().into(mCivImagen);
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    //Función que devuelve la fecha y hora del sistema
    public String fechaHora(){

        String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        return dateString;

    }

    private void mostrarExplicacion(final int tipoPeticion) {

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.sb_titulo))
                .setMessage(getString(R.string.sb_mensaje))
                .setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //pedir permiso
                        if(tipoPeticion == REQUEST_CODE_GALLERY){
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION);
                        }else{
                            throw new IllegalArgumentException();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.boton_cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Desplegar mensaje de lamentación
                        mostrarMensaje(getString(R.string.sb_mensaje_cancelar), 0);
                    }
                })
                .show();
    }

    private void mostrarMensaje(String mensaje, int estado){
        Snackbar snackbar = Snackbar.make(getView(), mensaje, Snackbar.LENGTH_LONG);
        //Color de boton de accion
        View snackBarView = snackbar.getView();
        if(estado == 1)
            //color de fondo
            snackBarView.setBackgroundColor(Color.argb(255, 76, 175, 80));
        else if(estado == 0){
            snackBarView.setBackgroundColor(Color.argb(255, 239, 83, 80));
        }
        snackbar.show();
    }
}
