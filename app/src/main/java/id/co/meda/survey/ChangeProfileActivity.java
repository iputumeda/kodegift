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

    EditText userFullName;
    EditText userEmail;
    EditText userOldPassword;
    EditText userPassword;
    EditText userRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        init();

    }

    public void init(){
        userFullName = (EditText) findViewById(R.id.userFullName_tv_acp);
        userEmail = (EditText) findViewById(R.id.userEmail_tv_acp);
        userPassword = (EditText) findViewById(R.id.userPassword_tv_acp);
        userRePassword = (EditText) findViewById(R.id.userRePassword_tv_acp);
        userOldPassword = (EditText) findViewById(R.id.userOldPassword_tv_acp);
        userEmail.setEnabled(false);

        //TODO menyetel userFullName dengan nama lengkap user yang ada di database server
        //TODO menyetel userEmail dengan email user didatabase server

    }

    public void onClickSaveProfile(View view) {

        String oldPasswordFromUser = userOldPassword.getText().toString();
        boolean isOldPasswordCorrect = true; //TODO mengecek apakah password lama user sesuai dengan password di database

        if(isOldPasswordCorrect) {//ketika old pasword yang diinput user sesuai dengan database

            String password = userPassword.getText().toString();
            String rePassword = userRePassword.getText().toString();

            if (password.length() == 0 || password.length() == 0) {
                Toast.makeText(this, getString(R.string.please_fill_all_box), Toast.LENGTH_SHORT).show();
            } else if (password.equals(rePassword)) { //ketika password sesuai dengan re-password untuk memastikan user tidak salah input
                //TODO mengubah password yang ada di database server sesuai berdasarkan input user
                //TODO mengganti nama lengkap user yang ada di database server berdasarkan input user
                Toast.makeText(this, getString(R.string.profile_has_been_modified), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, getString(R.string.password_not_match_with_re_password), Toast.LENGTH_SHORT).show();
            }

        }else{//ketika old tidak password yang diinput user sesuai dengan database

            Toast.makeText(this, getString(R.string.your_old_password_is_incorrect), Toast.LENGTH_SHORT).show();

        }
    }
}
