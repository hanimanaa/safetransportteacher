package androiddevelopments.safetransportteacher;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import androiddevelopments.safetransportteacher.model.Students;

public class Main2Activity extends AppCompatActivity {

    Calendar calendar;
    SimpleDateFormat mdf;

    Spinner spinner;
    ArrayAdapter<CharSequence> classAdapter;

    List<Students> studentList = new ArrayList<>();
    StudentAdapter adapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        calendar= Calendar.getInstance();
        mdf = new SimpleDateFormat("dd / MM / yyyy");
        String strDate = "Current Date : " + mdf.format(calendar.getTime());
        TextView txView =(TextView)findViewById(R.id.date_view);
        txView.setText(strDate);

        spinner=(Spinner)findViewById(R.id.sp);
        classAdapter = ArrayAdapter.createFromResource(this,R.array.class_names,R.layout.support_simple_spinner_dropdown_item);
        classAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(classAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Main2Activity.this, parent.getItemAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        adapter = new StudentAdapter(Main2Activity.this, studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
        recyclerView.setAdapter(adapter);


    }




}




