package ubs.thecompletecare;



import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static java.sql.Types.NULL;

public class MedicineList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference db;
    private PendingIntent broadcast;
    private PendingIntent broadcast1;
    private String patientName;


     int patientID1 = -1;

    public void setValue(int a)
    {
        patientID1 = a;
        Log.i("patientID",Integer.toString(patientID1));
    }
    public int getValue()
    {
        return patientID1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        final String userEmail = i.getStringExtra("Email");
        final String userId = i.getStringExtra("ID");
        db = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_medicine_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseReference patientID = FirebaseDatabase.getInstance().getReference();

        patientID.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setValue(Integer.parseInt(dataSnapshot.child(userId).child("patientID").getValue().toString()));
                Log.i("DATA CHANGE",Integer.toString(getValue()));

                MedicineFragment mf = new MedicineFragment();
                Bundle b = new Bundle();
                b.putString("Email",userEmail);
                b.putString("ID",userId);
                b.putString("patientID",Integer.toString(getValue()));
                mf.setArguments(b);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.medicineListFrameLayout,mf,"Medicine Fragment");
                ft.commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MedicineList.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_medicine_layout, null);
                dialogBuilder.setView(dialogView);

                final EditText addMedsET = (EditText) dialogView.findViewById(R.id.addMedsETLayout);
                final EditText numberOfMedicinesET = (EditText) dialogView.findViewById(R.id.numberOfMedicinesETLayout);
                final CheckBox morningCB = (CheckBox) dialogView.findViewById(R.id.morningCheckBox);
                final CheckBox afternoonCB = (CheckBox) dialogView.findViewById(R.id.afternoonCheckBox);
                final CheckBox nightCB = (CheckBox) dialogView.findViewById(R.id.nightCheckBox);






                dialogBuilder.setTitle("Add New Medicine");

                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        try {

                            final DatabaseReference medicines = db.child("patient").child(Integer.toString(getValue())).child("medicines");

                            medicines.child(addMedsET.getText().toString()).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    medicines.child(addMedsET.getText().toString()).child("days").setValue(numberOfMedicinesET.getText().toString());
                                    medicines.child(addMedsET.getText().toString()).child("dosage").setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        DatabaseReference patientDosage = medicines.child(addMedsET.getText().toString()).child("dosage");
                                        patientDosage.child("morning").setValue(morningCB.isChecked());
                                        patientDosage.child("afternoon").setValue(afternoonCB.isChecked());
                                        patientDosage.child("night").setValue(nightCB.isChecked());


                                            Fragment frg = null;
                                            frg = getFragmentManager().findFragmentByTag("Medicine Fragment");
                                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                            ft.detach(frg);
                                            ft.attach(frg);
                                            ft.commit();

                                        }
                                    });
                                }
                            });

                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Exception:" + e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        //PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        broadcast = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        int interval = 8000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.HOUR, 4);


        broadcast1 = PendingIntent.getBroadcast(this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, broadcast);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, broadcast1);

//        calendar.set(Calendar.HOUR_OF_DAY, 2);
//        calendar.set(Calendar.MINUTE, 41);
//
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, broadcast1);

//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 15);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.medicine_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
