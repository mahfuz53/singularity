package com.Singularity.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.volley.Request;
import com.libizo.CustomEditText;
import com.model.jsondata.login.LoginData;
import com.presenter.utils.AlphaNemericGenerator;
import com.presenter.utils.DataParseVolly;
import com.presenter.utils.FileUtils;
import com.presenter.utils.PromptRunnable;
import com.presenter.view.AlertMessage;
import com.skydoves.elasticviews.ElasticAnimation;
import com.skydoves.elasticviews.ElasticFinishListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    private DataParseVolly dataParse;
    Context con;
    CustomEditText Username;
    CustomEditText Userid;
    double lat;
    double lan;
    private TextView ShowData;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.appColorgreen));
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.login_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.login));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        con             = this;
        dataParse       = new DataParseVolly();

        Username        = findViewById(R.id.Username);
        Userid          = findViewById(R.id.UserID);
        ShowData        = findViewById(R.id.showData);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {

            String id       = (String) extras.get("id");
            String name     = (String) extras.get("name");
            lat             = getIntent().getDoubleExtra("lat",0);
            lan             = getIntent().getDoubleExtra("lan",0);

        }
    }

    public void LoginSubmit(View view) {
        new ElasticAnimation(view).setScaleX(0.9f).setScaleY(0.9f).setDuration(400)
                .setOnFinishListener(new ElasticFinishListener() {
                    @Override
                    public void onFinished() {
                        loadUserInfo();
                    }
                }).doAction();

    }
    private void loadUserInfo()
    {


        String Username_    = Username.getText().toString();
        String UserID_      = Userid.getText().toString();

        if(FileUtils.isEmptyString(Username_))
        {
            FileUtils.showToast(con,"Please Enter Username !");
        }
        else if(FileUtils.isEmptyString(UserID_))
        {
            FileUtils.showToast(con,"Please Enter User Id !");
        }
        else {
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("uid", ""+UserID_);
                jsonObj.put("name", Username_);
                jsonObj.put("latitude",""+ lat);
                jsonObj.put("longitude", ""+lan);
                jsonObj.put("request_id", AlphaNemericGenerator.randomAlphaNumeric(10));

                LoadUrl("http://128.199.215.102:4040/api/attendance",jsonObj,Request.Method.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void LoadUrl(String url_, JSONObject jsonObj_,int requestType)
    {

        dataParse.DataVolleyJson(con,url_,2,jsonObj_,requestType,new PromptRunnable(){
            @SuppressLint("NewApi")
            public void run() {
                boolean value_ = this.getValue();
                LoginData obj_ =  this.getLogin();
               // print.message("val"+value_);
                if(value_)
                {
                    LoadLoginData(obj_);
                }
                else
                {
                    FileUtils.showToast(con,getString(R.string.sorry));
                }
            }
        });
    }

    public void LoadLoginData(LoginData obj) {

        if (obj != null) {

            String uid = obj.getdata().uid.toString();
            String name = obj.getdata().name.toString();
            String latitude = obj.getdata().latitude.toString();
            String longitude = obj.getdata().longitude.toString();
            String request_id = obj.getdata().requestId.toString();
            String created_at = obj.getdata().createdAt.toString();
            String updated_at = obj.getdata().updatedAt.toString();

            String data_ = "uid : "+uid+"\n"+
                    "name : "+ name+"\n"+
                    "latitude : "+latitude+"\n"+
                    "longitude : "+longitude+"\n"+
                    "request_id : "+ request_id+"\n"+
                    "created_at : "+created_at+"\n"+
                    "updated_at : "+longitude+"\n";

            ShowData.setText(data_);
        } else {
            AlertMessage.showMessage(con, con.getString(R.string.app_name), getString(R.string.sorry));
        }

    }
}
