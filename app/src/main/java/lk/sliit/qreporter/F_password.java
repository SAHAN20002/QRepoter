package lk.sliit.qreporter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class F_password extends AppCompatActivity {
    String REemail;
    Button button;
    EditText email;
    FirebaseAuth mAuth;
    ImageView imageView15,imageView17;
    TextView textView16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fpassword);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Animation animation = AnimationUtils.loadAnimation(F_password.this,R.anim.animation);
        Animation animation2 = AnimationUtils.loadAnimation(F_password.this,R.anim.animation2);

        imageView15 = findViewById(R.id.imageView15);
        imageView17 = findViewById(R.id.imageView17);
        textView16 = findViewById(R.id.textView6);

        imageView17.startAnimation(animation);
        imageView15.startAnimation(animation);
        textView16.startAnimation(animation2);

        button = findViewById(R.id.button_res_Paw);
        email = findViewById(R.id.editTextText_re_password);
        mAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 REemail =  email.getText().toString().trim();
                if(!TextUtils.isEmpty(REemail)){
                    ResetPassword();
                }else {
                    email.setError("Email Filed can't be Empty");
                }
            }
        });
    }

    private void ResetPassword(){
              mAuth.sendPasswordResetEmail(REemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void unused) {
                      Toast.makeText(F_password.this, "Reseat Password link send to you email", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(F_password.this, Login.class);
                      startActivity(intent);
                      finish();
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(F_password.this, "Error!", Toast.LENGTH_SHORT).show();
                  }
              });
    }

}

