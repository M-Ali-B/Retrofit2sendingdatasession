package com.github.malib.retrofit_2sendingdatasession;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.malib.retrofit_2sendingdatasession.data.Post;
import com.github.malib.retrofit_2sendingdatasession.data.remote.APIService;
import com.github.malib.retrofit_2sendingdatasession.data.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mResponseTv;
    private APIService mAPIService;
    private String TAG = "status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText titleEt = (EditText) findViewById(R.id.et_title);
        final EditText bodyEt = (EditText) findViewById(R.id.et_body);
        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        mResponseTv = (TextView) findViewById(R.id.tv_response);


        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEt.getText().toString().trim();
                String body = bodyEt.getText().toString().trim();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)) {

                sendPost(title,body);

                }
                }
        });

    }

    public void sendPost(String title , String body){
        mAPIService.savePost(title,body,1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()){
                    showResponse(response.body().toString());
                    Toast.makeText(MainActivity.this, "postsubmitted successfully "+response.body().toString(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG,"postsubmitted successfully "+response.body().toString());
                }


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {


                Toast.makeText(MainActivity.this, "Unable to submit post to API"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();

Log.i(TAG,"Unable to submit post to API");

            }
        });
    }


    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }


}
