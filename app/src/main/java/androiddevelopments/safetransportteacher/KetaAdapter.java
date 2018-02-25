package androiddevelopments.safetransportteacher;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androiddevelopments.safetransportteacher.model.Ketot;
import androiddevelopments.safetransportteacher.model.Students;

/**
 * Created by Devlomi on 05/04/2017.
 */

public class KetaAdapter extends RecyclerView.Adapter<KetaAdapter.KetotHolder> {
    private Context context;
    private List<Ketot> ketaList;

    public KetaAdapter(Context context, List<Ketot> ketaList) {
        this.context = context;
        this.ketaList = ketaList;
    }

    @Override
    public KetotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_keta, parent, false);
        return new KetotHolder(row);
    }

    @Override
    public void onBindViewHolder(KetotHolder holder, int position) {
        final Ketot keta = ketaList.get(position);
        holder.ketaNum.setText(keta.getNum());
        holder.KetaName.setText(keta.getClassName());

        Toast.makeText(context, "...", Toast.LENGTH_SHORT).show();

        Intent intent =new Intent(context,StudentsActivity.class);
        intent.putExtra("className",ketaList.get(position));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return ketaList.size();
    }

    class KetotHolder extends RecyclerView.ViewHolder {
        TextView ketaNum , KetaName;


        public KetotHolder(View itemView) {
            super(itemView);
            ketaNum = (TextView) itemView.findViewById(R.id.ketaNum);
            KetaName = (TextView) itemView.findViewById(R.id.ketaName);


        }
    }
}