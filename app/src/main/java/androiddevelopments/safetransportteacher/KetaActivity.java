package androiddevelopments.safetransportteacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androiddevelopments.safetransportteacher.model.Ketot;
import androiddevelopments.safetransportteacher.model.Students;

public class KetaActivity extends AppCompatActivity {

    List<Ketot> ketaList = new ArrayList<Ketot>();
    KetaAdapter adapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keta);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        ref.child("Ketot").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ketaList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Ketot keta = snapshot.getValue(Ketot.class);
                    ketaList.add(keta);
                    adapter.notifyDataSetChanged();
                }

                Collections.reverse(ketaList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ketaRv);
        adapter = new KetaAdapter(KetaActivity.this, ketaList);
        recyclerView.setLayoutManager(new LinearLayoutManager(KetaActivity.this));
        recyclerView.setAdapter(adapter);


    }


}