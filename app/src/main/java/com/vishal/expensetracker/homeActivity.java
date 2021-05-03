package com.vishal.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth auth;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private FirebaseDatabase db;
    private DatabaseReference reference;

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(homeActivity.this, RegistrationActivity.class));
        }
        toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer);

        navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));//color of hamburger
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        db = FirebaseDatabase.getInstance();
        reference=db.getReference("user").child(auth.getCurrentUser().getUid());
        View headerView=navigationView.getHeaderView(0);
        TextView header_name=(TextView)headerView.findViewById(R.id.header_name);
        TextView header_email=(TextView)headerView.findViewById(R.id.header_email);
//        header_name.setText("Sam");
//        header_email.setText("@gmail.com");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameOf=snapshot.child("name").getValue().toString();
                String emailOf=snapshot.child("email").getValue().toString();
                header_name.setText(nameOf);
                header_email.setText(emailOf);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new MainFragment());
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId()==R.id.homeMenu){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new MainFragment());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()==R.id.Revenue){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new revenue());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()==R.id.Expense){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new expense());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()==R.id.Record){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new record());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()==R.id.Share){
            Intent myIntent=new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plane");
            String shareBody="Your body here";
            String shareSub="Your Subject here";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(myIntent,"Share using"));
        }

        if(menuItem.getItemId()==R.id.LogOutMenu){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(homeActivity.this,loginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return true;
    }
}