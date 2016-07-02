package id.co.meda.survey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.co.meda.survey.config.UrlConfig;
import id.co.meda.survey.controller.AppController;
import id.co.meda.survey.helper.LocalDBHandler;
import id.co.meda.survey.helper.SessionManager;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private LocalDBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //LocalDBHandler
        db =  new LocalDBHandler(getApplicationContext());

        //SessionManager
        session = new SessionManager(getApplicationContext());

        //check isLoggedIn
        if(session.isLoggedIn()){

            //Take to homActivity if already loggin
            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();

        }






        Button clickButton = (Button) findViewById(R.id.saveBtn);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                registerRequest();
            }
        });




    }

    private void registerRequest(){

        EditText inputFullName = (EditText) findViewById(R.id.fullNameField);
        EditText inputEmail = (EditText) findViewById(R.id.emailField);
        EditText inputPassword = (EditText) findViewById(R.id.passwordField);

        String name = inputFullName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
           register(name, email, password);

        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }


    }

    private void register(final String name,final String email,final String password){
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();




        StringRequest strReq = new StringRequest(Method.POST,
                UrlConfig.URL_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("fullname", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(strReq);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
}
