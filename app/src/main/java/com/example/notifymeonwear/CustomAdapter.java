package com.example.notifymeonwear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    private ArrayList<TaskClass> food;
    private Context context;
    private TextView tvName, tvDescription;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TaskClass> objects) {
        super(context, resource, objects);

        food = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_listvoew, parent, false);
        tvName = convertView.findViewById(R.id.textViewName);
        tvDescription = convertView.findViewById(R.id.textViewDescription);

        TaskClass currentFood = food.get(position);

        tvName.setText(currentFood.getName());
        tvDescription.setText(currentFood.getDescription());

        return convertView;


    }
}
