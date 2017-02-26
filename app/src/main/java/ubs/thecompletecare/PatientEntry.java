package ubs.thecompletecare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientEntry extends AppCompatActivity {
     int counter = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_entry);
        Intent i = getIntent();
        final String userEmail = i.getStringExtra("Email");
        final String userId = i.getStringExtra("ID");
        final EditText nameET = (EditText) findViewById(R.id.nameETLayout);
        final RadioButton femaleRadioButton = (RadioButton) findViewById(R.id.femaleRadioButtonLayout);
        final RadioButton maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButtonLayout);
        final EditText dobET = (EditText) findViewById(R.id.dobETLayout);
        final EditText ageET = (EditText) findViewById(R.id.ageETLayout);
        final Button submitButton = (Button) findViewById(R.id.submitBtnLayout);

        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ud = FirebaseDatabase.getInstance().getReference();

        bd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("VAL",dataSnapshot.toString());
                setValue(Integer.parseInt(dataSnapshot.child("patientCounter").getValue().toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int c = getValue();
                DatabaseReference d = bd.child("patient").child(Integer.toString(++c));
                d.child("patientName").setValue(nameET.getText().toString());
                if(femaleRadioButton.isChecked()) {
                    d.child("gender").setValue("female");
                }
                else{
                    d.child("gender").setValue("male");
                }
                d.child("dob").setValue(dobET.getText().toString());
                d.child("age").setValue(ageET.getText().toString());
                d.child("caretakerID").setValue(userId.toString());
                ud.child("patientCounter").setValue(Integer.toString(c));
                ud.child("users").child(userId).child("patientID").setValue(Integer.toString(c));
               finish();

            }
        });






    }
    public void setValue(int a)
    {
        counter = a;
        Log.i("Log",Integer.toString(counter));
    }

    public int getValue()
    {
        return counter;
    }

}
