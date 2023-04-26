package com.example.mylocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.media.MediaSession2Service;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mylocation.databinding.ActivityLocationHistoryBinding;
import com.example.mylocation.util.LocationHistoryAdapter;
import com.example.mylocation.util.LocationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LocationHistoryActivity extends AppCompatActivity {

    private ActivityLocationHistoryBinding binding;
    private ArrayList<LocationModel> locationModelArrayList;
    private LocationHistoryAdapter locationHistoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationModelArrayList = new ArrayList<>();
        locationHistoryAdapter = new LocationHistoryAdapter(this, locationModelArrayList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(locationHistoryAdapter);
        fetchData();

    }

    private void fetchData(){
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Locations").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationModelArrayList.clear();
                for (DataSnapshot dataSnapShot: snapshot.getChildren()) {
                    LocationModel locationModel = dataSnapShot.getValue(LocationModel.class);
                    locationModelArrayList.add(locationModel);
                }
                locationHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(LocationHistoryActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.delete:
                deleteAllLocationHistory();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllLocationHistory() {
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Locations").child(uid);
        databaseReference.removeValue();
    }

}