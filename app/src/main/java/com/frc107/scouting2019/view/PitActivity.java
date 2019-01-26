package com.frc107.scouting2019.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;

import com.frc107.scouting2019.BuildConfig;
import com.frc107.scouting2019.R;
import com.frc107.scouting2019.Scouting;
import com.frc107.scouting2019.model.question.Question;
import com.frc107.scouting2019.model.question.RadioQuestion;
import com.frc107.scouting2019.model.question.TextQuestion;
import com.frc107.scouting2019.viewmodel.PitViewModel;
import com.google.android.material.textfield.TextInputLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

import com.frc107.scouting2019.utils.PermissionUtils;
import com.frc107.scouting2019.utils.ViewUtils;

/**
 * Created by Matt on 9/30/2017.
 */

public class PitActivity extends AppCompatActivity {
    private EditText teamNumberEditText;
    private RadioGroup teleopPreferenceRadioGroup;
    private EditText cubeNumberInSwitchEditText;
    private EditText cubeNumberInScaleEditText;
    private EditText cubeNumberInExchangeEditText;
    private RadioGroup climbRadioGroup;
    private RadioGroup climbHelpRadioGroup;
    private RadioGroup programmingLanguageRadioGroup;
    private EditText arcadeGameEditText;
    private EditText commentsEditText;

    private PitViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit);

        Question[] questions = {
                new RadioQuestion(R.id.pit_teleopPreference_RadiobtnGrp, true,
                        new RadioQuestion.Option(R.id.pitTeleopScale_btn, getString(R.string.pitTeleopScale)),
                        new RadioQuestion.Option(R.id.pitTeleopSwitch_btn, getString(R.string.pitTeleopSwitch))),
                new TextQuestion(R.id.pit_cubeNumberInSwitch_editText, true),
                new TextQuestion(R.id.pit_cubeNumberInScale_editText, true),
                new TextQuestion(R.id.pit_cubeNumberInExchange_editText, true),
                new RadioQuestion(R.id.pit_climbBoolean_RadiobtnGrp, true,
                        new RadioQuestion.Option(R.id.pitClimbYes_btn, getString(R.string.pitClimbYes)),
                        new RadioQuestion.Option(R.id.pitClimbNo_btn, getString(R.string.pitClimbNo))),
                new RadioQuestion(R.id.pit_climbHelpBoolean_RadiobtnGrp, true,
                        new RadioQuestion.Option(R.id.pitClimbHelp1_btn, getString(R.string.pitClimbHelp1)),
                        new RadioQuestion.Option(R.id.pitClimbHelp2_btn, getString(R.string.pitClimbHelp2)),
                        new RadioQuestion.Option(R.id.pitClimbHelpNo_btn, getString(R.string.pitClimbHelpNo))),
                new RadioQuestion(R.id.pit_programmingLanguage_RadiobtnGrp, true,
                        new RadioQuestion.Option(R.id.pit_programmingLanguageJava_btn, getString(R.string.pitJava)),
                        new RadioQuestion.Option(R.id.pit_programmingLanguageCpp_btn, getString(R.string.pitCpp)),
                        new RadioQuestion.Option(R.id.pit_programmingLanguageLabview_btn, getString(R.string.pitLabview)),
                        new RadioQuestion.Option(R.id.pit_programmingLanguageOther_btn, getString(R.string.pitOther))),
                new TextQuestion(R.id.pit_arcadeGame_editText, true),
                new TextQuestion(R.id.pit_comments_editText, true)
        };
        viewModel = new PitViewModel(questions);

        teamNumberEditText = findViewById(R.id.pit_teamNumber_editText);
        teleopPreferenceRadioGroup = findViewById(R.id.pit_teleopPreference_RadiobtnGrp);
        cubeNumberInSwitchEditText = findViewById(R.id.pit_cubeNumberInSwitch_editText);
        cubeNumberInScaleEditText = findViewById(R.id.pit_cubeNumberInScale_editText);
        cubeNumberInExchangeEditText = findViewById(R.id.pit_cubeNumberInExchange_editText);
        climbRadioGroup = findViewById(R.id.pit_climbBoolean_RadiobtnGrp);
        climbHelpRadioGroup = findViewById(R.id.pit_climbHelpBoolean_RadiobtnGrp);
        programmingLanguageRadioGroup = findViewById(R.id.pit_programmingLanguage_RadiobtnGrp);
        arcadeGameEditText = findViewById(R.id.pit_arcadeGame_editText);
        commentsEditText = findViewById(R.id.pit_comments_editText);

        teamNumberEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int teamNumber = Scouting.getTeamNumber();
                Scouting.setTeamNumber(teamNumber);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
        teleopPreferenceRadioGroup.setOnCheckedChangeListener((group, checkedId) -> viewModel.setAnswer(R.id.pit_teleopPreference_RadiobtnGrp, checkedId));
        cubeNumberInSwitchEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setAnswer(R.id.pit_cubeNumberInSwitch_editText, s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
        cubeNumberInScaleEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setAnswer(R.id.pit_cubeNumberInScale_editText, s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
        cubeNumberInExchangeEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setAnswer(R.id.pit_cubeNumberInExchange_editText, s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
        climbRadioGroup.setOnCheckedChangeListener((group, checkedId) -> viewModel.setAnswer(R.id.pit_climbBoolean_RadiobtnGrp, checkedId));
        climbHelpRadioGroup.setOnCheckedChangeListener((group, checkedId) -> viewModel.setAnswer(R.id.pit_climbHelpBoolean_RadiobtnGrp, checkedId));
        programmingLanguageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> viewModel.setAnswer(R.id.pit_programmingLanguage_RadiobtnGrp, checkedId));
        arcadeGameEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setAnswer(R.id.pit_arcadeGame_editText, s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
        commentsEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setAnswer(R.id.pit_comments_editText, s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });

        checkForPermissions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_activity:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.send_data:
                startActivity(new Intent(this, SendDataActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void savePitData(View view) {
        int unfinishedQuestionId = viewModel.getFirstUnfinishedQuestionId();
        if (unfinishedQuestionId != -1) {
            ViewUtils.requestFocus(findViewById(unfinishedQuestionId), this);
            return;
        }

        boolean hasWritePermissions = PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!hasWritePermissions) {
            Toast.makeText(getApplicationContext(), "No write permissions.", Toast.LENGTH_LONG).show();
            return;
        }

        String saveResponse = viewModel.save();

        Toast.makeText(getApplicationContext(), saveResponse, Toast.LENGTH_LONG).show();

        setResult(RESULT_OK);

        finish();
    }

    public void takeAndCompressPhoto(View view) {
        String teamNumber = viewModel.getAnswerForQuestion(R.id.pit_teamNumber_editText);
        if (teamNumber == null) {
            ViewUtils.requestFocus(findViewById(R.id.pit_teamNumber_editText), this);
            return;
        }

        boolean hasCameraPermissions = PermissionUtils.getPermissions(this, Manifest.permission.CAMERA);
        if (!hasCameraPermissions) {
            Toast.makeText(getApplicationContext(), "No camera permissions.", Toast.LENGTH_LONG).show();
            checkForPermissions();
            return;
        }

        boolean hasWritePermissions = PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!hasWritePermissions) {
            Toast.makeText(getApplicationContext(), "No write permissions.", Toast.LENGTH_LONG).show();
            checkForPermissions();
            return;
        }

        boolean hasReadPermissions = PermissionUtils.getPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (!hasReadPermissions) {
            Toast.makeText(getApplicationContext(), "No read permissions.", Toast.LENGTH_LONG).show();
            checkForPermissions();
            return;
        }

        File photoFile = viewModel.createPhotoFile();
        Uri outputUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            startActivityForResult(takePictureIntent, 0);
        } else {
            Toast.makeText(getApplicationContext(), "Failure trying to take picture.", Toast.LENGTH_LONG).show();
            return;
        }

        boolean didCompressPhoto = viewModel.compressPhoto();
        if (!didCompressPhoto) {
            Toast.makeText(getApplicationContext(), "Failure while compressing picture.", Toast.LENGTH_LONG).show();
        }
    }

    private void checkForPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }
}
