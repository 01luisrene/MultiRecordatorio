package com.a01luisrene.multirecordatorio.fragmentos;


        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.TimePickerDialog;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.design.widget.CollapsingToolbarLayout;
        import android.support.design.widget.Snackbar;
        import android.support.design.widget.TextInputLayout;
        import android.support.v4.app.Fragment;
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
    public static final String ID_CATEGORIA_NULO = "nulo";
    public static int MILISEGUNDOS_ESPERA = 1500;
    public static final int CODIGO_RESPUESTA_CATEGORIA = 100;

    //Expresiones regulares
    public static final String REGEX_CARACTERES_LATINOS = "^[a-zA-Z0-9 áÁéÉíÍóÓúÚñÑüÜ]+$";
    public static final String REGEX_FECHAS = "^([0-2][0-9]|3[0-1])(\\/)(0[1-9]|1[0-2])\\2(\\d{4})$";

    //Referencias de widgets del fragmento
    Button mBotonGuardar, mBotonAgregarCategoria;
    EditText mEtTituloRecordatorio, mEtEntidadOtros, mEtTelefono, mEtContenidoMensaje, mEtFecha, mEtHora;
    TextInputLayout mTilTituloRecordatorio, mTilEntidadOtros, mTilTelefono, mTilContenidoMensaje, mTilFecha, mTilHora;
    Switch mSwFacebook, mSwTwitter, mSwEnviarMensaje;
    String mValorFacebook, mValorTwitter, mValorEnviarMensaje;
    ImageButton mIbContactos, mIbFecha, mIbHora;

    //[Combo categoria recordatorios]
    Spinner mSpinnerListaCategotegorias;
    //Variables para el combo
    ArrayList<String> comboListaCategorias;
    ArrayAdapter<CharSequence> comboAdapter;

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
        mEtTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                esTelefonoValido(String.valueOf(s));
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

        //[INICIO COMBO]
        spinner();
        //[FIN COMBO]

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

    public void spinner(){
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
                }else{
                    mValorEnviarMensaje = ENVIAR_OFF;
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_nuevo_guardar:
                validarDatos();
                break;
            case R.id.bt_agregar_categoria:
                abrirFormulario();
            case R.id.bt_contactos:
                break;
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
        }
    }

    private void abrirFormulario() {

        Intent i = new Intent(getActivity(), DetalleCategoriaActivity.class);
        startActivityForResult(i, CODIGO_RESPUESTA_CATEGORIA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == CODIGO_RESPUESTA_CATEGORIA) && (resultCode == RESULT_OK)){

            if(data.hasExtra(AgregarCategotiaRecordatorioFragmento.LLAVE_RETORNO_CATEGORIA)){
                int valorObtenido = data.getExtras()
                        .getInt(AgregarCategotiaRecordatorioFragmento.LLAVE_RETORNO_CATEGORIA);
                if(valorObtenido == AgregarCategotiaRecordatorioFragmento.VALOR_CATEGORIA_ENVIADO_RESULT){
                    //Recargo el spinner siempre y cuando tenga una respuesta OK
                    spinner();
                }
            }

        }
    }

    public void validarDatos(){
        String stTitulo = mTilTituloRecordatorio.getEditText().getText().toString();
        String stEntidadOtros = mTilEntidadOtros.getEditText().getText().toString();
        String stTelefono = mTilTelefono.getEditText().getText().toString();
        String stContenidoMensaje = mTilContenidoMensaje.getEditText().getText().toString();
        String stFecha = mTilFecha.getEditText().getText().toString();
        String stHora =  mTilHora.getEditText().getText().toString();

        boolean titulo = esTituloRecordatorioValido(stTitulo);
        boolean entidadOtros = esEntidadOtrosValido(stEntidadOtros);
        boolean telefono = esTelefonoValido(stTelefono);

        boolean fecha = esFechaValido(stFecha);

        if(titulo && entidadOtros && telefono && fecha) {

            if(!mValorIdCategoria.equals(ID_CATEGORIA_NULO)) {
                try {
                    mManagerRecordatorios.insertarRecoratorio(null,
                            mEtTituloRecordatorio.getText().toString(), //[Titulo]
                            mEtEntidadOtros.getText().toString(),       //[Entidad - Otros]
                            mValorIdCategoria,                          //[Id Categoría]
                            mEtContenidoMensaje.getText().toString(),   //[Contenido del mensaje]
                            mEtTelefono.getText().toString(),            //[Teléfono]
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
                    mostrarMensaje(getString(R.string.mensaje_agregado_satisfactoriamente), 1);

                    //Cierro la activity
                    esperarYCerrar(MILISEGUNDOS_ESPERA);
                }
            }else{
                Toast.makeText(getActivity(), getString(R.string.error_spinner_categorias), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Validar campos EditText
    private boolean esTituloRecordatorioValido(String titulo){
        Pattern patron = Pattern.compile(REGEX_CARACTERES_LATINOS);
        if(!patron.matcher(titulo).matches() || titulo.length() > 120){
            mTilTituloRecordatorio.setError(getString(R.string.error_titulo_recordatorio));
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
    private boolean esTelefonoValido(String telefono){
        if(!Patterns.PHONE.matcher(telefono).matches()){
            mTilTelefono.setError(getString(R.string.error_telefono));
            return false;
        }else{
            mTilTelefono.setError(null);
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
        final int am_pm = c.get(Calendar.AM_PM);


        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if(am_pm == Calendar.AM){
                    Toast.makeText(getActivity(), "AM", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "PM", Toast.LENGTH_SHORT).show();
                }

                String horaFormateada = (hourOfDay < 10)? "0" + String.valueOf(hourOfDay):String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 10)? "0" + String.valueOf(minute):String.valueOf(minute);

                mEtHora.setText(horaFormateada + ":" + minutoFormateado);

            }
        }, hour, minute, false);

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
