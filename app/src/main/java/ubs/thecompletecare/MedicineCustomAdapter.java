package ubs.thecompletecare;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by ullas on 2/25/2017.
 */

public class MedicineCustomAdapter extends ArrayAdapter<Medicines> {


    private ArrayList<Medicines> meds = new ArrayList<>();
    Context context;

    public MedicineCustomAdapter(ArrayList<Medicines> meds, Context context)
    {
        super(context,R.layout.medicine_list_item,meds);
        this.meds = meds;
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;


        if (view == null) {

            view = View.inflate(context, R.layout.medicine_list_item, null);
        } else {
            view = convertView;
        }
        final Button b = (Button) view.findViewById(R.id.test);
        final TextView medicineIdText = (TextView) view.findViewById(R.id.medicineIdTextLayout);
        final TextView medicineDaysLeft = (TextView) view.findViewById(R.id.medicineDaysLeftLayout);
        final CheckBox morningCheckBoxDisplayBtn = (CheckBox) view.findViewById(R.id.morningCheckBoxDisplay);
        final CheckBox afternoonCheckBoxDisplayBtn = (CheckBox) view.findViewById(R.id.afternoonCheckBoxDisplay);
        final CheckBox nightCheckBoxDisplayBtn = (CheckBox) view.findViewById(R.id.nightCheckBoxDisplay);
        medicineIdText.setText(meds.get(position).getName());
        medicineDaysLeft.setText(Integer.toString(meds.get(position).getDays()));
        morningCheckBoxDisplayBtn.setChecked(meds.get(position).morningDosage());
        afternoonCheckBoxDisplayBtn.setChecked(meds.get(position).afternoonDosage());
        nightCheckBoxDisplayBtn.setChecked(meds.get(position).nightDosage());
        final TextView dosageLabel = (TextView) view.findViewById(R.id.dosageLabelLayout);
        final TextView daysLeftLabel = (TextView) view.findViewById(R.id.daysLeftLabelLayout);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),Integer.toString(position),Toast.LENGTH_SHORT).show();

                if(medicineDaysLeft.getVisibility() == GONE)
                {
                    medicineDaysLeft.setVisibility(VISIBLE);
                }
                else if(medicineDaysLeft.getVisibility() == VISIBLE)
                {
                    medicineDaysLeft.setVisibility(View.GONE);
                }

                if(morningCheckBoxDisplayBtn.getVisibility() == GONE) {
                    morningCheckBoxDisplayBtn.setVisibility(VISIBLE);

                }
                else if(morningCheckBoxDisplayBtn.getVisibility() == VISIBLE) {
                    morningCheckBoxDisplayBtn.setVisibility(GONE);
                }

                if(afternoonCheckBoxDisplayBtn.getVisibility() == GONE) {
                    afternoonCheckBoxDisplayBtn.setVisibility(VISIBLE);
                }
                else if(afternoonCheckBoxDisplayBtn.getVisibility() == VISIBLE) {
                    afternoonCheckBoxDisplayBtn.setVisibility(GONE);
                }

                if(nightCheckBoxDisplayBtn.getVisibility() == GONE) {
                    nightCheckBoxDisplayBtn.setVisibility(VISIBLE);
                }
                else if(nightCheckBoxDisplayBtn.getVisibility() == VISIBLE) {
                    nightCheckBoxDisplayBtn.setVisibility(GONE);
                }

                if(dosageLabel.getVisibility() == GONE) {
                    dosageLabel.setVisibility(VISIBLE);
                }
                else if(dosageLabel.getVisibility() == VISIBLE) {
                    dosageLabel.setVisibility(GONE);
                }

                if(daysLeftLabel.getVisibility() == GONE) {
                    daysLeftLabel.setVisibility(VISIBLE);
                }
                else if(daysLeftLabel.getVisibility() == VISIBLE) {
                    daysLeftLabel.setVisibility(GONE);
                }
                notifyDataSetChanged();




            }
        });



        return view;
    }

}
