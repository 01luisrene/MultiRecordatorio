package com.a01luisrene.multirecordatorio.fragmentos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cover extends Fragment {

    Activity activity;
    CircleImageView mLogoApp;
    TextView mNombreApp;
    ImageButton mIbEliminar, mIbEditar;

    public Cover() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this.getActivity();
        mLogoApp = (CircleImageView) activity.findViewById(R.id.civ_toolbar);
        mNombreApp = (TextView) activity.findViewById(R.id.tv_titulo_toolbar);
        mIbEditar = (ImageButton) activity.findViewById(R.id.ib_editar_toolbar);
        mIbEliminar = (ImageButton) activity.findViewById(R.id.ib_eliminar_toolbar);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cover, container, false);
        if(!Utilidades.smartphone){
            mLogoApp.setImageResource(R.drawable.ic_image_150dp);

            mNombreApp.setText(getString(R.string.app_name));
            mIbEditar.setVisibility(View.INVISIBLE);
            mIbEliminar.setVisibility(View.INVISIBLE);
        }

        return v;
    }


}
