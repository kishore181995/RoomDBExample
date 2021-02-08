package com.example.appcaremachtas.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcaremachtas.R;
import com.example.appcaremachtas.adaptors.PersonAdaptor;
import com.example.appcaremachtas.database.ApplicationDatabase;
import com.example.appcaremachtas.database.Executors;
import com.example.appcaremachtas.model.Person;

import java.util.List;

public class FragmentUserList extends Fragment {

    RecyclerView mRecylerView;
    private ApplicationDatabase mDb;
    private PersonAdaptor mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.users_list_screen, container, false);
        mRecylerView = view.findViewById(R.id.userRv);
        mDb = ApplicationDatabase.getInstance(getActivity().getApplicationContext());

        setData(view);
        return view;

    }

    private void setData(View view) {
        retriveData();
    }

    private void retriveData() {
            Executors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final List<Person> persons = mDb.personDao().loadAllPersons();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("SIZE", String.valueOf(persons.size()));
                            PersonAdaptor personAdaptor = new PersonAdaptor(persons,getActivity());
                            mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            mRecylerView.setAdapter(personAdaptor);
                        }
                    });
                }
            });

        }


    public void onBackPressed() {
        MainFragment fragment2 = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment2);
        fragmentTransaction.commit();

    }

}
