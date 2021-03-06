package com.a01luisrene.multirecordatorio.ui.fragmentos;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.interfaces.InterfaceCrud;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.io.db.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.ui.DetalleCategoriaActivity;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.a01luisrene.multirecordatorio.ui.fragmentos.DetalleRecordatorioFragmento.*;

public class ActualizarRecordatorioFragmento extends Fragment
        implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    //Constantes
    public static final String ESTADO_RECORDATORIO = "1";
    public static final String ENVIAR_ON = "1";
    public static final String ENVIAR_OFF = "0";
    public static final String VI_CARACTERES_TWITTER_INFO = "140"; //VI = valor inical
    public static final String VI_CANTIDAD_MENSAJE_INFO = "0";
    public static final String ID_CATEGORIA_NULO = "nulo";
    public static final String VALOR_VACIO = "";
    public static final String CERO = "0";
    public static final String UNO = "1";
    public static final String RUTA_CARPETA_ASSETS = "file:///android_asset/";
    public static final String BARRA = "/";
    public static final String DOS_PUNTOS = ":";
    public static final String LLAVE_RETORNO_ACTUALIZAR_RECORDATORIO = "llave.retorno.actualizar.recordatorio";
    public static int MILISEGUNDOS_ESPERA = 1000;
    public static final int CODIGO_RESPUESTA_CATEGORIA = 100;
    public static final int PICK_CONTACT_REQUEST = 101;
    private static final int PICK_SMS_REQUEST = 102;
    private static final int READ_CONTACTS_PERMISSION = 103;
    public static final int SEND_SMS_PERMISSION = 104;

    //Expresiones regulares
    public static final String REGEX_CARACTERES_LATINOS = "^[a-zA-Z0-9-/°. áÁéÉíÍóÓúÚñÑüÜ]*$";
    public static final String REGEX_FECHAS = "^([012][1-9]|3[01])(\\/)(0[1-9]|1[012])\\2(\\d{4})$";
    public static final String REGEX_HORAS = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

    //Referencias de widgets del fragmento
    Button mBotonAgregarCategoria;
    TextInputEditText mTieTituloRecordatorio, mTieEntidadOtros, mTieTelefono, mTieContenidoMensaje, mTieFecha, mTieHora;
    TextInputLayout mTilTituloRecordatorio, mTilEntidadOtros, mTilTelefono, mTilContenidoMensaje, mTilFecha, mTilHora;
    Switch mSwFacebook, mSwTwitter, mSwEnviarMensaje;
    String mValorFacebook, mValorTwitter, mValorEnviarMensaje;
    String[] tituloObtenido;
    ImageButton mIbContactos, mIbFecha, mIbHora, mIbFacebookInfo, mIbTwitterInfo, mIbMensajeInfo;
    TextView mTvTwitterInfo, mTvMensajeInfo;

    //Booleanos
    boolean guardarNumeroTelefono = true;

    //[Combo categoria recordatorios]
    Spinner mSpinnerListaCategotegorias;
    //Variables para el combo
    List<String> comboListaCategorias;
    ArrayAdapter<String> comboAdapter;

    //Obtener número de los contactos del phone
    Cursor contactCursor, phoneCursor;
    Uri contactoUri;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    int diaIngresado, mesIngresado, anioIngresado;
    int obtenerCantidadCaracteresCampoFecha;

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    int horaIngresada, minutoIngresado;

    int cantidadMensajesInfo;

    //Referencia a manager de SQLite
    DataBaseManagerRecordatorios mManagerRecordatorios;
    //POJO (Plain Old Java Object)
    Recordatorios mItemRecordatorio;

    // Objeto de la interfaz remover, con este objeto llamaremos el
    // método de la interfaz
    private InterfaceCrud mCrud; //CRUD: crear, leer, actualizar, eliminar

    //Referencias de widgets que se encuentran en activity detalle
    CollapsingToolbarLayout mCtCategoriaRecordatorio;
    ImageView mIvImagenRecordatorio;
    String  mValorIdCategoria, mValorRutaImgCategoria, mValorTituloCategoria;
    int mValorProteccionImg;
    FloatingActionButton mFabAddUpd;
    Activity activity;

    //Referencias de widgets que se encuentran en layout toolbar_top.xml
    CircleImageView mCivImagenRecordatorio;
    TextView mTvTituloCategoriaRecordatorio;
    ImageButton mIbEliminarRecordatorio, mIbAcutualizarRecordatorio;

    protected View mView;

    public ActualizarRecordatorioFragmento() {
        // Required empty public constructor
    }

    public static ActualizarRecordatorioFragmento actualizarRecordatorio(Recordatorios recordatorios) {
        ActualizarRecordatorioFragmento fragmentoActualizar = new ActualizarRecordatorioFragmento();

        Bundle args = new Bundle();
        args.putParcelable(KEY_RECORDATORIO, recordatorios);
        fragmentoActualizar.setArguments(args);

        return fragmentoActualizar;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(KEY_RECORDATORIO)) {
            mItemRecordatorio = getArguments().getParcelable(KEY_RECORDATORIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_actualizar_recordatorio, container, false);

        if(mItemRecordatorio != null) {
            this.mView = v; //Para mostrar mensaje
            activity = this.getActivity();
            //Asignamos nuestro manager que contiene nuestros metodos CRUD
            mManagerRecordatorios = new DataBaseManagerRecordatorios(getActivity().getApplicationContext());

            if(Utilidades.smartphone) {
                //Widgets de la activity Detalle Recordatorio
                mIvImagenRecordatorio = (ImageView) activity.findViewById(R.id.iv_cover);
                mCtCategoriaRecordatorio = (CollapsingToolbarLayout) activity.findViewById(R.id.ct_categoria_recordatorio);
            }else{
                //Widgets del layout toolbar_top.xml
                mCivImagenRecordatorio = (CircleImageView) activity.findViewById(R.id.civ_toolbar);
                mTvTituloCategoriaRecordatorio = (TextView) activity.findViewById(R.id.tv_titulo_toolbar);
                mIbAcutualizarRecordatorio = (ImageButton) activity.findViewById(R.id.ib_editar_toolbar);
                mIbEliminarRecordatorio = (ImageButton) activity.findViewById(R.id.ib_eliminar_toolbar);

                mIbAcutualizarRecordatorio.setVisibility(View.INVISIBLE);
                mIbEliminarRecordatorio.setVisibility(View.INVISIBLE);
            }
            //Botón fab para actualizar recordatorio
            mFabAddUpd = (FloatingActionButton) activity.findViewById(R.id.fab_add_upd);
            mFabAddUpd.setVisibility(View.VISIBLE);

            //String[]
            tituloObtenido = new String[]{mItemRecordatorio.getTitulo()};

            //Spinner
            mSpinnerListaCategotegorias = (Spinner) v.findViewById(R.id.sp_categorias_recordatorios);

            //TextInputLayout
            mTilTituloRecordatorio = (TextInputLayout)  v.findViewById(R.id.til_nuevo_titulo_recordatorio);
            mTilEntidadOtros = (TextInputLayout) v.findViewById(R.id.til_nuevo_entidad_otros);
            mTilTelefono = (TextInputLayout) v.findViewById(R.id.til_nuevo_telefono);
            mTilContenidoMensaje = (TextInputLayout) v.findViewById(R.id.til_nuevo_contenido_mensaje);
            mTilFecha = (TextInputLayout) v.findViewById(R.id.til_nuevo_fecha);
            mTilHora = (TextInputLayout) v.findViewById(R.id.til_nuevo_hora);

            //TextInputEditText
            mTieTituloRecordatorio = (TextInputEditText) v.findViewById(R.id.tie_nuevo_titulo_recordatorio);
            mTieEntidadOtros = (TextInputEditText) v.findViewById(R.id.tie_nuevo_entidad_otros);
            mTieTelefono = (TextInputEditText) v.findViewById(R.id.tie_nuevo_telefono);
            mTieContenidoMensaje = (TextInputEditText) v.findViewById(R.id.tie_nuevo_contenido_mensaje);
            mTieFecha = (TextInputEditText) v.findViewById(R.id.tie_nuevo_fecha);
            mTieHora = (TextInputEditText) v.findViewById(R.id.tie_nuevo_hora);

            //TextView
            mTvTwitterInfo = (TextView) v.findViewById(R.id.tv_twitter_info);
            mTvMensajeInfo = (TextView) v.findViewById(R.id.tv_mensaje_info);

            //Switch
            mSwFacebook = (Switch) v.findViewById(R.id.sw_facebook);
            mSwTwitter = (Switch) v.findViewById(R.id.sw_twitter);
            mSwEnviarMensaje = (Switch) v.findViewById(R.id.sw_enviar_mensaje);

            mValorEnviarMensaje = ENVIAR_OFF;
            mValorFacebook = ENVIAR_OFF;
            mValorTwitter = ENVIAR_OFF;

            //Botones
            mBotonAgregarCategoria = (Button) v.findViewById(R.id.bt_agregar_categoria);

            //Botón con imagenes
            mIbFecha = (ImageButton) v.findViewById(R.id.ib_obtener_fecha);
            mIbHora = (ImageButton) v.findViewById(R.id.ib_obtener_hora);
            mIbContactos = (ImageButton) v.findViewById(R.id.bt_contactos);
            mIbFacebookInfo = (ImageButton) v.findViewById(R.id.ib_facebook_info);
            mIbTwitterInfo = (ImageButton) v.findViewById(R.id.ib_twitter_info);
            mIbMensajeInfo = (ImageButton) v.findViewById(R.id.ib_mensaje_info);

            //TextInputEditText: addTextChangedListener con un observador de texto
            mTieTituloRecordatorio.addTextChangedListener(twTituloRecordatorio);
            mTieEntidadOtros.addTextChangedListener(twEntidadOtros);
            mTieContenidoMensaje.addTextChangedListener(twContenidoMensaje);
            mTieTelefono.addTextChangedListener(twTelefono);
            mTieFecha.addTextChangedListener(twFecha);
            mTieHora.addTextChangedListener(twHora);

            //Muestro la cantidad de textos disponibles para twitter
            mTvTwitterInfo.setText(VI_CARACTERES_TWITTER_INFO);
            mTvMensajeInfo.setText(VI_CANTIDAD_MENSAJE_INFO);

            mSwFacebook.setOnCheckedChangeListener(this);
            mSwTwitter.setOnCheckedChangeListener(this);
            mSwEnviarMensaje.setOnCheckedChangeListener(this);

            mBotonAgregarCategoria.setOnClickListener(this);

            mIbContactos.setOnClickListener(this);
            mIbFecha.setOnClickListener(this);
            mIbHora.setOnClickListener(this);
            //Evento clic fab actualizar
            mFabAddUpd.setOnClickListener(this);


            //[INICIO COMBO]
            poblarSpinner();
            mSpinnerListaCategotegorias.setOnItemSelectedListener(this);
            //[FIN COMBO]

            mTieTituloRecordatorio.setText(mItemRecordatorio.getTitulo());
            mTieEntidadOtros.setText(mItemRecordatorio.getEntidadOtros());
            mTieContenidoMensaje.setText(mItemRecordatorio.getContenidoMensaje());

            //Variables de preferencias de envio
            String valorFacebook = mItemRecordatorio.getPublicarFacebook();
            String valorTwitter = mItemRecordatorio.getPublicarTwitter();
            String valorMensaje = mItemRecordatorio.getEnvioMensaje();
            //Facebook
            if(valorFacebook.equals(UNO)){
                mSwFacebook.setChecked(true);
            }else{
                mSwFacebook.setChecked(false);
            }
            //Twitter
            if(valorTwitter.equals(UNO)){
                mSwTwitter.setChecked(true);
            }else{
                mSwTwitter.setChecked(false);
            }
            //Enviar SMS
            if(valorMensaje.equals(UNO)){
                mSwEnviarMensaje.setChecked(true);
            }else{
                mSwEnviarMensaje.setChecked(false);
            }

            mTieTelefono.setText(mItemRecordatorio.getTelefono());
            mTieFecha.setText(mItemRecordatorio.getFechaPublicacionRecordatorio());
            mTieHora.setText(mItemRecordatorio.getHoraPublicacionRecordatorio());
        }else{
            Log.i("logcat", "Parcelable esta vacío");
        }

        return v;
    }

    //===============================FUNCIONES PROPIAS=================================//
    public void poblarSpinner(){

        comboListaCategorias = new ArrayList<>();

        int sizeListaCat = mManagerRecordatorios.getListaCategorias().size();

        for(int i = 0; i < sizeListaCat; i++){
            comboListaCategorias.add(mManagerRecordatorios
                    .getListaCategorias().get(i).getTituloCategoria());
        }

        comboAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, comboListaCategorias);

        comboAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mSpinnerListaCategotegorias.setAdapter(comboAdapter);

        String categoria = mItemRecordatorio.getTitulocategoria();

        mSpinnerListaCategotegorias.setSelection(Utilidades.getIndexSpinner(mSpinnerListaCategotegorias, categoria));

    }

    private void abrirFormularioNuevaCategoria() {

        Intent i = new Intent(getActivity(), DetalleCategoriaActivity.class);
        startActivityForResult(i, CODIGO_RESPUESTA_CATEGORIA);

    }

    private void enviarSms(){
        //si la API 23 a mas
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int verificarPermisoSendSms = ContextCompat
                    .checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);
            if(verificarPermisoSendSms != PackageManager.PERMISSION_GRANTED){
                //solicitar permiso
                if(shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)){

                    mostrarExplicacion(PICK_SMS_REQUEST, getString(R.string.adb_mensaje_permiso_sms));
                    noEnviarSms();
                }else{
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION);
                }

            }else{
                activarEnvioSms();
            }
        }else{
            activarEnvioSms();
        }
    }
    private void noEnviarSms(){
        mValorEnviarMensaje = ENVIAR_OFF;
        mIbContactos.setVisibility(View.GONE);
        mTilTelefono.setVisibility(View.GONE);
        mTieTelefono.setText(VALOR_VACIO);
        //Si el usuario no desea enviar mensaje [Guardo compo teléfono vacío]
        guardarNumeroTelefono = true;
        mSwEnviarMensaje.setChecked(false);
    }

    private void activarEnvioSms(){
        mValorEnviarMensaje = ENVIAR_ON;
        mIbContactos.setVisibility(View.VISIBLE);
        mTilTelefono.setVisibility(View.VISIBLE);

        //Compruebo si el campo de teléfono es válido
        String numeroTelefono = mTieTelefono.getText().toString();

        //False
        guardarNumeroTelefono = false;

        //True
        if(!numeroTelefono.isEmpty()){
            guardarNumeroTelefono = esTelefonoValido(numeroTelefono);
        }

        mSwEnviarMensaje.setChecked(true);
    }

    private void accederAgendaContactos(){
        //si la API 23 a mas
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Habilitar permisos para la version de API 23 a mas
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                //solicitar permiso
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
                    mostrarExplicacion(PICK_CONTACT_REQUEST,
                            getString(R.string.adb_mensaje_permiso_contactos));
                }else{

                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION);
                }

            }else{
                abrirIntentContactos();
            }

        }else{

            abrirIntentContactos();
        }
    }

    public void abrirIntentContactos(){
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(i, PICK_CONTACT_REQUEST);
    }

    public void validarDatos() {
        String stTitulo = mTieTituloRecordatorio.getText().toString().trim();
        String stEntidadOtros = mTieEntidadOtros.getText().toString().trim();
        String stContenidoMensaje = mTieContenidoMensaje.getText().toString().trim();
        String stTelefono = mTieTelefono.getText().toString().trim();
        String stFecha = mTieFecha.getText().toString().trim();
        String stHora = mTieHora.getText().toString().trim();
        String existeTitulo = mManagerRecordatorios.tituloRecordatorioQueExisteUp(stTitulo);
        String mensajeTextoExiste = Utilidades.formatearCadenasStr(
                getActivity(),
                R.string.error_titulo_recordatorio_existe,
                existeTitulo
        );

        int valorInicialSpannable = 10;
        int valorFinalSpannable = existeTitulo.length() + valorInicialSpannable;

        boolean titulo = esTituloRecordatorioValido(stTitulo);
        boolean entidadOtros = esEntidadOtrosValido(stEntidadOtros);
        boolean contenidoMensaje = esContenidoMensajeValido(stContenidoMensaje);
        boolean fechaIngresada = esFechaIngresadaValido(stFecha);
        boolean horaIngresada = esHoraIngresadaValido(stHora);
        boolean existeTituloDb = mManagerRecordatorios
                .compruebaTituloRecordatorioUp(stTitulo, tituloObtenido[0]);

        //Formato personalizado del texto [Recordatorio existe]
        Spannable tituloRecordatorioExiste = Utilidades.setSpanCustomText(getActivity(),
                mensajeTextoExiste,
                valorInicialSpannable,
                valorFinalSpannable,
                Color.WHITE,
                1.2f,
                Typeface.BOLD);



        if (!mValorIdCategoria.equals(ID_CATEGORIA_NULO)) {

            if (existeTituloDb && titulo && entidadOtros &&
                    contenidoMensaje && guardarNumeroTelefono &&
                    fechaIngresada && horaIngresada) {

                try {
                    mManagerRecordatorios.actualizarRecoratorio(
                            mItemRecordatorio.getId(),                          //[_id]
                            stTitulo,                                           //[Titulo]
                            stEntidadOtros,                                     //[Entidad - Otros]
                            stContenidoMensaje,                                 //[Contenido del mensaje]
                            mValorFacebook,                                     //[Publicar en facebook]
                            mValorTwitter,                                      //[Publicar en twitter]
                            mValorEnviarMensaje,                                //[Envio mesaje]
                            stTelefono,                                         //[Teléfono]
                            mItemRecordatorio.getFechaCreacionRecordatorio(),    //[Fecha creación]
                            stFecha,                                            //[Fecha del recordatorio]
                            stHora,                                             //[Hora del recordatorio]
                            ESTADO_RECORDATORIO,                                //[Estado recordatorio]
                            mValorIdCategoria                                   //[Id Categoría]
                    );


                } catch (Exception e) {
                    //Mensaje de error
                    mostrarMensaje(getString(R.string.error_al_guardar), 0);
                } finally {
                    //Mensaje de registro guardado con exito
                    Toast.makeText(getActivity(), getString(R.string.actualizado_recordatorio_almacenado),
                            Toast.LENGTH_SHORT).show();

                    Recordatorios actualizarItem = new Recordatorios(
                            mItemRecordatorio.getId(),
                            stTitulo,
                            stEntidadOtros,
                            stContenidoMensaje,
                            mValorFacebook,
                            mValorTwitter,
                            mValorEnviarMensaje,
                            stTelefono,
                            Utilidades.fechaHora(),
                            stFecha,
                            stHora,
                            mValorTituloCategoria,
                            mValorRutaImgCategoria,
                            mValorProteccionImg
                    );

                    //Cierro la activity siempre que me encuentre en un smartphone
                    if(Utilidades.smartphone){
                        //Envio la respuesta con los datos del modelo
                        Intent i = new Intent();
                        i.putExtra(LLAVE_RETORNO_ACTUALIZAR_RECORDATORIO, actualizarItem);
                        getActivity().setResult(RESULT_OK, i);

                        esperarYCerrar(MILISEGUNDOS_ESPERA);

                    }else {
                        mCrud.actualizarItem(actualizarItem);
                    }
                }
            }else if(!existeTituloDb){
                mensajeTituloRecordatorioExiste(tituloRecordatorioExiste);
            } else if (!titulo) {
                mostrarMensaje(getString(R.string.error_titulo_recordatorio), 0);
            } else if (!contenidoMensaje) {
                mostrarMensaje(getString(R.string.error_contenido_mensaje_ingresado), 0);
            } else if (!guardarNumeroTelefono) {
                mostrarMensaje(getString(R.string.error_telefono_ingresado), 0);
            } else if (!fechaIngresada) {
                mostrarMensaje(getString(R.string.error_fecha_ingresada), 0);
            } else if (!horaIngresada) {
                mostrarMensaje(getString(R.string.error_hora_ingresada), 0);
            } else {
                mostrarMensaje(getString(R.string.error_completar_campos_recordatorios), 0);
            }
        } else {
            mostrarMensaje(getString(R.string.error_spinner_categorias), 0);
        }
    }

    //Validar campos EditText
    private boolean esTituloRecordatorioValido(String titulo){
        Pattern patron = Pattern.compile(REGEX_CARACTERES_LATINOS);
        if(!patron.matcher(titulo).matches()){
            mTilTituloRecordatorio.setError(getString(R.string.error_titulo_recordatorio));
            return false;
        }else if(titulo.length() > 100){
            mTilTituloRecordatorio.setError(getString(R.string.error_titulo_recordatorio_max_char));
            return false;
        }else if(titulo.length() == 0){
            mTilTituloRecordatorio.setError(getString(R.string.error_titulo_recordatorio));
            return false;
        }else if(titulo.length() < 4){
            mTilTituloRecordatorio.setError(getString(R.string.error_titulo_recordatorio_min_char));
            return false;
        }else{
            mTilTituloRecordatorio.setError(null);
        }
        return true;
    }
    private boolean esEntidadOtrosValido(String entidadOtros) {
        Pattern patron = Pattern.compile(REGEX_CARACTERES_LATINOS);
        if (!patron.matcher(entidadOtros).matches()) {
            mTilEntidadOtros.setError(getString(R.string.error_entidad_otros));
            return false;
        }else if(entidadOtros.length() > 80){
            mTilEntidadOtros.setError(getString(R.string.error_entidad_otros_max_char));
            return false;
        }else{
            mTilEntidadOtros.setError(null);
        }
        return true;
    }
    public void validarBotonesInfo(String numeroCaracteres){

        Drawable drawable_valido, drawable_invalido;

        drawable_valido = ContextCompat.getDrawable(getActivity(), R.drawable.bt_info_valido);
        drawable_invalido = ContextCompat.getDrawable(getActivity(), R.drawable.bt_info_invalido);

        //Restar caracteres aceptados por twitter
        int caracteresValorInicialTwitter = 140;
        int caracteresTwitter = caracteresValorInicialTwitter - numeroCaracteres.length();
        int residuo = numeroCaracteres.length() % 160;

        if(residuo == 0){
            cantidadMensajesInfo = numeroCaracteres.length() / 160;
        }else{
            cantidadMensajesInfo = Math.round(numeroCaracteres.length() / 160) + 1;
        }

        mTvTwitterInfo.setText(String.valueOf(caracteresTwitter));

        mTvMensajeInfo.setText(String.valueOf(cantidadMensajesInfo));

        //Twitter
        if(numeroCaracteres.length() > 140){
            mIbTwitterInfo.setBackground(drawable_invalido);
            mSwTwitter.setEnabled(false);
            mSwTwitter.setChecked(false);
        }else{
            mIbTwitterInfo.setBackground(drawable_valido);
            mSwTwitter.setEnabled(true);
        }


    }
    private boolean esTelefonoValido(String telefono){
        if(!Patterns.PHONE.matcher(telefono).matches()){
            mTilTelefono.setError(getString(R.string.error_telefono));
            return false;
        }else{
            mTilTelefono.setError(null);
        }
        return true;
    }
    private boolean esContenidoMensajeValido(String contenidoMensaje) {
        if(contenidoMensaje.length() == 0){
            mTilContenidoMensaje.setError(getString(R.string.error_contenido_mensaje_ingresado));
            return false;
        }else if(contenidoMensaje.length() < 8){
            mTilContenidoMensaje.setError(getString(R.string.error_contenido_mensaje_min_char));
            return false;
        }else if(contenidoMensaje.length() > 1000){
            mTilContenidoMensaje.setError(getString(R.string.error_contenido_mensaje_max_char));
            return false;
        }else{
            mTilContenidoMensaje.setError(null);
        }
        return true;
    }

    public void diaMesAnioIngresado(String string){
        if(string.length() == 10){
            diaIngresado = Integer.parseInt(string.substring(0, 2));
            mesIngresado = Integer.parseInt(string.substring(3, 5));
            anioIngresado = Integer.parseInt(string.substring(6, 10));
        }

    }

    private boolean esFechaIngresadaValido(String fechaIngresada){

        Pattern patron = Pattern.compile(REGEX_FECHAS);
        if(!fechaIngresada.isEmpty())
            diaMesAnioIngresado(fechaIngresada);
        //Comprobar si el formato de fecha es válido
        if (!patron.matcher(fechaIngresada).matches()){
            mTilFecha.setError(getString(R.string.error_fecha));
            return false;
        }else{
            mTilFecha.setError(null);
        }

        int fechaActual = mes + 1;
        if(anioIngresado < anio ){
            return false;
        }else if(anioIngresado == anio){
            if(diaIngresado > dia && mesIngresado < fechaActual){
                return false;
            }else if(diaIngresado < dia && mesIngresado > fechaActual){
                return true;
            } else if(diaIngresado < dia && mesIngresado == fechaActual){
                return false;
            }else if(diaIngresado >= dia && mesIngresado < fechaActual){
                return false;
            }else if(diaIngresado < dia && mesIngresado < fechaActual){
                return false;
            }else if(diaIngresado > dia && mesIngresado == fechaActual){
                return true;
            }
            else if(diaIngresado > dia && mesIngresado > fechaActual){
                return true;
            }
        }else{
            return true;
        }

        return true;

    }

    public void horaMinutoValoresObtenidos(String hora){
        if(hora.length() == 5){
            horaIngresada = Integer.parseInt(hora.substring(0, 2));
            minutoIngresado = Integer.parseInt(hora.substring(3, 5));
        }
    }
    private boolean esHoraIngresadaValido(String horaIngresadaUsuario){

        //Funvción para obtener los valores de hora y minuto
        horaMinutoValoresObtenidos(horaIngresadaUsuario);

        Pattern patron = Pattern.compile(REGEX_HORAS);
        int mesActual = mes + 1;
        int horaLocal24 = (horaIngresada == 0)? 24: horaIngresada;
        int horaSistema24 = (hora == 0)? 24: hora;

        if (!patron.matcher(horaIngresadaUsuario).matches()){
            mTilHora.setError(getString(R.string.error_hora));
            return false;
        }else{
            mTilHora.setError(null);
        }

        if((diaIngresado == dia) && (mesIngresado == mesActual) && (anioIngresado == anio)){

            if(horaLocal24 < horaSistema24){
                return false;

            }else if(horaLocal24 == horaSistema24){
                if(minutoIngresado <= minuto){
                    return false;
                }
            }
        }


        return true;

    }
    public void diaMesAnioValoresObtenidos(int dia, int mes, int anio){
        diaIngresado = dia;
        mesIngresado = mes;
        anioIngresado = anio;
    }

    public void obtenerFecha(){

        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                if(obtenerCantidadCaracteresCampoFecha == 10)
                    diaMesAnioValoresObtenidos(dayOfMonth, mesActual, year);

                if(year > anio ){
                    mTieFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                } else if(year == anio){
                    if(dayOfMonth < dia){
                        mostrarMensaje(getString(R.string.error_dia_menor_actual), 0);
                        diaMesAnioValoresObtenidos(0, 0, 0);
                    }else if(mesActual < (mes+1)){
                        mostrarMensaje(getString(R.string.error_mes_menor_actual), 0);
                        diaMesAnioValoresObtenidos(0, 0, 0);
                    } else{
                        mTieFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                    }
                }else{
                    //Muestro mensaje de error siempre y cuando fecha sea menor a la actual
                    mostrarMensaje(getString(R.string.error_anio_menor_actual), 0);
                    diaMesAnioValoresObtenidos(0, 0, 0);
                }

            }
        },anio, mes, dia);

        recogerFecha.show();

    }
    public void obtenerHora(){

        //Cargo los valores de la fecha
        String fechaIngresada = mTieFecha.getText().toString();
        if(!fechaIngresada.isEmpty())
            diaMesAnioIngresado(fechaIngresada);

        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada = (hourOfDay < 10)? CERO + String.valueOf(hourOfDay):String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 10)? CERO + String.valueOf(minute):String.valueOf(minute);

                int mesActual = mes + 1;

                int horaLocal24 = (hourOfDay == 0)? 24 : hourOfDay;
                int horaSistema24 = (hora == 0)? 24: hora;

                if(diaIngresado > 0 && mesIngresado > 0 && anioIngresado > 0 && obtenerCantidadCaracteresCampoFecha == 10) {

                    if ((diaIngresado == dia) && (mesIngresado == mesActual) && (anioIngresado == anio)) {

                        if (horaLocal24 < horaSistema24) {
                            mostrarMensaje(getString(R.string.error_hora_menor_actual), 0);

                        } else if (horaLocal24 == horaSistema24) {
                            if (minute <= minuto) {
                                mostrarMensaje(getString(R.string.error_minuto_menor_actual), 0);

                            } else {
                                mTieHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
                            }
                        } else {
                            mTieHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
                        }

                    } else if ((diaIngresado > dia) && (mesIngresado > mesActual) && (anioIngresado == anio)) {

                        mTieHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);

                    } else if ((diaIngresado > dia) && (mesIngresado == mesActual) && (anioIngresado == anio)) {

                        mTieHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);

                    } else if ((diaIngresado == dia) && (mesIngresado > mesActual) && (anioIngresado == anio)) {

                        mTieHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);

                    } else if (anioIngresado > anio) {
                        mTieHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
                    } else if (anioIngresado < anio) {
                        mostrarMensaje(getString(R.string.error_anio), 0);

                    } else if (mesIngresado < mesActual) {
                        mostrarMensaje(getString(R.string.error_mes), 0);

                    } else if (diaIngresado < dia) {
                        mostrarMensaje(getString(R.string.error_dia), 0);
                    }
                }else{
                    mostrarMensaje(getString(R.string.error_primero_ingrese_fecha), 0);
                }



            }

        }, hora, minuto, true);

        recogerHora.show();


    }

    //Esperar unos segundos antes de cerrar la activity
    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // [cerrar activity]

                getActivity().finish();

            }
        }, milisegundos);
    }

    private void mostrarSmartphone(Uri uri) {

        //Cargar el valor obtenido en el campo teléfono
        mTieTelefono.setText(getSmartphone(uri));

    }

    private String getSmartphone(Uri uri) {
        //Variables temporales para el id y el teléfono
        String id = null;
        String smartphone = null;

        // PRIMERA CONSULTA

        //Obtener el _ID del contacto

        contactCursor = getActivity().getApplicationContext().getContentResolver().query(
                uri,
                new String[]{ContactsContract.Contacts._ID},
                null,
                null,
                null);

        if(contactCursor != null && contactCursor.getCount() > 0){

            if(contactCursor.moveToFirst()) {
                id = contactCursor.getString(0);
            }

            contactCursor.close();
        }



        //SEGUNDA CONSULTA
        /*
        Sentencia WHERE para especificar que solo deseamos
        números de telefonía móvil
         */
        String selectionArgs =
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE+" = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

        /*
        Obtener el número telefónico
         */
        phoneCursor = getActivity().getApplicationContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
                selectionArgs,
                new String[] { id },
                null
        );
        if(phoneCursor != null && phoneCursor.getCount() > 0){

            if (phoneCursor.moveToFirst()) {
                smartphone = phoneCursor.getString(0);
            }

            phoneCursor.close();
        }


        return smartphone;
    }

    private void mostrarExplicacion(final int tipoPeticion, String mensaje) {

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.adb_titulo))
                .setMessage(mensaje)
                .setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //pedir permiso
                        if(tipoPeticion == PICK_CONTACT_REQUEST){
                            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION);
                        }else if(tipoPeticion == PICK_SMS_REQUEST){
                            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION);
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
    private void mensajeTituloRecordatorioExiste(Spannable mensaje){

        Snackbar snackbar = Snackbar.make(mView, mensaje, Snackbar.LENGTH_LONG);
        //Color de boton de accion
        View snackBarView = snackbar.getView();
        //Cambiando el color del texto
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackBarView.setBackgroundColor(Color.argb(255, 239, 83, 80));

        snackbar.show();

    }

    //====================================== Observadores de texto =======================================//

    private TextWatcher twTituloRecordatorio = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mTilTituloRecordatorio.setError(null);
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };


    private TextWatcher twEntidadOtros = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mTilEntidadOtros.setError(null);
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher twContenidoMensaje = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validarBotonesInfo(String.valueOf(s));
            mTilContenidoMensaje.setError(null);
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };
    private  TextWatcher twTelefono = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() > 0){
                guardarNumeroTelefono = esTelefonoValido(String.valueOf(s));
            }

        }
        @Override
        public void afterTextChanged(Editable s) { }
    };

    private TextWatcher twFecha = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mTilFecha.setError(null);
            obtenerCantidadCaracteresCampoFecha = s.length();
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher twHora = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mTilHora.setError(null);
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };

    //===============================@Override=================================//
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int idItemSelected = parent.getId();
        switch (idItemSelected){
            case R.id.sp_categorias_recordatorios:
                    mValorIdCategoria = mManagerRecordatorios
                            .getListaCategorias()
                            .get(position).getId();
                    mValorRutaImgCategoria = mManagerRecordatorios
                            .getListaCategorias()
                            .get(position).getImagen();
                    mValorTituloCategoria = mManagerRecordatorios
                            .getListaCategorias()
                            .get(position).getTituloCategoria();

                    //Almaceno el valor obtenido de protección imagen categoría
                    mValorProteccionImg =  mManagerRecordatorios
                            .getListaCategorias()
                            .get(position).getProteccion();

                    if(Utilidades.smartphone) {

                        mCtCategoriaRecordatorio.setTitle(mValorTituloCategoria);

                        if (mValorRutaImgCategoria != null){
                            if (mValorProteccionImg == 1){
                                Picasso.with(getActivity())
                                        .load(RUTA_CARPETA_ASSETS + mValorRutaImgCategoria)
                                        .error(R.drawable.ic_image_150dp)
                                        .noFade()
                                        .into(mIvImagenRecordatorio);

                            }else {
                                Picasso.with(getActivity())
                                        .load(new File(mValorRutaImgCategoria))
                                        .error(R.drawable.ic_image_150dp)
                                        .noFade()
                                        .into(mIvImagenRecordatorio);
                            }
                        }else{
                            mIvImagenRecordatorio.setImageDrawable(null);
                        }
                    }else{

                        String mTituloCategoriaFormateado;

                        if(mValorTituloCategoria.length() >= 40){
                            mTituloCategoriaFormateado = mValorTituloCategoria.substring(0, 40);
                        }else{
                            mTituloCategoriaFormateado = mValorTituloCategoria;
                        }

                        mTvTituloCategoriaRecordatorio.setText(mTituloCategoriaFormateado);
                        if (mValorRutaImgCategoria != null){
                            if (mValorProteccionImg == 1){
                                Picasso.with(getActivity().getApplicationContext())
                                        .load(RUTA_CARPETA_ASSETS + mValorRutaImgCategoria)
                                        .error(R.drawable.ic_image_150dp)
                                        .noFade()
                                        .into(mCivImagenRecordatorio);
                            }else{
                                Picasso.with(getActivity().getApplicationContext())
                                        .load(new File(mValorRutaImgCategoria))
                                        .error(R.drawable.ic_image_150dp)
                                        .noFade()
                                        .into(mCivImagenRecordatorio);
                            }
                        }else{
                            mCivImagenRecordatorio.setImageResource(R.drawable.ic_image_150dp);
                        }
                    }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int idBotonToggle = buttonView.getId();
        switch(idBotonToggle) {
            case R.id.sw_facebook:
                if(isChecked){
                    mValorFacebook = ENVIAR_ON;
                }else{
                    mValorFacebook = ENVIAR_OFF;
                }
                break;
            case R.id.sw_twitter:
                if(isChecked){
                    mValorTwitter = ENVIAR_ON;
                }else{
                    mValorTwitter = ENVIAR_OFF;
                }
                break;
            case R.id.sw_enviar_mensaje:
                if(isChecked){
                    enviarSms();
                }else{
                    noEnviarSms();
                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add_upd:
                validarDatos();
                break;
            case R.id.bt_agregar_categoria:
                abrirFormularioNuevaCategoria();
                break;
            case R.id.bt_contactos:
                accederAgendaContactos();
                break;
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            //Volver a recargar el spinner de categorías
            if(requestCode == CODIGO_RESPUESTA_CATEGORIA){
                if(data.hasExtra(AgregarCategoriaFragmento.LLAVE_RETORNO_CATEGORIA)){
                    boolean valorObtenido = data.getExtras()
                            .getBoolean(AgregarCategoriaFragmento.LLAVE_RETORNO_CATEGORIA);
                    if(valorObtenido){
                        //Recargo el spinner siempre y cuando que el valor retornado sea `true`
                        poblarSpinner();
                    }
                }
            }
            //Obtener el número de teléfono
            if(requestCode == PICK_CONTACT_REQUEST && data != null ){

                contactoUri = data.getData();
                mostrarSmartphone(contactoUri);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case READ_CONTACTS_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    accederAgendaContactos();
                }else {
                    mostrarMensaje(getString(R.string.permiso_denegado_contacto), 0);
                }
                break;
            case SEND_SMS_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    enviarSms();
                }else {
                    noEnviarSms();
                    mostrarMensaje(getString(R.string.permiso_denegado_sms), 0);
                }
                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Aquí nos aseguramos de que en la actividad se haya implementado la interfaz,
        // si el programador no la implementado se lanza el mensaje de error.
        try {
            mCrud = (InterfaceCrud) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Debe implementar las interfaces en su Activity");
        }
    }

    //Función llamada cuando el fragment es desasociada de una actividad
    @Override
    public void onDetach() {
        super.onDetach();
        mCrud = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
