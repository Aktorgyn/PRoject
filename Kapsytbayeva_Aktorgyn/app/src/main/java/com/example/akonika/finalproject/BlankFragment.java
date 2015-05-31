package com.example.akonika.finalproject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements View.OnClickListener {
    SecondActivity sa;
    Button upButton;
    ListView lvMain;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter_menu, container, false);
        upButton = (Button) view.findViewById(R.id.button);
        upButton.setOnClickListener(this);

        lvMain = (ListView) view.findViewById(R.id.listView);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.names, android.R.layout.simple_list_item_multiple_choice);
        lvMain.setAdapter(adapter);

        sa = (SecondActivity) getActivity();

        lvMain.setItemChecked(0,sa.isVisibleCinema);
        lvMain.setItemChecked(1,sa.isVisibleHospital);
        lvMain.setItemChecked(2,sa.isVisibleCancer);

        return view;
    }



    @Override
    public void onClick(View v) {
        ((SecondActivity)getActivity()).dohit();
        ((SecondActivity)getActivity()).delelteBf();
        Log.d("loooog", "checked: ");
        SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();
        sa.isVisibleCancer = false;
        sa.isVisibleCinema = false;
        sa.isVisibleHospital = false;
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)) {
                if(key == 0) sa.isVisibleCinema = true;
                if(key == 1) sa.isVisibleHospital = true;
                if(key == 2) sa.isVisibleCancer = true;
            }
        }
        if(sa.isVisibleCinema)
            sa.showmesto(1);
        else
            sa.hidemesto(1);
        if(sa.isVisibleHospital)
            sa.showmesto(2);
        else
            sa.hidemesto(2);
        if(sa.isVisibleCancer)
            sa.showmesto(3);
        else
            sa.hidemesto(3);
    }
}

