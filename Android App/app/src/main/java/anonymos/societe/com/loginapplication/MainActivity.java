package anonymos.societe.com.loginapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText password,username;
    String pass,user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = findViewById(R.id.password);
        username = findViewById(R.id.username);


        findViewById(R.id.B_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this for geting the input informations 
                pass = password.getText().toString();
                user = username.getText().toString();

            }
        });
    }

    private class AsyncLogin extends AsyncTask<String,Void,String>{

        //here you put your php page URL
        String loginUrl = "localhost/login.php";
        String result;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            try {
                Uri uri = Uri.parse(loginUrl).buildUpon().appendQueryParameter("username",username)
                                                         .appendQueryParameter("password",password)
                                                         .build();
                URL url = new URL(uri.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                return scanner.next();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if(result == null){
                Toast.makeText(MainActivity.this,"there is an error in connection",Toast.LENGTH_SHORT).show();
            }else {
                String receive;
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                receive = object.getString("status");
                if (receive == "success"){
                    JSONArray jsonArray = object.getJSONArray("users");
                    String nom = jsonArray.getJSONObject(0).getString("nom");
                    String prenom = jsonArray.getJSONObject(0).getString("prenom");
                    Toast.makeText(MainActivity.this,"Hello "+nom+" in our Application",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
                }else if (receive == "404"){
                    Toast.makeText(MainActivity.this," Sorry, there is no user with this informations !!",Toast.LENGTH_LONG).show();

                }else if (receive == "faild"){
                    Toast.makeText(MainActivity.this,"you must fill the informations !!",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
