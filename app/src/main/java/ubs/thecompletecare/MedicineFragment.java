package ubs.thecompletecare;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by ullas on 2/25/2017.
 */

public class MedicineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.medicine_list_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
         DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        ListView lv = (ListView) view.findViewById(R.id.medicineListViewLayout) ;
        final  ArrayList<Medicines> meds = new ArrayList<>();
        final MedicineCustomAdapter adapter = new MedicineCustomAdapter(meds,this.getContext());
        lv.setAdapter(adapter);



        try {
            db.child("patient").child("0").child("medicines").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot med : dataSnapshot.getChildren()) {

                        Log.i("snap",med.toString());
                        boolean dosage[] = new boolean[3];
                        int days = Integer.parseInt(med.child("days").getValue().toString());
                        String name = med.getKey().toString();
                        dosage[0] = Boolean.parseBoolean(med.child("dosage").child("morning").getValue().toString());
                        dosage[1] = Boolean.parseBoolean(med.child("dosage").child("afternoon").getValue().toString());
                        dosage[2] = Boolean.parseBoolean(med.child("dosage").child("night").getValue().toString());

                        meds.add(new Medicines(days,dosage,name));
                        adapter.notifyDataSetChanged();





                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch(Exception e)
        {
            Toast.makeText(getContext(), "Exception" + e.toString(), Toast.LENGTH_SHORT).show();
        }





//        meds.add(new Medicines(1));
//        meds.add(new Medicines(2));
//        meds.add(new Medicines(3));
//        meds.add(new Medicines(4));



        lv.setAdapter(adapter);


       

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}