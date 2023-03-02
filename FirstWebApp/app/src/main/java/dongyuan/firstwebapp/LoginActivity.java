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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static String TAG = MainActivity.class.getSimpleName();
    private EditText mAccount, mPassword;
    private Button bt_login;
    private String url="http://192.168.137.1:8080/AndroidWeb/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView() {
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        bt_login = (Button) findViewById(R.id.login);
        bt_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        login();
    }

    private void login() {
        String account = mAccount.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder
                .add("username", account)
                .add("userpassword", password)
                .build();
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"failed ====>> "+e.getMessage());
                Looper.prepare();
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                Looper.loop();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG,"Success ===>> "+response.body().string());
                Looper.prepare();
                Looper.prepare();
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                Looper.loop();

                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                Looper.loop();
            }
        });

    }

}
