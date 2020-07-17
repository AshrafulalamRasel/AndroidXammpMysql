package database.com.mysql.mysqlwithxammpcrud;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    EditText name, age, email;
    Button push;
    XammpConnector xammpConnector;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.textname);
        age = findViewById(R.id.textage);
        email = findViewById(R.id.textemail);
        push = findViewById(R.id.btnpush);

        xammpConnector = new XammpConnector();

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Doregister gg = new Doregister();
                gg.execute("");
                //Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                //Intent smsWrite = new Intent(MainActivity.this,SmsWrite.class);
                //startActivity(smsWrite);
               // conn = xammpConnector.CONN();
              //  Log.e("354435",""+conn);
            }
        });

    }

    public class Doregister extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {

            conn = xammpConnector.CONN();
          Log.e("354435",""+conn);
            String Sql = "insert into ross(name,age,email) values(?,?,?)";

            try {
                pst = conn.prepareStatement(Sql);
                pst.setString(1, name.getText().toString());
                pst.setString(2, age.getText().toString());
                pst.setString(3, email.getText().toString());

                pst.execute();


            } catch (Exception e) {

            }

            return "Successfully";
        }
    }
}