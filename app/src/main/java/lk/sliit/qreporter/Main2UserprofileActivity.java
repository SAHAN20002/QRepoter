package lk.sliit.qreporter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2UserprofileActivity extends AppCompatActivity {
    private ImageView backButton;
    private ConstraintLayout constraintLayout_login;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView textView_email;
    TextView textView5;
    DatabaseReference reference;
    ConstraintLayout constraintLayout_logout,save_news,calander_view;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2_userprofile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final LoadingDialog loadingDialog = new LoadingDialog(Main2UserprofileActivity.this);

        auth = FirebaseAuth.getInstance();
        constraintLayout_logout = findViewById(R.id.log_out);
        textView_email = findViewById(R.id.textView_email_show);
        textView5 = findViewById(R.id.textView5);
        user = auth.getCurrentUser();

        constraintLayout_login = findViewById(R.id.login_windows);
        if (user != null) {


            String new_email = user.getEmail();
            textView5.setText(getFirst10Characters(new_email));
            /*String uid = getUserId();*/
           /* if (uid != null) {
                reference = FirebaseDatabase.getInstance().getReference("User").child(uid);
                reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String name = String.valueOf(dataSnapshot.child("name").getValue());
                            textView5.setText(name);
                        } else {
                            Toast.makeText(Main2UserprofileActivity.this, "Failed to load name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(Main2UserprofileActivity.this, "User ID not found", Toast.LENGTH_SHORT).show();
            }*/
        }

         if(user == null) {

             constraintLayout_login.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Intent intent = new Intent(Main2UserprofileActivity.this, Login.class);
                     startActivity(intent);



                 }
             });
         }else {
             textView_email.setText(user.getEmail());
             constraintLayout_login.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Intent intent = new Intent(Main2UserprofileActivity.this, Main3homeActivity.class);
                     startActivity(intent);


                 }

             });
         }
         /*vinuya*/
        builder = new AlertDialog.Builder(this);
         constraintLayout_logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
              if(user!=null) {
                  builder.setTitle("Alert")
                          .setMessage("Do you want to logout?")
                          .setCancelable(true)
                          .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  FirebaseAuth.getInstance().signOut();
                                  Intent intent = new Intent(Main2UserprofileActivity.this, Main3homeActivity.class);
                                  startActivity(intent);
                                  clearUserId();
                              }
                          })
                          .setNegativeButton("No", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }
                          }).show();
              }else{
                  Toast.makeText(Main2UserprofileActivity.this, "You are not login", Toast.LENGTH_SHORT).show();
              }
             }
         });
        /*vinuya*/
        backButton = findViewById(R.id.user_back_button_2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2UserprofileActivity.this,Main3homeActivity.class);
                startActivity(intent);

                loadingDialog.loadingAlert();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                },5000);
            }
        });

        save_news = findViewById(R.id.save_news);
        save_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Alert").setMessage("This feature not available yet")
                        .setCancelable(true)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        calander_view = findViewById(R.id.calander_view);
        calander_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Alert").setMessage("This feature not available yet")
                        .setCancelable(true)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }

        });


    }
    public String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("userId", null);
    }
    // Clear user ID from shared preferences
    private void clearUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userId");
        editor.apply();
    }
    public static String getFirst10Characters(String str) {
        // Check if the string length is at least 10
        if (str.length() >= 10) {
            // Return the first 10 characters
            return str.substring(0, 10);
        } else {
            // Return the entire string if it's shorter than 10 characters
            return str;
        }
    }
}