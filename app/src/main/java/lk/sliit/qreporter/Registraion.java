package lk.sliit.qreporter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registraion extends AppCompatActivity {
    EditText editTextEmail,editTextPassword,editTextName;
    FirebaseAuth mAuth;
    UserId user_store_Id;
    FirebaseDatabase database_user;
    DatabaseReference reference;
    ProgressBar progressBar;
    Button button_reg;
    AlertDialog.Builder builder;
    TextView backtosignup,textView16;
    ImageView imageView16 , imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registraion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView16 = findViewById(R.id.textView16);
        imageView4 = findViewById(R.id.imageView4);
        imageView16 = findViewById(R.id.imageView16);
        Animation animation = AnimationUtils.loadAnimation(Registraion.this,R.anim.animation);
        Animation animation2 = AnimationUtils.loadAnimation(Registraion.this,R.anim.animation2);

        imageView16.startAnimation(animation);
        imageView4.startAnimation(animation);
        textView16.startAnimation(animation2);

        editTextName = findViewById(R.id.editText_name);
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        button_reg = findViewById(R.id.button_reg_new);
        /*progressBar = findViewById(R.id.progressBar);*/
        mAuth = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(this);
        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*progressBar.setVisibility(View.VISIBLE);*/
                String email,password,name;

                name = String.valueOf(editTextName.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Registraion.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Registraion.this, "Enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(Registraion.this, "Enter the Name", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    mAuth.getCurrentUser().sendEmailVerification();
                                    builder.setTitle("Alert")
                                            .setMessage("verify you email!")
                                            .setCancelable(true)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Toast.makeText(Registraion.this, "Account Created!!",
                                                            Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(Registraion.this, Main3homeActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }).show();

                                    /*progressBar.setVisibility(View.GONE);*/
                                    String userId = generateUniqueUserId();
                                    User user = new User(name,email,userId);
                                    database_user = FirebaseDatabase.getInstance();
                                    reference = database_user.getReference("User").child(userId);
                                    reference.setValue(user);

                                    saveUserId(userId);



                                } else {
                                    // If sign in fails, display a message to the user.
                                    /*progressBar.setVisibility(View.GONE);*/
                                    Toast.makeText(Registraion.this, "Authentication failed. ",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

        backtosignup = findViewById(R.id.textView_backtosignup);
        backtosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registraion.this, Login.class);
                startActivity(intent);

            }
        });
    }

    // Method to generate a unique user ID
    private String generateUniqueUserId() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        return reference.push().getKey();
    }
    // Save user ID in shared preferences for future use
    private void saveUserId(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }





}