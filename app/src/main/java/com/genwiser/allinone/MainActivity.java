package com.genwiser.allinone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public int numb = 0;
    public static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void buttonMethods(View view) {
        switch (view.getId()){
            case R.id.callActivity:
                Intent i = new Intent(MainActivity.this,SecondActivity.class);
                i.putExtra("message","This was a hii from 1st Activity!");
                startActivityForResult(i,REQUEST_CODE);
                break;
            case R.id.showToast:
                Toast.makeText(this, "Showing a Toast", Toast.LENGTH_SHORT).show();
                break;
            case R.id.notify:
                createNotificationChannel();
                Notification notification = new Notification.Builder(this)
                        .setContentTitle("My App Notifies")
                        .setContentText("The Subject is here.")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
                        .setChannelId("1")
                        .build();
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0,notification);
                break;
            case R.id.simpleDialog:
                ProgressDialog.Builder builder =  new ProgressDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle("Exit")
                        .setMessage("Are you sure to exit")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface,int i){
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
                break;
            case R.id.customDialog:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCancelable(false);
                dialog.show();
                Button b1 = dialog.findViewById(R.id.ok);
                b1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Toast.makeText(MainActivity.this, numb+" Checkboxes Selected!", Toast.LENGTH_SHORT).show();
                        numb = 0;
                        dialog.dismiss();
                    }
                });
                Button b2 = dialog.findViewById(R.id.cancel);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                break;

            case R.id.openWeb:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itsjigyasu.me"));
                startActivity(intent);
                break;
        }
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "Channel_1", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void checkBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if(checked){
            numb++;
        }else{
            numb--;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this,"Item 3 Selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Toast.makeText(this,intent.getStringExtra("message"),Toast.LENGTH_SHORT).show();
        }
    }
}