package id.co.meda.survey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SurveyResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);
    }

    public void onClickGoToMainMenu(View view) {
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}
