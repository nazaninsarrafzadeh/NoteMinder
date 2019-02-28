package com.example.nazanin.notepad.controller.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.nazanin.notepad.R;
import com.example.nazanin.notepad.model.dao.CheckListDbHelper;
import com.example.nazanin.notepad.model.dto.CheckList;

import java.util.ArrayList;
import java.util.LinkedList;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.MyViewHolder> {

    private ArrayList<CheckList> checkLists;
    private Context context;

    public CheckListAdapter(ArrayList<CheckList> checkLists,Context context){
        this.checkLists=checkLists;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.check_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CheckList checkList=checkLists.get(position);
        holder.checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.checkBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(), "font/byekan.ttf");
        holder.checkBox.setTypeface(font);
        holder.checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        holder.checkBox.setText(checkList.getWhatToDo());
        holder.checkBox.setChecked(checkList.isDone());
    }

    @Override
    public int getItemCount() {
    //    Log.d("ff", "getItemCount: "+checkLists.size());
        return checkLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.task);
            checkBox.setOnCheckedChangeListener(this);

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position=getAdapterPosition();
            CheckList checkList=checkLists.get(position);
         //   Toast.makeText(context,String.valueOf(position+1),Toast.LENGTH_SHORT).show();
            checkLists.remove(position);
            notifyItemRemoved(position);
            CheckListDbHelper checkListDbHelper=new CheckListDbHelper(context);
            checkListDbHelper.delete(checkList.getId());
        }
    }
}
