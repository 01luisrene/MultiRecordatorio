package com.a01luisrene.multirecordatorio.ui.fragmentos;

import android.Manifest;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.io.CopiarArchivo;
import com.a01luisrene.multirecordatorio.io.db.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AgregarCategoriaFragmento extends Fragment implements View.OnClickListener {
    private static final String IMAGE_SELECT_ALL_TYPE = "image/*";
    private static final String REGEX_LATINOS = "^[a-zA-Z0-9-/°+() áÁéÉíÍóÓúÚñÑüÜ]*$";
    //Constantes para la imagen
    public static final String CHAR_ORIGINAL= "áàäéèëíìïóòöúùüñ /°()";
    public static final String CHAR_ASCII = "aaaeeeiiiooouuun_----";
    public static final String DIRECTORIO_IMAGENES = "IMG-CAT";
    public static final String PREFIJO_IMG = "IMG_";
    public static final String TIMESTAMP = "_ddMMyyyy_HHmmss";
    public static final String BARRA = "/";
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int WRITE_STORAGE_PERMISSION = 2;

    private static final int PROTECCION = 0;
    public static final String LLAVE_RETORNO_CATEGORIA = "llave.retorno.categoria";

    public static final String TAG = "log";

    boolean respuestaRetornoCategoria = false;

    CircleImageView mCivImagen;
    EditText mEtTituloRecordatorio;
    TextInputLayout mTilTituloTipoRecordatorio;
    Button mBtGuardarCategoriaRecordatorio, mBtSeleccionarImagen;
    Uri mUri;
    String mUriInicialImagen;

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
        mCivImagen = v.findViewById(R.id.civ_imagen_categoria);
        mBtSeleccionarImagen = v.findViewById(R.id.bt_seleccionar_imagen);
        mEtTituloRecordatorio = v.findViewById(R.id.et_titulo_recordatorio);
        mBtGuardarCategoriaRecordatorio = v.findViewById(R.id.bt_guardar_tipo_recordatorio);
        mTilTituloTipoRecordatorio = v.findViewById(R.id.til_titulo_tipo_recordatorio);

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
            case WRITE_STORAGE_PERMISSION:
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
            mUriInicialImagen = imageFile.getPath();

            //Muestro la imagen seleccionada
            Picasso.with(getContext()).load(new File(mUriInicialImagen)).error(R.drawable.ic_image_150dp).noFade().into(mCivImagen);
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
        String rutaDirectorio = String.valueOf(
                getActivity().getApplicationContext().getExternalFilesDir(
                        Environment.DIRECTORY_PICTURES + BARRA + DIRECTORIO_IMAGENES)
        );
        String uriFinalImagen = null;

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

        //Condicionamos todos los datos a guardar a true
        if (existeTituloDb && titulo) {
            //======================= CREO CARPETA IMG_CAT=====================================//
            //Me aseguro que la ruta inicial de la imagen no devuelva nulo
            if (mUriInicialImagen != null) {
                //Condiciono para saber que existe (almacenamiento externo)
                if(isExternalStorageWritable()){
                    //Almaceno la ruta final de la imagen a copiar
                    uriFinalImagen = uriFinalImagen(rutaDirectorio, mUriInicialImagen);
                    //Creo la carpeta IMG_CAT y copio la imagen seleccionada a la carpeta IMG_CAT
                    copiarImagen(rutaDirectorio, mUriInicialImagen, uriFinalImagen);
                }else {
                    //Si el almacenamiento externo no esta disponible solo guardo la ruta en la DB
                    uriFinalImagen = mUriInicialImagen;
                }
            }
            try{
                mManagerRecordatorios.insertarCategoriaRecordatorio(
                        null,
                        uriFinalImagen,
                        stTitulo,
                        PROTECCION,
                        Utilidades.fechaHora());
            }catch (Exception e){
                //Mensaje de error (si el registro no se almacena en la DB)
                mostrarMensaje(getString(R.string.error_al_guardar), 0);
            }finally {
                //Mensaje de registro almacenado con exito
                Toast.makeText(getActivity(),
                        getString(R.string.mensaje_agregado_satisfactoriamente),
                        Toast.LENGTH_SHORT).show();

                //Reseteo los valores de las Views
                mCivImagen.setImageResource(R.drawable.ic_image_150dp);
                mEtTituloRecordatorio.setText("");
                mUriInicialImagen = null;

                //Devuelvo una respuesta al formulario nueo recordatorio
                //Y envio datos
                respuestaRetornoCategoria = true;
                Intent i = new Intent();
                i.putExtra(LLAVE_RETORNO_CATEGORIA, respuestaRetornoCategoria);
                getActivity().setResult(RESULT_OK, i);

            }
        }else if(!existeTituloDb){
            //Muestro un mensaje de error si existe la categoría
           mensajeTituloCategoriaExiste(tituloCategoriaExiste);
        }

    }

    private void copiarImagen(String rutaDirectorio, String uriInicialImagen, String uriFinalImagen){

        //TODO: comprobar espacio: getFreeSpace() o  getTotalSpace()
        //TODO: eliminar archivos creados: getCacheDir()
        //Arreglo que contiene la uri inicial archivo y la uri final donde se copiara el archivo
        String[] args = {uriInicialImagen, uriFinalImagen};
        //Ruta del directorio IMG_CAT
        File directorio = new File(rutaDirectorio);

        //Compruebo si la carpeta IMG_CAT existe
        if (directorio.exists() && directorio.isDirectory()) {
            //Copio la imagen seleccionada a la carpeta IMG_CAT
            CopiarArchivo.main(args);

        } else {//Se ejecuta si la carpeta IMG_CAT no existe
            //Creo la carpeta IMG_CAT
            crearCarpetaImagenesCategoria(getActivity().getApplicationContext(), DIRECTORIO_IMAGENES);
            //Copio la imagen seleccionada a la carpeta IMG_CAT creada
            CopiarArchivo.main(args);
        }
    }

    private String uriFinalImagen(String rutaDirectorio, String uriInicialImagen){
        //Obtengo el valor del string de la caja de texto categorías
        String entradaCategoria = mEtTituloRecordatorio.getText().toString().trim();
        //Devuelvo el nombre de para la imagen categoría
        String nombreImagen = nombreImagenCategoria(entradaCategoria, uriInicialImagen);
        //Devuelvo la ruta final donde se copiara la imagen
        return String.format("%s%s%s",rutaDirectorio,BARRA,nombreImagen);
    }

    private String nombreImagenCategoria(String nombreCategoria, String uriInicialImagen){
        //Nombre de la categoría
        String nombreCat = removerCaracteresEspeciales(nombreCategoria);
        //Creo una cadena con la fecha y hora
        String timeStamp = new SimpleDateFormat(TIMESTAMP, Locale.getDefault()).format(new Date());
        //Extraigo la extension de la imagen seleccionada
        String extensionImagen = String.format(".%s", Utilidades.obtenerExtensionArchivo(uriInicialImagen));

        return String.format("%s%s%s%s", PREFIJO_IMG,nombreCat,timeStamp,extensionImagen);
    }

    public String removerCaracteresEspeciales(String input) {

        //Cadena de caracteres original a sustituir.
        String original = CHAR_ORIGINAL;
        //Convierto la cadena recuperada en minúsculas
        String output = input.toLowerCase();
        //Recorro la cadena y reemplazo los caracteres
        for (int i=0; i<original.length(); i++) {
            //Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), CHAR_ASCII.charAt(i));
        }
        //Devuelvo la cadena final
        return output;
    }

    public File crearCarpetaImagenesCategoria(Context context, String nombreCarpeta) {
        //Crear directorio privado en la carpeta Pictures.
        File directorio =new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                nombreCarpeta);
        //Muestro un mensaje en el logcat si no se creo la carpeta por algun motivo
        if (!directorio.mkdirs())
            Log.e(TAG, "No se creo el directorio: " + DIRECTORIO_IMAGENES);

        return directorio;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
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
    private void seleccionarImagen(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Habilitar permisos para la version de API 23 a mas

            int verificarPermisoWriteExternalStorage = ContextCompat
                    .checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            boolean write_external_storage = shouldShowRequestPermissionRationale
                    (Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(verificarPermisoWriteExternalStorage != PackageManager.PERMISSION_GRANTED){
                //solicitar permiso
                if(write_external_storage){
                    mostrarExplicacion(REQUEST_CODE_GALLERY);
                }else{

                    String[] read_write_permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    requestPermissions(read_write_permission, WRITE_STORAGE_PERMISSION);

                }

            }else{
                verGaleria();
            }

        }else{

            verGaleria();
        }
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
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
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
