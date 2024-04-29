package com.example.sharedpreferencemodellab;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String PREF_NAME = "MyPrefs";
    private static final String DARK_MODE_KEY = "darkMode";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private SharedPreferences sharedPreferences;
    private Switch switchDarkMode;
    private Button buttonSaveCredentials;
    private EditText editTextUsername;
    private EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        buttonSaveCredentials = findViewById(R.id.buttonSaveCredentials);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);        switchDarkMode.setChecked(sharedPreferences.getBoolean(DARK_MODE_KEY, false));
        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(DARK_MODE_KEY, isChecked);
                editor.apply();
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                recreate(); // Recreate the activity to apply the new theme
            }
        });
        buttonSaveCredentials.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USERNAME_KEY, username);
                editor.putString(PASSWORD_KEY, password);
                editor.apply(); // Changed to apply() for asynchronous save
                Toast.makeText(MainActivity.this, "Credentials saved", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Credentials saved successfully");
            } else {
                Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            }
        });
        String sharedPrefsPath = getSharedPreferences(PREF_NAME, MODE_PRIVATE).toString();
        Log.d(TAG, "SharedPreferences file path: " + sharedPrefsPath);
    }
}

