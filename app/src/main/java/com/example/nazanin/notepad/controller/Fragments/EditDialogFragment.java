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

/**
 * A simple {@link Fragment} subclass.
 */
public class EditDialogFragment extends AppCompatDialogFragment {


    private TextView ask;
    private OnCallBackEditListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.fragment_edit_dialog,null);
        ask=view.findViewById(R.id.ask);

        ask.setText("تغییرات اعمال شده ذخیره شوند؟");


        builder.setView(view).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.confirmChanges(false);
            }
        }).setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean accepted=true;
                listener.confirmChanges(accepted);

            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener= (OnCallBackEditListener) context;
    }

    public interface OnCallBackEditListener{

        void confirmChanges(boolean accepted);
    }
//
//    @Override
//    public void onCancel(DialogInterface dialog) {
//        super.onCancel(dialog);
//        listener.confirmChanges(false);
//    }
}
