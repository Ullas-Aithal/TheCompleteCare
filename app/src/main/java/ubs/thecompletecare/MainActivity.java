package ubs.thecompletecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        final String userEmail = i.getStringExtra("Email");
        final String userId = i.getStringExtra("ID");


        Log.i("Val",i.getStringExtra("Email"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button medicationBtn = (Button) findViewById(R.id.medicationBtnLayout);
        medicationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medicineActivity = new Intent(getApplicationContext(),MedicineList.class);
                medicineActivity.putExtra("Email",userEmail);
                medicineActivity.putExtra("ID",userId);
                startActivity(medicineActivity);

            }
        });

        Button patientEntryBtn = (Button) findViewById(R.id.patientEntryBtnLayout);
        patientEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent patientEntryIntent = new Intent(getApplicationContext(),PatientEntry.class);
                patientEntryIntent.putExtra("Email",userEmail);
                patientEntryIntent.putExtra("ID",userId);
                startActivity(patientEntryIntent);
            }
        });
    }
}
