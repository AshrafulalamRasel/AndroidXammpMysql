package database.com.mysql.mysqlwithxammpcrud;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsWrite extends AppCompatActivity {

    EditText phoNumber,smsText;
    Button sendbtn,phoneBtn;
    private final static int SEND_SMS_PERMISSION_REQ=1;
    private static final int RESULT_PICK_CONTACT =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_write);

        phoNumber = findViewById(R.id.phoneNumber);
        smsText =  findViewById(R.id.wrireText);
        sendbtn = findViewById(R.id.send);
        phoneBtn = findViewById(R.id.phoneNumberCollect);


        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            sendbtn.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQ);
        }

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1=phoNumber.getText().toString();
                String s2=smsText.getText().toString();
                if(!TextUtils.isEmpty(s1)&&!TextUtils.isEmpty(s2))
                {

                    if(checkPermission(Manifest.permission.SEND_SMS))
                    {
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(s1,null,s2,null,null);
                    }
                    else {
                        Toast.makeText(SmsWrite.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SmsWrite.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }

            }
        });


        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent (Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult (in, RESULT_PICK_CONTACT);
                Toast.makeText(SmsWrite.this,"fgdgd",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean checkPermission(String sendSms) {

        int checkpermission= ContextCompat.checkSelfPermission(this,sendSms);
        return checkpermission== PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case SEND_SMS_PERMISSION_REQ:
                if(grantResults.length>0 &&(grantResults[0]==PackageManager.PERMISSION_GRANTED))
                {
                    sendbtn.setEnabled(true);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if(resultCode==RESULT_OK)
        {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked (data);
                    break;
            }
        }
        else
        {
            Toast.makeText (this, "Failed To pick contact", Toast.LENGTH_SHORT).show ();
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;

        try {
            String phoneNo = null;
            Uri uri = data.getData ();
            cursor = getContentResolver ().query (uri, null, null,null,null);
            cursor.moveToFirst ();
            int phoneIndex = cursor.getColumnIndex (ContactsContract.CommonDataKinds.Phone.NUMBER);

            phoneNo = cursor.getString (phoneIndex);

            phoNumber.setText (phoneNo);


        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
