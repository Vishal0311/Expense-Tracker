package com.vishal.expensetracker;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class revenue extends Fragment {

    EditText revAmt,revDate,revComment;

    Button revSubmit;
   private FirebaseDatabase db;
   private DatabaseReference root,reference;
   private FirebaseAuth firebaseAuth;
   int currBal;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_revenue, container, false);

        //for progress bar
        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);





        firebaseAuth=FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        root = db.getReference("user").child(firebaseAuth.getCurrentUser().getUid()).child("Transaction");
        reference=db.getReference("user").child(firebaseAuth.getCurrentUser().getUid());

        revAmt=view.findViewById(R.id.revAmt);
        revDate=view.findViewById(R.id.revDate);
        revComment=view.findViewById(R.id.revComment);
        revSubmit=view.findViewById(R.id.revSubmit);
        revSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value=snapshot.child("savings").getValue().toString();
                        currBal=Integer.parseInt(value);

                        String gains=revAmt.getText().toString().trim();
                        String dates=revDate.getText().toString();
                        String note=revComment.getText().toString().trim();
                        Toast.makeText(getContext(), gains+" added", Toast.LENGTH_LONG).show();

                        HashMap<String,String> userMap=new HashMap<>();
                        userMap.put("Cash","+"+gains);
                        userMap.put("Date",dates);
                        userMap.put("note",note);
                        root.push().setValue(userMap);

                        currBal=currBal+Integer.parseInt(gains);
                        HashMap map=new HashMap();
                        map.put("savings",currBal);

                        reference.updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });
      return view;
    }

}
