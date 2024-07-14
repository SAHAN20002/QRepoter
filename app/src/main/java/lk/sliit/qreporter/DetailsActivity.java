package lk.sliit.qreporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;

import lk.sliit.qreporter.Models.NewsHeadlines;

public class DetailsActivity extends AppCompatActivity {
  NewsHeadlines headlines;
  String name;
  FirebaseAuth auth;
  FirebaseUser user;
  FirebaseDatabase database;
  DatabaseReference reference;
  ImageView imageView;
  int counter , sum, cheker;
  TextView txt_title,txt_author,txt_time,txt_detail,txt_content,like_counet;
  ImageView img_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        imageView = findViewById(R.id.like);

        txt_title = findViewById(R.id.text_details_title);
        txt_author = findViewById(R.id.text_detail_author);
        txt_time = findViewById(R.id.text_detail_time);
        txt_detail = findViewById(R.id.text_detail_detail);
        txt_content = findViewById(R.id.text_detail_content);
        img_news = findViewById(R.id.img_detail_news);

        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        txt_title.setText(headlines.getTitle());
        txt_author.setText(headlines.getAuthor());
        txt_time.setText(headlines.getPublishedAt());
        txt_detail.setText(headlines.getDescription());
        txt_content.setText(headlines.getContent());
        Picasso.get().load(headlines.getUrlToImage()).into(img_news);
        like_counet = findViewById(R.id.like_counter);

        /*database = FirebaseDatabase.getInstance();
        reference = database.getReference("Like").child("Count");
        reference.setValue("70");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                     name = String.valueOf(dataSnapshot.child("Count").getValue());

                     like_counet.setText(name);
                } else {
                    Toast.makeText(DetailsActivity.this, "Failed to load name", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null){
                    Toast.makeText(DetailsActivity.this, "Like is add", Toast.LENGTH_SHORT).show();


                   /* if(cheker == 0){
                        counter = Integer.parseInt(like_counet.getText().toString());
                        counter++;
                        cheker = 1;

                    } else  {
                        counter--;
                        cheker = 0;
                    }

                    like_counet.setText(String.valueOf(counter));

                    reference.setValue(name);*/
                }else{
                    Intent intent = new Intent(DetailsActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}