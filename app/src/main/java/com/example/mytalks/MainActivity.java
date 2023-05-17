package com.example.mytalks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytalks.Adapters.Fragment_Adapters;
import com.example.mytalks.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.viewpager.setAdapter(new Fragment_Adapters(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewpager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.mymenu, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.settings:
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }






}