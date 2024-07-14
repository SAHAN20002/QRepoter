package lk.sliit.qreporter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import lk.sliit.qreporter.Models.NewsApiResponse;
import lk.sliit.qreporter.Models.NewsHeadlines;

public class Main3homeActivity extends AppCompatActivity implements SelectListener{
    final LoadingDialog loadingDialog = new LoadingDialog(Main3homeActivity.this);
    RecyclerView recyclerView;
    Registraion registraion;
    CustomAdapter customAdapter;
    ImageView btn_5,btn_6,btn_7;

    SearchView searchView;
    TextView textView,textView_refresh;

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;
    UserId userId;
    ConstraintLayout constraintLayout;
    RecyclerView recyclerView2;

    private ImageView userProfilButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        constraintLayout = findViewById(R.id.main);

        Animation animation = AnimationUtils.loadAnimation(Main3homeActivity.this,R.anim.animation);
        Animation animation2 = AnimationUtils.loadAnimation(Main3homeActivity.this,R.anim.animation2);

        constraintLayout.startAnimation(animation);

        textView = findViewById(R.id.textView4);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            /*String uid = getUserId();*/
             String Email = user.getEmail();
             String new_email = getFirst10Characters(Email);
             textView.setText(new_email);
            /*if (uid != null) {
                reference = FirebaseDatabase.getInstance().getReference("User").child(uid);
                reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String name = String.valueOf(dataSnapshot.child("name").getValue());
                            textView.setText(name);
                        } else {
                            Toast.makeText(Main3homeActivity.this, "Failed to load name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(Main3homeActivity.this, "User ID not found", Toast.LENGTH_SHORT).show();
            }*/
        }

/*Kaveesha*/
        searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestManager manager = new RequestManager(Main3homeActivity.this);
                manager.getNewsHeadlines(listener,"general",query);
                loadingDialog.loadingAlert();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                },1000);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
 /*Kaveesha*/
        userProfilButton = findViewById(R.id.user_profile_view_button);
        userProfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3homeActivity.this,Main2UserprofileActivity.class);
                startActivity(intent);

            }
        });


  /* Kavesha*/
       btn_5 = findViewById(R.id.btn_5);
       btn_5.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               recyclerView2 = findViewById(R.id.recycler_main);
               recyclerView2.startAnimation(animation2);
               RequestManager manager = new RequestManager(Main3homeActivity.this);
               manager.getNewsHeadlines(listener,"science",null);
               /*loadingDialog.loadingAlert();
               Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       loadingDialog.dismissDialog();
                   }
               },1000);*/


           }
       });
       btn_6 = findViewById(R.id.btn_6);
       btn_6.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               RequestManager manager = new RequestManager(Main3homeActivity.this);
               manager.getNewsHeadlines(listener,"sports",null);
               recyclerView2 = findViewById(R.id.recycler_main);
               recyclerView2.startAnimation(animation2);
               /*loadingDialog.loadingAlert();
               Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       loadingDialog.dismissDialog();
                   }
               },1000);*/

           }
       });
       btn_7 = findViewById(R.id.btn_7);
       btn_7.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               RequestManager manager = new RequestManager(Main3homeActivity.this);
               manager.getNewsHeadlines(listener,"technology",null);
               recyclerView2 = findViewById(R.id.recycler_main);
               recyclerView2.startAnimation(animation2);
               /*loadingDialog.loadingAlert();
               Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       loadingDialog.dismissDialog();
                   }
               },500);*/

           }
       });
/*Kaveesha - end*/
       textView_refresh = findViewById(R.id.textView13);
       textView_refresh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               RequestManager manager = new RequestManager(Main3homeActivity.this);
               manager.getNewsHeadlines(listener,"general",null);
               recyclerView2 = findViewById(R.id.recycler_main);
               recyclerView2.startAnimation(animation2);
               /*loadingDialog.loadingAlert();
               Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       loadingDialog.dismissDialog();
                   }
               },1000);*/

           }
       });

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);
    }

    private  final  OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {

        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(Main3homeActivity.this,"No data Found !",Toast.LENGTH_SHORT).show();
            }else {

                showNews(list);

                /*loadingDialog.loadingAlert();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                },1000);*/
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(Main3homeActivity.this,"An Error Occured !!!",Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        customAdapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void OnNewsCliked(NewsHeadlines newsHeadlines) {
               startActivity(new Intent(Main3homeActivity.this, DetailsActivity.class)
                       .putExtra("data",newsHeadlines));
    }

   /* // Retrieve user ID from shared preferences
    public String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("userId", null);
    }*/
    // Method to get the first 10 characters of a string
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