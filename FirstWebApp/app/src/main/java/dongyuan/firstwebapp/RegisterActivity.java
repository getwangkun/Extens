package dongyuan.firstwebapp;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static String TAG ="zhuo";
    private EditText usernameet, userpasswordet,reuserpasswordet;
    private Button bt_register;
    //private String url="http://172.16.204.136:8080/Luyou/registerservlet";
    //private String url="http://192.168.31.166:8080/Luyou/registerservlet";
    private String url="http://192.168.137.1:8080/AndroidWeb/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameet=(EditText)findViewById(R.id.usernameid);
        userpasswordet=(EditText)findViewById(R.id.userpassword);
        reuserpasswordet=(EditText)findViewById(R.id.reuserpassword);
        bt_register=(Button)findViewById(R.id.registerid);
        bt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String account = usernameet.getText().toString().trim();
        String password = userpasswordet.getText().toString().trim();
        String repassword=reuserpasswordet.getText().toString().trim();
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder
                .add("name", account)
                .add("password", password)
                .build();
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"failed ====>> "+e.getMessage());
                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG,"Success ===>> "+response.body().string());
                Looper.prepare();
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                Looper.loop();
            }
        });

    }
}
