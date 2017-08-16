package com.a01luisrene.multirecordatorio.fragmentos;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class AgregarRecordatorioFragmento extends Fragment implements View.OnClickListener{

    //Constantes
    public static int MILISEGUNDOS_ESPERA = 1500;
    public static final String ESTADO_INICIAL_RECORDATORIO = "1";

    //Referencias de widgets del fragmento
    private Button mBotonGuardar, mBotonActualizar, mBotonContactos;
    private EditText mEtTituloRecordatorio, mEtEntidadOtros, mEtTelefono, mEtContenidoMensaje, mEtFecha, mEtHora;
    private Switch mSwFacebook, mSwTwitter, mSwEnviarMensaje;
    private String mValorFacebook, mValorTwitter, mValorEnviarMensaje;
    private ImageButton mIbFecha, mIbHora;

    //Referencia a manager de SQLite
    private DataBaseManagerRecordatorios mManagerRecordatorios;

    //Referencias de widgets que se encuentran en activity detalle
    private CollapsingToolbarLayout mCtRecordatorio;
    private ImageView mIvImagenRecordatorio;
    private String  mValorIdCategoria, mValorImagenCategoria, mValorTituloCategoria;
    Activity activity;

    //[Combo categoria recordatorios]
    private Spinner mSpinnerListaCategotegorias;
    //Variables para el combo
    ArrayList<String> comboListaCategorias;
    ArrayAdapter<CharSequence> comboAdapter;



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

        //Switch
        mSwFacebook = (Switch) v.findViewById(R.id.sw_facebook);
        mSwTwitter = (Switch) v.findViewById(R.id.sw_twitter);
        mSwEnviarMensaje = (Switch) v.findViewById(R.id.sw_enviar_mensaje);

        mValorEnviarMensaje = "0";
        mValorFacebook = "0";
        mValorTwitter = "0";

        //Botones
        mBotonGuardar = (Button) v.findViewById(R.id.bt_nuevo_guardar);
        mBotonActualizar = (Button) v.findViewById(R.id.bt_nuevo_actualizar);
        mBotonContactos = (Button) v.findViewById(R.id.bt_contactos);

        //Botón con imagenes
        mIbFecha = (ImageButton) v.findViewById(R.id.ib_obtener_fecha);
        mIbHora = (ImageButton) v.findViewById(R.id.ib_obtener_hora);

        //[INICIO COMBO]
        comboListaCategorias = new ArrayList<String>();
        comboListaCategorias.add("Seleccione:");
        for(int i = 0; i < mManagerRecordatorios.getListaCategoriaRecordatorios().size(); i++){
            comboListaCategorias.add(mManagerRecordatorios.getListaCategoriaRecordatorios().get(i).getCategorioRecordatorio());
        }

        comboAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, comboListaCategorias);

        mSpinnerListaCategotegorias.setAdapter(comboAdapter);

        mSpinnerListaCategotegorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int posicionReal = position - 1;

                if(posicionReal >= 0) {

                    mValorIdCategoria = mManagerRecordatorios.getListaCategoriaRecordatorios().get(posicionReal).getId();
                    mValorImagenCategoria = mManagerRecordatorios.getListaCategoriaRecordatorios().get(posicionReal).getImagen();
                    mValorTituloCategoria = mManagerRecordatorios.getListaCategoriaRecordatorios().get(posicionReal).getCategorioRecordatorio();

                    if(Utilidades.smartphone) {

                        mCtRecordatorio.setTitle(mValorTituloCategoria);
                        if (mValorImagenCategoria != null){
                            Picasso.with(getContext())
                                    .load(new File(mValorImagenCategoria))
                                    .into(mIvImagenRecordatorio);
                        }else{
                            mIvImagenRecordatorio.setImageDrawable(null);
                        }
                    }

                }else{

                    if(Utilidades.smartphone) {
                        mCtRecordatorio.setTitle(getString(R.string.agregar_recordatorio));
                        mIvImagenRecordatorio.setImageDrawable(null);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //[FIN COMBO]
        mSwFacebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mValorFacebook = "1";
                }else{
                    mValorFacebook = "0";
                }
            }
        });
        mSwTwitter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mValorTwitter = "1";
                }else{
                    mValorTwitter = "0";
                }
            }
        });
        mSwEnviarMensaje.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mValorEnviarMensaje = "1";
                }else{
                    mValorEnviarMensaje = "0";
                }
            }
        });

        mBotonGuardar.setOnClickListener(this);
        mBotonActualizar.setOnClickListener(this);
        mBotonContactos.setOnClickListener(this);

        mIbFecha.setOnClickListener(this);
        mIbHora.setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_nuevo_guardar:
                validarDatos();
                break;
            case R.id.bt_nuevo_actualizar:
                break;
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

    public void validarDatos(){

        Toast.makeText(activity, "Id Categoría:" + mValorIdCategoria +" - " +mValorFacebook, Toast.LENGTH_SHORT).show();
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
                    "2017-08-15",                               //[Fecha del recordatorio]
                    ESTADO_INICIAL_RECORDATORIO);
        }catch (Exception e){
            //Mensaje de error
            mostrarMensaje(getString(R.string.error_al_guardar), 0);
        }finally {

            //Mensaje de registro guardado con exito
            mostrarMensaje(getString(R.string.mensaje_agregado_satisfactoriamente), 1);

            //Cierro la activity
            esperarYCerrar(MILISEGUNDOS_ESPERA);
        }
    }

    public void obtenerFecha(){
        final Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int anio = c.get(Calendar.YEAR);

        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);

                mEtFecha.setText(diaFormateado + "/" + mesFormateado +"/"+ year);

            }
        }, dia, mes, anio);

        recogerFecha.show();

    }
    public void obtenerHora(){

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerdialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada = (hourOfDay < 10)? "0" + String.valueOf(hourOfDay):String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 10)? "0" + String.valueOf(minute):String.valueOf(minute);

                mEtHora.setText(horaFormateada + ":" + minutoFormateado);

            }
        },hour, minute, false);

        timePickerdialog.show();

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
