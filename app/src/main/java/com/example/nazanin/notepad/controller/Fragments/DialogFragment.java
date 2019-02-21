package com.example.nazanin.notepad.controller.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nazanin.notepad.R;
import com.example.nazanin.notepad.controller.FileManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends AppCompatDialogFragment {



    private TextView ask;
    private OnCallBackListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.fragment_dialog,null);
        ask=view.findViewById(R.id.ask);

            ask.setText("آیا میخواهید این یادداشت را حذف کنید؟");


        builder.setView(view).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               boolean accepted=true;
               listener.decide(accepted);

            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener= (OnCallBackListener) context;
    }

    public interface OnCallBackListener{

        void decide(boolean accepted);
    }


}
