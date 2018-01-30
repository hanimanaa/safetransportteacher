package androiddevelopments.safetransportteacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androiddevelopments.safetransportteacher.model.Students;


import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentsActivity extends AppCompatActivity {

        List<Students> studentList = new ArrayList<>();
        StudentAdapter adapter;
        DatabaseReference ref;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_students);


            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReference();

            ref.child("Students").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    studentList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Students student = snapshot.getValue(Students.class);
                        studentList.add(student);
                        adapter.notifyDataSetChanged();
                    }

                    Collections.reverse(studentList);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
            adapter = new StudentAdapter(StudentsActivity.this, studentList);
            recyclerView.setLayoutManager(new LinearLayoutManager(StudentsActivity.this));
            recyclerView.setAdapter(adapter);

            SearchView searchView = (SearchView) findViewById(R.id.search_view);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Query fireQuery = ref.child("Students").orderByChild("name").equalTo(query);
                    fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                Toast.makeText(StudentsActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                            } else {
                                List<Students> searchList = new ArrayList<Students>();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Students student = snapshot.getValue(Students.class);
                                    searchList.add(student);
                                    adapter = new StudentAdapter(StudentsActivity.this, searchList);
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    adapter = new StudentAdapter(StudentsActivity.this, studentList);
                    recyclerView.setAdapter(adapter);
                    return false;
                }
            });
        }


    }