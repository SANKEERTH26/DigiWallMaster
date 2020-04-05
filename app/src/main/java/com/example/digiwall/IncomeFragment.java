package com.example.digiwall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.digiwall.model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    public IncomeFragment() {
        // Required empty public constructor
    }

    //Firebase database

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;

    //Recycler view
    private RecyclerView recyclerView;

    private SearchView searchView;
    
    //TextView Total amount
    private TextView income_total;

    private ArrayList<Data> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_income, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();

        String uid = mUser.getUid();

        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        recyclerView= myview.findViewById(R.id.recycle_income);
        searchView = myview.findViewById(R.id.searchView);

        return myview;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(mIncomeDatabase != null){

            mIncomeDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    list = new ArrayList<Data>();
                    if(dataSnapshot.exists()){
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            list.add(ds.getValue(Data.class));
                        }
                        AdapterClass adapterClass = new AdapterClass(list);
                        recyclerView.setAdapter(adapterClass);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//
//        View mView;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.mView = itemView;
//        }
//
//
//        private void setType(String type){
//
//            TextView mType=mView.findViewById(R.id.type_txt_income);
//            mType.setText(type);
//
//        }
//
//        private void setNote(String note){
//
//            TextView mNote=mView.findViewById(R.id.note_txt_income);
//            mNote.setText(note);
//
//        }
//
//        private void setDate(String date){
//
//            TextView mDate=mView.findViewById(R.id.date_txt_income);
//            mDate.setText(date);
//
//        }
//
//        private void setAmount(int amount){
//
//            TextView mAmount=mView.findViewById(R.id.amount_txt_income);
//            String stamount=String.valueOf(amount);
//            mAmount.setText(stamount);
//
//        }
//
//
//    }

}

