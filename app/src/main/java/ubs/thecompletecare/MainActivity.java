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
        Log.i("Val",i.getStringExtra("Email"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button medicationBtn = (Button) findViewById(R.id.medicationBtnLayout);
        medicationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medicineActivity = new Intent(getApplicationContext(),MedicineList.class);
                startActivity(medicineActivity);

            }
        });
    }
}
