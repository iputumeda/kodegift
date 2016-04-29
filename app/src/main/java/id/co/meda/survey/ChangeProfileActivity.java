package id.co.meda.survey;

import android.content.Intent;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeProfileActivity extends AppCompatActivity {

    private static final String NAME = "NAME FROM MAIN MENU";
    private static final String EMAIL = "EMAIL FROM MAIN MENU";
    EditText userFullName;
    EditText userPassword;
    EditText userRePassword;
    Button saveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        init();
        buttonAction();

    }

    public void init(){
        userFullName = (EditText) findViewById(R.id.userFullName_tv_acp);
        userPassword = (EditText) findViewById(R.id.userPassword_tv_acp);
        userRePassword = (EditText) findViewById(R.id.userRePassword_tv_acp);
        saveProfile = (Button) findViewById(R.id.saveProfile_b_acp);

        Intent intent = getIntent();
        String name = intent.getStringExtra(NAME);
        String email = intent.getStringExtra(EMAIL);

        userFullName.setText(name);
        ((TextView)findViewById(R.id.userEmail_tv_acp)).setText(email);
    }

    public void buttonAction(){
        String password = userPassword.getText().toString();
        String rePassword = userRePassword.getText().toString();
        if(password.equals(rePassword)){
            saveProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO mengubah password dari database server
                    finish();
                }
            });
        }else{
            Toast.makeText(this,"Password tidak sesuai dengan Repassword",Toast.LENGTH_SHORT).show();
        }
    }

}
