package com.example.fabia.doppelkopfnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        final EditText nameText = findViewById(R.id.playerNameEditText);
        final EditText commentText = findViewById(R.id.playerCommentEditText);
        Button addBtn = findViewById(R.id.playerHinzufuegenButton);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String comment = commentText.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("comment",comment);
                setResult(1,intent);
                finish();
            }
        });

    }
}
