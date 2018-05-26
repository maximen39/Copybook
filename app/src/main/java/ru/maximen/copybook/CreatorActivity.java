package ru.maximen.copybook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class CreatorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText are;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator);
        initViews();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getIntent().getIntExtra("position", -1);
                    long id = getIntent().getIntExtra("id", -1);
                    String namee = getIntent().getStringExtra("name");
                    String aree = getIntent().getStringExtra("are");


                    if (!are.getText().toString().equalsIgnoreCase(aree) || !name.getText().toString().equalsIgnoreCase(namee)) {
                        Intent intent = new Intent();
                        intent.putExtra("position", position);
                        intent.putExtra("id", id);
                        intent.putExtra("are", are.getText().toString());
                        intent.putExtra("name", name.getText().toString());
                        setResult(RESULT_OK, intent);
                    }
                    finish();
                }
            });
        }
        are.setText(getIntent().getStringExtra("are"));
        name.setText(getIntent().getStringExtra("name"));

    }

    private void initViews() {
        this.toolbar = findViewById(R.id.toolbar);
        this.are = findViewById(R.id.editText8);
        this.name = findViewById(R.id.editText7);
    }


}
