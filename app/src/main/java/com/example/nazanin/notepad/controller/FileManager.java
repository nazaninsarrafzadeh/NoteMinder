package com.example.nazanin.notepad.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.nazanin.notepad.model.dto.Note;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileManager {

    private ArrayList<Note> notesList=new ArrayList<>();

    public String saveFile(String filename,String note,Context context){
        String path="";
        try {
            FileOutputStream stream=context.openFileOutput(filename,0);
            OutputStreamWriter writer=new OutputStreamWriter(stream);
            writer.write(note);
            writer.close();
            path=context.getFileStreamPath(filename).getPath();
        //    Toast.makeText(context,path,Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public void editFile(String filename,String note,Context context){
        try {
            File directory=context.getFilesDir();
            File file=new File(directory,filename);
        //    FileWriter writer=new FileWriter(file,true);
            FileOutputStream stream=new FileOutputStream(file);
            OutputStreamWriter writer=new OutputStreamWriter(stream);
            writer.write(note);
            writer.close();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String openFile(String filename,Context context){
        String wholeNote="";
        if (checkFileExistance(filename,context)){
         //   Toast.makeText(context,filename,Toast.LENGTH_SHORT).show();
            try {
                InputStream stream=context.openFileInput(filename);
                InputStreamReader reader=new InputStreamReader(stream);
                BufferedReader bufferReader=new BufferedReader(reader);
                String text="";
                StringBuilder stringBuilder=new StringBuilder();
                while ((text=bufferReader.readLine())!=null){
                    stringBuilder.append(text+"\n");
                }
                wholeNote=stringBuilder.toString();
                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wholeNote;
    }

    private boolean checkFileExistance(String filename,Context context){
        File file=context.getFileStreamPath(filename);
        return file.exists();
    }

    public ArrayList<Note> prepareNotes(Context context){
      //  FileManager fileManager=new FileManager();
        File directory=context.getFilesDir();
        File[] files=directory.listFiles();
        for(File f:files){
            String filename=f.getName();
            Note note=new Note();
            note.setTitle(filename);
            note.setText(openFile(filename,context));
            notesList.add(note);
        }
     //
        //Toast.makeText(context,String.valueOf(notesList.size()),Toast.LENGTH_SHORT).show();
        return notesList;
    }

    public boolean delete(String fileName,Context context){
        File directory=context.getFilesDir();
        File file=new File(directory,fileName);
        boolean isDeleted=file.delete();
        return isDeleted;
    }


}
