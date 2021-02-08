package com.example.appcaremachtas.adaptors;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcaremachtas.R;
import com.example.appcaremachtas.database.ApplicationDatabase;
import com.example.appcaremachtas.model.Person;

import java.util.List;

public class PersonAdaptor extends RecyclerView.Adapter<PersonAdaptor.MyViewHolder> {
    private Context context;
    private List<Person> mPersonList;

    public PersonAdaptor(List<Person> persons, Context context) {
        this.mPersonList = persons;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_adapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdaptor.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(mPersonList.get(i).getName());
        myViewHolder.dept.setText(mPersonList.get(i).getDept());
        myViewHolder.id.setText(""+mPersonList.get(i).getUserUId());
        myViewHolder.company.setText(mPersonList.get(i).getCompany());
        Log.d("DATA",mPersonList.get(i).getCompany());
    }

    @Override
    public int getItemCount() {
        if (mPersonList == null) {
            return 0;
        }
        return mPersonList.size();

    }

    public void setTasks(List<Person> personList) {
        mPersonList = personList;
        notifyDataSetChanged();
    }

    public List<Person> getTasks() {

        return mPersonList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, id, company, dept;
        ImageView editImage;
        ApplicationDatabase mDb;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = ApplicationDatabase.getInstance(context);
            name = itemView.findViewById(R.id.person_name);
            id = itemView.findViewById(R.id.person_id);
            dept = itemView.findViewById(R.id.person_dept);
            company = itemView.findViewById(R.id.person_company);


        }
    }
}
