package com.a01luisrene.multirecordatorio.fragmentos;


        import android.Manifest;
        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.TimePickerDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.graphics.Color;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Handler;
        import android.provider.ContactsContract;
        import android.support.annotation.NonNull;
        import android.support.design.widget.CollapsingToolbarLayout;
        import android.support.design.widget.Snackbar;
        import android.support.design.widget.TextInputLayout;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.Fragment;
        import android.support.v4.content.ContextCompat;
        import android.provider.ContactsContract.CommonDataKinds.Phone;
        import android.support.v7.app.AlertDialog;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Patterns;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CompoundButton;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;
        import static android.app.Activity.RESULT_OK;
        import com.a01luisrene.multirecordatorio.R;
        import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
        import com.a01luisrene.multirecordatorio.ui.DetalleCategoriaActivity;
        import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
        import com.squareup.picasso.Picasso;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.regex.Pattern;

        import de.hdodenhof.circleimageview.CircleImageView;

public class AgregarRecordatorioFragmento extends Fragment
        implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener{

    //Constantes
    public static final String ESTADO_RECORDATORIO = "1";
    public static final String ENVIAR_ON = "1";
    public static final String ENVIAR_OFF = "0";
    public static final String VI_CARACTERES_INFO = "140"; //VI = valor inical
    public static final String ID_CATEGORIA_NULO = "nulo";
    public static int MILISEGUNDOS_ESPERA = 1500;
    public static final int CODIGO_RESPUESTA_CATEGORIA = 100;
    public static final int PICK_CONTACT_REQUEST = 101;
    private static final int READ_CONTACTS_PERMISSION = 102;

    //Expresiones regulares
    public static final String REGEX_CARACTERES_LATINOS = "^[a-zA-Z0-9 áÁéÉíÍóÓúÚñÑüÜ]*$";
    public static final String REGEX_FECHAS = "^([0-2][0-9]|3[0-1])(\\/)(0[1-9]|1[0-2])\\2(\\d{4})$";
    public static final String REGEX_HORAS = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

    //Referencias de widgets del fragmento
    Button mBotonGuardar, mBotonAgregarCategoria;
    EditText mEtTituloRecordatorio, mEtEntidadOtros, mEtTelefono, mEtContenidoMensaje, mEtFecha, mEtHora;
    TextInputLayout mTilTituloRecordatorio, mTilEntidadOtros, mTilTelefono, mTilContenidoMensaje, mTilFecha, mTilHora;
    Switch mSwFacebook, mSwTwitter, mSwEnviarMensaje;
    String mValorFacebook, mValorTwitter, mValorEnviarMensaje;
    ImageButton mIbContactos, mIbFecha, mIbHora, mIbFacebookInfo, mIbTwitterInfo, mIbMensajeInfo;
    TextView mTvTwitterInfo;

    //[Combo categoria recordatorios]
    Spinner mSpinnerListaCategotegorias;
    //Variables para el combo
    ArrayList<String> comboListaCategorias;
    ArrayAdapter<CharSequence> comboAdapter;

    //Obtener número de los contactos del phone
    Cursor contactCursor, phoneCursor;
    Uri contactoUri;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Referencia a manager de SQLite
    DataBaseManagerRecordatorios mManagerRecordatorios;

    //Referencias de widgets que se encuentran en activity detalle
    CollapsingToolbarLayout mCtRecordatorio;
    ImageView mIvImagenRecordatorio;
    String  mValorIdCategoria, mValorImagenCategoria, mValorTituloCategoria;
    Activity activity;

    //Referencias de widgets que se encuentran en layout toolbar_detalle.xml
    private CircleImageView mCivImagenRecordatorio;
    private TextView mTvTituloCategoriaRecordatorio;


    public AgregarRecordatorioFragmento() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_agregar_recordatorio, container, false);

        //Asignamos nuestro manager que contiene nuestros metodos CRUD
        mManagerRecordatorios = new DataBaseManagerRecordatorios(getContext());

        if(Utilidades.smartphone) {
            activity = this.getActivity();
            //Widgets de la activity Detalle Recordatorio
            mIvImagenRecordatorio = (ImageView) activity.findViewById(R.id.iv_cover);
            mCtRecordatorio = (CollapsingToolbarLayout) activity.findViewById(R.id.ct_recordatorio);
        }else{
            //Widgets del layout toolbar_detelle.xml
            mCivImagenRecordatorio = (CircleImageView) v.findViewById(R.id.civ_nuevo_categoria_recordatorio);
            mTvTituloCategoriaRecordatorio = (TextView) v.findViewById(R.id.tv_nuevo_titulo_categoria_recordatorio);
        }

        //Spinner
        mSpinnerListaCategotegorias = (Spinner) v.findViewById(R.id.sp_categorias_recordatorios);

        //EditTex
        mEtTituloRecordatorio = (EditText) v.findViewById(R.id.et_nuevo_titulo_recordatorio);
        mEtEntidadOtros = (EditText) v.findViewById(R.id.et_nuevo_entidad_otros);
        mEtTelefono = (EditText) v.findViewById(R.id.et_nuevo_telefono);
        mEtContenidoMensaje = (EditText) v.findViewById(R.id.et_nuevo_contenido_mensaje);
        mEtFecha = (EditText) v.findViewById(R.id.et_nuevo_fecha);
        mEtHora = (EditText) v.findViewById(R.id.et_nuevo_hora);

        //TextView
        mTvTwitterInfo = (TextView) v.findViewById(R.id.tv_twitter_info);

        //TextInputLayout
        mTilTituloRecordatorio = (TextInputLayout)  v.findViewById(R.id.til_nuevo_titulo_recordatorio);
        mTilEntidadOtros = (TextInputLayout) v.findViewById(R.id.til_nuevo_entidad_otros);
        mTilTelefono = (TextInputLayout) v.findViewById(R.id.til_nuevo_telefono);
        mTilContenidoMensaje = (TextInputLayout) v.findViewById(R.id.til_nuevo_contenido_mensaje);
        mTilFecha = (TextInputLayout) v.findViewById(R.id.til_nuevo_fecha);
        mTilHora = (TextInputLayout) v.findViewById(R.id.til_nuevo_hora);

        //Switch
        mSwFacebook = (Switch) v.findViewById(R.id.sw_facebook);
        mSwTwitter = (Switch) v.findViewById(R.id.sw_twitter);
        mSwEnviarMensaje = (Switch) v.findViewById(R.id.sw_enviar_mensaje);

        mValorEnviarMensaje = ENVIAR_OFF;
        mValorFacebook = ENVIAR_OFF;
        mValorTwitter = ENVIAR_OFF;

        //Botones
        mBotonGuardar = (Button) v.findViewById(R.id.bt_nuevo_guardar);
        mBotonAgregarCategoria = (Button) v.findViewById(R.id.bt_agregar_categoria);

        //Botón con imagenes
        mIbFecha = (ImageButton) v.findViewById(R.id.ib_obtener_fecha);
        mIbHora = (ImageButton) v.findViewById(R.id.ib_obtener_hora);
        mIbContactos = (ImageButton) v.findViewById(R.id.bt_contactos);
        mIbFacebookInfo = (ImageButton) v.findViewById(R.id.ib_facebook_info);
        mIbTwitterInfo = (ImageButton) v.findViewById(R.id.ib_twitter_info);
        mIbMensajeInfo = (ImageButton) v.findViewById(R.id.ib_mensaje_info);

        //Limpiar los EditText
        mEtTituloRecordatorio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                esTituloRecordatorioValido(String.valueOf(s));
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mEtEntidadOtros.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                esEntidadOtrosValido(String.valueOf(s));
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mEtContenidoMensaje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validarBotonesInfo(String.valueOf(s));
                esContenidoMensajeValido(String.valueOf(s));}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        mEtTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validarTelefono(String.valueOf(s));
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mEtFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                esFechaValido(String.valueOf(s));}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        mEtHora.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                esHoraValido(String.valueOf(s));
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        //[INICIO COMBO]
        poblarSpinner();
        //[FIN COMBO]

        //Muestro la cantidad de textos disponibles para twitter
        mTvTwitterInfo.setText(VI_CARACTERES_INFO);

        mSwFacebook.setOnCheckedChangeListener(this);
        mSwTwitter.setOnCheckedChangeListener(this);
        mSwEnviarMensaje.setOnCheckedChangeListener(this);

        mBotonGuardar.setOnClickListener(this);
        mBotonAgregarCategoria.setOnClickListener(this);

        mIbContactos.setOnClickListener(this);
        mIbFecha.setOnClickListener(this);
        mIbHora.setOnClickListener(this);

        return v;

    }

    public void poblarSpinner(){
        comboListaCategorias = new ArrayList<String>();
        comboListaCategorias.add(getString(R.string.selecciona_categoria_spinner));
        int sizeListaCat = mManagerRecordatorios.getListaCategoriasSpinner().size();
        for(int i = 0; i < sizeListaCat; i++){
            comboListaCategorias.add(mManagerRecordatorios
                    .getListaCategoriasSpinner().get(i).getCategorioRecordatorio());
        }

        comboAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, comboListaCategorias );
        mSpinnerListaCategotegorias.setAdapter(comboAdapter);
        mSpinnerListaCategotegorias.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int idSpinner = parent.getId();

        switch (idSpinner){
            case R.id.sp_categorias_recordatorios:
                if(position != 0) {

                    mValorIdCategoria = mManagerRecordatorios
                            .getListaCategoriaRecordatorios()
                            .get(position-1).getId();
                    mValorImagenCategoria = mManagerRecordatorios
                            .getListaCategoriaRecordatorios()
                            .get(position-1).getImagen();
                    mValorTituloCategoria = mManagerRecordatorios
                            .getListaCategoriaRecordatorios()
                            .get(position-1).getCategorioRecordatorio();

                    if(Utilidades.smartphone) {

                        mCtRecordatorio.setTitle(mValorTituloCategoria);
                        if (mValorImagenCategoria != null){
                            Picasso.with(getActivity())
                                    .load(new File(mValorImagenCategoria))
                                    .noFade()
                                    .into(mIvImagenRecordatorio);
                        }else{
                            mIvImagenRecordatorio.setImageDrawable(null);
                        }
                    }else{
                        mTvTituloCategoriaRecordatorio.setText(mValorTituloCategoria);
                        if (mValorImagenCategoria != null){
                            Picasso.with(getActivity().getApplicationContext())
                                    .load(new File(mValorImagenCategoria))
                                    .noFade()
                                    .into(mCivImagenRecordatorio);
                        }else{
                            //mCivImagenRecordatorio.setImageDrawable(R.drawable.ic_image_150dp);
                            mCivImagenRecordatorio.setImageResource(R.drawable.ic_image_150dp);
                        }
                    }

                }else{
                    //Colocar el valor de id categoría en nulo
                    mValorIdCategoria = ID_CATEGORIA_NULO;

                    if(Utilidades.smartphone) {
                        mCtRecordatorio.setTitle(getString(R.string.agregar_recordatorio));
                        mIvImagenRecordatorio.setImageDrawable(null);
                    }else{
                        mTvTituloCategoriaRecordatorio.setText(getString(R.string.agregar_recordatorio));
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
                    mValorEnviarMensaje = ENVIAR_ON;
                    mIbContactos.setVisibility(View.VISIBLE);
                    mTilTelefono.setVisibility(View.VISIBLE);
                    //Validar télefono solo si el switch es on
                    String telefono = mEtTelefono.getText().toString();
                    validarTelefono(telefono);

                }else{
                    mValorEnviarMensaje = ENVIAR_OFF;
                    mIbContactos.setVisibility(View.GONE);
                    mTilTelefono.setVisibility(View.GONE);
                    mEtTelefono.setText(null);
                }
                break;
        }
    }

    public void validarTelefono(String telefono){
        if(!Patterns.PHONE.matcher(telefono).matches()){
            mTilTelefono.setError(getString(R.string.error_telefono));
        }else{
            mTilTelefono.setError(null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_nuevo_guardar:
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

    private void abrirFormularioNuevaCategoria() {

        Intent i = new Intent(getActivity(), DetalleCategoriaActivity.class);
        startActivityForResult(i, CODIGO_RESPUESTA_CATEGORIA);

    }

    private void accederAgendaContactos(){
        //si la API 23 a mas
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Habilitar permisos para la version de API 23 a mas
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                //solicitar permiso
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
                    mostrarExplicacion(PICK_CONTACT_REQUEST);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            //Volver a recargar el spinner de categorías
            if(requestCode == CODIGO_RESPUESTA_CATEGORIA){
                if(data.hasExtra(AgregarCategotiaRecordatorioFragmento.LLAVE_RETORNO_CATEGORIA)){
                    boolean valorObtenido = data.getExtras()
                            .getBoolean(AgregarCategotiaRecordatorioFragmento.LLAVE_RETORNO_CATEGORIA);
                    if(valorObtenido){
                        //Recargo el spinner siempre y cuando que el valor retornado sea `true`
                        poblarSpinner();
                    }
                }
            }
            //Obtener el número de teléfono
            if(requestCode == PICK_CONTACT_REQUEST ){

                contactoUri = data.getData();

                mostrarSmartphone(contactoUri);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(requestCode == READ_CONTACTS_PERMISSION) {
                accederAgendaContactos();
            }
        }else {
            mostrarMensaje(getString(R.string.permiso_denegado_contacto), 0);
        }
    }

    public void validarDatos(){
        String stTitulo = mTilTituloRecordatorio.getEditText().getText().toString();
        String stEntidadOtros = mTilEntidadOtros.getEditText().getText().toString();
        String stContenidoMensaje = mTilContenidoMensaje.getEditText().getText().toString();
        String stFecha = mTilFecha.getEditText().getText().toString();
        String stHora =  mTilHora.getEditText().getText().toString();

        boolean titulo = esTituloRecordatorioValido(stTitulo);
        boolean entidadOtros = esEntidadOtrosValido(stEntidadOtros);
        boolean contenidoMensaje = esContenidoMensajeValido(stContenidoMensaje);
        boolean fecha = esFechaValido(stFecha);
        boolean hora = esHoraValido(stHora);

        if(titulo && entidadOtros && contenidoMensaje && fecha && hora) {

            if(!mValorIdCategoria.equals(ID_CATEGORIA_NULO)) {
                try {
                    mManagerRecordatorios.insertarRecoratorio(null,
                            mEtTituloRecordatorio.getText().toString(), //[Titulo]
                            mEtEntidadOtros.getText().toString(),       //[Entidad - Otros]
                            mValorIdCategoria,                          //[Id Categoría]
                            mEtContenidoMensaje.getText().toString(),   //[Contenido del mensaje]
                            mEtTelefono.getText().toString(),           //[Teléfono]
                            mValorEnviarMensaje,                        //[Envio mesaje]
                            mValorFacebook,                             //[Publicar en facebook]
                            mValorTwitter,                              //[Publicar en twitter]
                            Utilidades.fechaHora(),                     //[Fecha creación]
                            mEtFecha.getText().toString(),              //[Fecha del recordatorio]
                            mEtHora.getText().toString(),               //[Hora del recordatorio]
                            ESTADO_RECORDATORIO);



                } catch (Exception e) {
                    //Mensaje de error
                    mostrarMensaje(getString(R.string.error_al_guardar), 0);
                } finally {

                    //Mensaje de registro guardado con exito
                    Toast.makeText(getActivity(), getString(R.string.mensaje_agregado_satisfactoriamente), Toast.LENGTH_SHORT).show();

                    //Cierro la activity
                    esperarYCerrar(MILISEGUNDOS_ESPERA);
                }
            }else{
                Toast.makeText(getActivity(), getString(R.string.error_spinner_categorias), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(), getString(R.string.error_completar_campos_recordatorios), Toast.LENGTH_SHORT).show();
        }
    }

    //Validar campos EditText
    private boolean esTituloRecordatorioValido(String titulo){
        Pattern patron = Pattern.compile(REGEX_CARACTERES_LATINOS);
        if(!patron.matcher(titulo).matches() || titulo.length() > 120){
            mTilTituloRecordatorio.setError(getString(R.string.error_titulo_recordatorio));
            return false;
        }else if(titulo.length() < 3){
            mTilTituloRecordatorio.setError(getString(R.string.error_titulo_recordatorio_min_char));
            return false;
        }else{
            mTilTituloRecordatorio.setError(null);
        }
        return true;
    }
    private boolean esEntidadOtrosValido(String entidadOtros) {
        Pattern patron = Pattern.compile(REGEX_CARACTERES_LATINOS);
        if (!patron.matcher(entidadOtros).matches() || entidadOtros.length() > 100) {
            mTilEntidadOtros.setError(getString(R.string.error_entidad_otros));
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

        mTvTwitterInfo.setText(String.valueOf(caracteresTwitter));

        if(numeroCaracteres.length() > 140){
            mIbTwitterInfo.setBackground(drawable_invalido);
            mSwTwitter.setEnabled(false);
            mSwTwitter.setChecked(false);
        }else{
            mIbTwitterInfo.setBackground(drawable_valido);
            mSwTwitter.setEnabled(true);
        }

    }
    private boolean esContenidoMensajeValido(String contenidoMensaje) {
        if(contenidoMensaje.length() < 8){
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

    private boolean esFechaValido(String fecha){
        Pattern patron = Pattern.compile(REGEX_FECHAS);
        if (!patron.matcher(fecha).matches()){
            mTilFecha.setError(getString(R.string.error_fecha));
            return false;
        }else{
            mTilFecha.setError(null);
        }
        return true;
    }

    private boolean esHoraValido(String hora){
        Pattern patron = Pattern.compile(REGEX_HORAS);
        if (!patron.matcher(hora).matches()){
            mTilHora.setError(getString(R.string.error_fecha));
            return false;
        }else{
            mTilHora.setError(null);
        }
        return true;
    }

    public void obtenerFecha(){

        final int mes = c.get(Calendar.MONTH);
        final int dia = c.get(Calendar.DAY_OF_MONTH);
        final int anio = c.get(Calendar.YEAR);

        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);

                mEtFecha.setText(diaFormateado + "/" + mesFormateado +"/"+ year);

            }
        },anio, mes, dia);

        recogerFecha.show();

    }
    public void obtenerHora(){

        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada = (hourOfDay < 10)? "0" + String.valueOf(hourOfDay):String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 10)? "0" + String.valueOf(minute):String.valueOf(minute);

                mEtHora.setText(horaFormateada + ":" + minutoFormateado);

            }

        }, hour, minute, true);

        recogerHora.show();

    }


    //Esperar unos segundos antes de cerrar la activity
    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                // [cerrar activity]
                //activity.finish();

            }
        }, milisegundos);
    }

    private void mostrarSmartphone(Uri uri) {

        //Cargar el valor obtenido en el campo teléfono
        mEtTelefono.setText(getSmartphone(uri));
    }

    private String getSmartphone(Uri uri) {
        //Variables temporales para el id y el teléfono
        String id = null;
        String smartphone = null;

        /************* PRIMERA CONSULTA ************/

        //Obtener el _ID del contacto

        contactCursor = getActivity().getApplicationContext().getContentResolver().query(
                uri,
                new String[]{ContactsContract.Contacts._ID},
                null,
                null,
                null);


        if (contactCursor.moveToFirst()) {
            id = contactCursor.getString(0);
        }
        contactCursor.close();

        /************* SEGUNDA CONSULTA ************/
        /*
        Sentencia WHERE para especificar que solo deseamos
        números de telefonía móvil
         */
        String selectionArgs =
                Phone.CONTACT_ID + " = ? AND " +
                Phone.TYPE+" = " +
                Phone.TYPE_MOBILE;

        /*
        Obtener el número telefónico
         */
        phoneCursor = getActivity().getApplicationContext().getContentResolver().query(
                Phone.CONTENT_URI,
                new String[] { Phone.NUMBER },
                selectionArgs,
                new String[] { id },
                null
        );
        if (phoneCursor.moveToFirst()) {
            smartphone = phoneCursor.getString(0);
        }
        phoneCursor.close();

        return smartphone;
    }

    private void mostrarExplicacion(final int tipoPeticion) {

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.adb_titulo))
                .setMessage(getString(R.string.adb_mensaje_contacto))
                .setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //pedir permiso
                        if(tipoPeticion == PICK_CONTACT_REQUEST){
                            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION);
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

        Snackbar snackbar = Snackbar.make(getView(), mensaje, Snackbar.LENGTH_LONG);
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
}
