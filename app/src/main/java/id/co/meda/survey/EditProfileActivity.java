package id.co.meda.survey;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int SELECT_PHOTO = 10;
    ImageView photoUser;
    EditText userFullName;
    EditText userEmail;
    EditText userOldPassword;
    EditText userPassword;
    EditText userMobileNumber;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    public void init(){
        photoUser = (ImageView) findViewById(R.id.photoField);
        userFullName = (EditText) findViewById(R.id.fullNameField);
        userEmail = (EditText) findViewById(R.id.emailField);
        userPassword = (EditText) findViewById(R.id.passwordField);
        userMobileNumber = (EditText) findViewById(R.id.mobileNumberField);
        userOldPassword = (EditText) findViewById(R.id.oldPasswordField);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        photoUser.setOnClickListener(this);

        //TODO menyetel userFullName dengan nama lengkap user yang ada di database server
        //TODO menyetel userEmail dengan email user didatabase server

    }

    public void onClickSaveProfile(View view) {

//        String oldPasswordFromUser = userOldPassword.getText().toString();
//        boolean isOldPasswordCorrect = true; //TODO mengecek apakah password lama user sesuai dengan password di database
//
//        if(isOldPasswordCorrect) {//ketika old pasword yang diinput user sesuai dengan database
//
//            String password = userPassword.getText().toString();
//            String rePassword = userRePassword.getText().toString();
//
//            if (password.length() == 0 || password.length() == 0) {
//                Toast.makeText(this, getString(R.string.please_fill_all_box), Toast.LENGTH_SHORT).show();
//            } else if (password.equals(rePassword)) { //ketika password sesuai dengan re-password untuk memastikan user tidak salah input
//                //TODO mengubah password yang ada di database server sesuai berdasarkan input user
//                //TODO mengganti nama lengkap user yang ada di database server berdasarkan input user
//                Toast.makeText(this, getString(R.string.profile_has_been_modified), Toast.LENGTH_SHORT).show();
//                finish();
//            } else {
//                Toast.makeText(this, getString(R.string.password_not_match_with_re_password), Toast.LENGTH_SHORT).show();
//            }
//
//        }else{//ketika old tidak password yang diinput user sesuai dengan database
//
//            Toast.makeText(this, getString(R.string.your_old_password_is_incorrect), Toast.LENGTH_SHORT).show();
//
//        }
    }

    @Override
    public void onClick(View v) {
        takePictureOrGetFromGalery();
    }

    private void takePictureOrGetFromGalery() {

        Intent pickPhoto = new Intent();
        pickPhoto.setType("image/*");
        pickPhoto.setAction(Intent.ACTION_GET_CONTENT);

        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooserIntent = Intent.createChooser(pickPhoto, "Take Your Photo");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhoto });

        startActivityForResult(chooserIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PHOTO){
                if(data == null){
                    Log.e("FARISIII","data dari ambil photo null");
                    return;
                }
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    photoUser.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
