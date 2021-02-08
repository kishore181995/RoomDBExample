package com.example.appcaremachtas.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.appcaremachtas.R;
import com.example.appcaremachtas.constants.Constants;
import com.example.appcaremachtas.database.ApplicationDatabase;
import com.example.appcaremachtas.database.Executors;
import com.example.appcaremachtas.model.Person;

public class MainFragment extends Fragment {

    EditText name, id, dept, company;
    TextView save;

    int mPersonId;
    Intent intent;
    private ApplicationDatabase mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        setUPUIData(view);
        intent = getActivity().getIntent();
        mDb = ApplicationDatabase.getInstance(getActivity().getApplicationContext());
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            save.setText("Update");

            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);

            Executors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Person person = mDb.personDao().loadPersonById(mPersonId);
                    populateUI(person);
                }
            });


        }

        return view;

    }

    private void populateUI(Person person) {
        if (person == null) {
            return;
        }

        name.setText(person.getName());
        id.setText(person.getId());
        dept.setText(person.getDept());
        company.setText(person.getCompany());
    }

    private void setUPUIData(View view) {

        name = view.findViewById(R.id.persone_name);
        id = view.findViewById(R.id.persone_id);
        company = view.findViewById(R.id.persone_company);
        dept = view.findViewById(R.id.persone_dept);
        save = view.findViewById(R.id.save_data_tv);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length()>0)
                {
                    if (id.getText().toString().length()>0)
                    {
                        if (dept.getText().toString().length()>0)
                        {
                            if (company.getText().toString().length()>0)
                            {
                                insertData();
                            }else{
                                Toast.makeText(getActivity(),"Enter company",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(),"Enter department",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(),"Enter ID",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Enter user name",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void insertData() {
        final Person person = new Person(
                id.getText().toString(),
                name.getText().toString(),
                dept.getText().toString(),
                company.getText().toString(), id.getText().toString());

        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                    mDb.personDao().insertPerson(person);
                    Log.d("INSERTING","DATA");
                } else {
                    person.setId(mPersonId);
                    mDb.personDao().updatePerson(person);
                }
//                getActivity().finish();
                id.setText("");
                name.setText("");
                dept.setText("");
                company.setText("");
                loadFragment();
            }
        });
        

    }

    private void loadFragment() {
        FragmentUserList fragment2 = new FragmentUserList();
        getFragmentManager().beginTransaction().replace(R.id.frame, fragment2).addToBackStack("my_fragment").commit();

    }

    public boolean allowBackPressed() {
        return true;
    }
}
