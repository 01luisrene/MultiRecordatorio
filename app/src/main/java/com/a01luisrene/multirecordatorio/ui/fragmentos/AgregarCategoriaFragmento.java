package com.a01luisrene.multirecordatorio.ui.fragmentos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.io.db.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AgregarCategoriaFragmento extends Fragment implements View.OnClickListener {
    private static final String IMAGE_SELECT_ALL_TYPE = "image/*";
    private static final String REGEX_LATINOS = "^[a-zA-Z0-9-/° áÁéÉíÍóÓúÚñÑüÜ]*$";
    private static final int PROTECCION = 0;
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int READ_STORAGE_PERMISSION = 2;
    public static final String LLAVE_RETORNO_CATEGORIA = "llave.retorno.categoria";

    boolean respuestaRetornoCategoria = false;

    CircleImageView mCivImagen;
    EditText mEtTituloRecordatorio;
    TextInputLayout mTilTituloTipoRecordatorio;
    Button mBtGuardarCategoriaRecordatorio, mBtSeleccionarImagen;
    Uri mUri;
    String mRutaImagen;

    DataBaseManagerRecordatorios mManagerRecordatorios;

    protected View mView;

    public AgregarCategoriaFragmento() {
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
        this.mView = v;

        //Asignamos nuestro manager que contiene nuestros metodos CRUD
       mManagerRecordatorios = new DataBaseManagerRecordatorios(getActivity());

        //Asignado los id a las variables
        mCivImagen = (CircleImageView) v.findViewById(R.id.civ_imagen_categoria);
        mBtSeleccionarImagen = (Button) v.findViewById(R.id.bt_seleccionar_imagen);
        mEtTituloRecordatorio = (EditText) v.findViewById(R.id.et_titulo_recordatorio);
        mBtGuardarCategoriaRecordatorio = (Button) v.findViewById(R.id.bt_guardar_tipo_recordatorio);
        mTilTituloTipoRecordatorio = (TextInputLayout) v.findViewById(R.id.til_titulo_tipo_recordatorio);

        mEtTituloRecordatorio.addTextChangedListener(twCategoriaRecordatorio);

        //Evento clic
        mCivImagen.setOnClickListener(this);
        mBtSeleccionarImagen.setOnClickListener(this);
        mBtGuardarCategoriaRecordatorio.setOnClickListener(this);

        return v;
    }

    //================================ @Override =====================================//
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_seleccionar_imagen:
                seleccionarImagen();
                break;
            case R.id.civ_imagen_categoria:
                seleccionarImagen();
                break;
            case R.id.bt_guardar_tipo_recordatorio:
                validarDatos();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case READ_STORAGE_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    verGaleria();
                }else {
                    mostrarMensaje(getString(R.string.permiso_denegado_storage), 0);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            mUri = data.getData();
            File imageFile = new File(getRealPathFromURI(mUri));

            //Guarda la ruta de la imagen seleccionada en un cadena
            mRutaImagen = imageFile.getPath();

            //Muestro la imagen seleccionada
            Picasso.with(getContext()).load(new File(mRutaImagen)).error(R.drawable.ic_image_150dp).noFade().into(mCivImagen);
        }

    }

    //TODO: trabajar con esto
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void seleccionarImagen(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Habilitar permisos para la version de API 23 a mas
            int verificarPermisoReadExternalStorage = ContextCompat
                    .checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int verificarPermisoWriteExternalStorage = ContextCompat
                    .checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            boolean read_external_storage = shouldShowRequestPermissionRationale
                    (Manifest.permission.READ_EXTERNAL_STORAGE);
            boolean write_external_storage = shouldShowRequestPermissionRationale
                    (Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if((verificarPermisoReadExternalStorage != PackageManager.PERMISSION_GRANTED) ||
                    (verificarPermisoWriteExternalStorage != PackageManager.PERMISSION_GRANTED)){
                //solicitar permiso
                if(read_external_storage || write_external_storage){
                    mostrarExplicacion(REQUEST_CODE_GALLERY);
                }else{

                    String [] read_write_permission = {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(read_write_permission, READ_STORAGE_PERMISSION);

                }

            }else{
                verGaleria();
            }

        }else{

            verGaleria();
        }
    }

    private void validarDatos() {
        //Almacena el valor del campo titulo tipo recordatorio
        String stTitulo = mEtTituloRecordatorio.getText().toString().trim();
        String existeTitulo = mManagerRecordatorios.tituloCategoriaQueExiste(stTitulo);
        String mensajeTextoExiste = Utilidades.formatearCadenasStr(
                getActivity(),
                R.string.error_titulo_categoria_existe,
                existeTitulo
        );

        int valorInicialSpannable = 13;
        int valorFinalSpannable = existeTitulo.length() + valorInicialSpannable;

        boolean titulo = esTituloCategoriaValido(stTitulo);
        boolean existeTituloDb = mManagerRecordatorios.compruebaTituloCategoria(stTitulo);

        //Formato personalizado del texto [Categoría existe]
        Spannable tituloCategoriaExiste = Utilidades.setSpanCustomText(getActivity(),
                mensajeTextoExiste,
                valorInicialSpannable,
                valorFinalSpannable,
                Color.WHITE,
                1.2f,
                Typeface.BOLD);

        //Condicionamos a true
        if (existeTituloDb && titulo) {
            try{
                mManagerRecordatorios.insertarCategoriaRecordatorio(
                        null,
                        mRutaImagen,
                        stTitulo,
                        PROTECCION,
                        Utilidades.fechaHora());
            }catch (Exception e){
                //Mensaje de error
                mostrarMensaje(getString(R.string.error_al_guardar), 0);
            }finally {
                //Mensaje de registro guardado con exito
                Toast.makeText(getActivity(),
                        getString(R.string.mensaje_agregado_satisfactoriamente),
                        Toast.LENGTH_SHORT).show();

                respuestaRetornoCategoria = true;
                mCivImagen.setImageResource(R.drawable.ic_image_150dp);
                mEtTituloRecordatorio.setText("");
                mRutaImagen = null;

                //Función que sirve para devolver una respuesta de retorno
                //al formulario nuevo recordatorio
                Intent i = new Intent();
                i.putExtra(LLAVE_RETORNO_CATEGORIA, respuestaRetornoCategoria);
                getActivity().setResult(RESULT_OK, i);

            }
        }else if(!existeTituloDb){
           mensajeTituloCategoriaExiste(tituloCategoriaExiste);
        }

    }

    private boolean esTituloCategoriaValido(String titulo){
        Pattern patron = Pattern.compile(REGEX_LATINOS);
        if (!patron.matcher(titulo).matches()) {
            mTilTituloTipoRecordatorio.setError(getString(R.string.error_titulo_categoria));
            return false;
        }else if(titulo.trim().length() == 0){
            mTilTituloTipoRecordatorio.setError(getString(R.string.error_ingresar_categoria));
            return false;
        }else if(titulo.length() < 4){
            mTilTituloTipoRecordatorio.setError(getString(R.string.error_titulo_categoria_char_min));
            return false;
        }else if(titulo.length() > 60){
            mTilTituloTipoRecordatorio.setError(getString(R.string.error_titulo_categoria_char_max));
            return  false;
        } else {
            mTilTituloTipoRecordatorio.setError(null);
        }
        return true;
    }

    public void verGaleria(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        i.setType(IMAGE_SELECT_ALL_TYPE);

        Intent ic = Intent.createChooser(i, getString(R.string.seleccionar_app_imagen));

        startActivityForResult(ic, REQUEST_CODE_GALLERY);

    }
    //================================ Observadores de texto =====================================//
    private TextWatcher twCategoriaRecordatorio = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mTilTituloTipoRecordatorio.setError(null);
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        int column_index;
        String ruta = null;

        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);

            if (cursor != null){

                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                ruta = cursor.getString(column_index);

            }
            return ruta;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void mostrarExplicacion(final int tipoPeticion) {

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.adb_titulo))
                .setMessage(getString(R.string.adb_mensaje_permiso_almacenamiento))
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
                        mostrarMensaje(getString(R.string.adb_cancelar), 0);
                    }
                })
                .show();
    }

    private void mostrarMensaje(String mensaje, int estado){

        Snackbar snackbar = Snackbar.make(mView, mensaje, Snackbar.LENGTH_LONG);
        //Color de boton de accion
        View snackBarView = snackbar.getView();
        //Cambiando el color del texto
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if(estado == 1)
            //color de fondo
            snackBarView.setBackgroundColor(Color.argb(255, 76, 175, 80));
        else if(estado == 0){
            snackBarView.setBackgroundColor(Color.argb(255, 239, 83, 80));
        }
        snackbar.show();

    }

    private void mensajeTituloCategoriaExiste(Spannable mensaje){

        Snackbar snackbar = Snackbar.make(mView, mensaje, Snackbar.LENGTH_LONG);
        //Color de boton de accion
        View snackBarView = snackbar.getView();
        //Cambiando el color del texto
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackBarView.setBackgroundColor(Color.argb(255, 239, 83, 80));

        snackbar.show();

    }
}
