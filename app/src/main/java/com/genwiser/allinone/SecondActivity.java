package com.genwiser.allinone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    Button b1;
    SmsManager smsManager = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String message = bundle.get("message").toString();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        String[] fruits = {"Apple","Banana","Orange","Apricot","Cherry"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_item,fruits);

        AutoCompleteTextView textView = findViewById(R.id.autoText);
        textView.setThreshold(1);
        textView.setAdapter(adapter);

        b1 = findViewById(R.id.goBack);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("message", "Hi there, this was 2nd Activity");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void buttonClicked(View view) {
        Intent i = new Intent(this,MyService.class);
        switch (view.getId()) {
            case R.id.asyncTask:
                myAsyncTask();
                break;
            case R.id.database:
                myDatabase();
                break;
            case R.id.startService:
                startService(i);
                break;
            case R.id.stopService:
                stopService(i);
                break;
            case R.id.sms:
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){}
                    else{
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
                    }
                }else{
                    smsManager.sendTextMessage("DestinationAddress", "SourceAddress", "This is Message body from direct sending", null, null);
                    Log.d("Second Activity", "buttonClicked: Message sent");
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    smsManager.sendTextMessage("DestinationAddress", "SourceAddress", "This is Message body after getting permission", null, null);
                }
            }
        }
    }

    public void myDatabase(){
        DBHelper db;
        db = new DBHelper(this);
        db.insertData("Ramu","123456879");
        db.insertData("Shyam","987654321");
    }

    public void myAsyncTask() {
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("3000");
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            return "Thread slept for 3 secs";
        }
        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
        }
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(SecondActivity.this, "AsyncTask Executing", "Wait for 3 secs");
        }
    }
}