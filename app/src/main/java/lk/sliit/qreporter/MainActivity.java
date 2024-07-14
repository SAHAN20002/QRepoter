package lk.sliit.qreporter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton ExplorButton;
    ConstraintLayout constraintLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation);
        Animation animation2 = AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation2);

        constraintLayout = findViewById(R.id.main);
        constraintLayout.startAnimation(animation);



        ExplorButton = findViewById(R.id.button_explora);
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
        ExplorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main3homeActivity.class);
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
    }
}