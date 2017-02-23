package com.sdacademy.gieysztor.michal.dropboxjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public String id;
    public String name;
    public String surname;
    public String email;
    public String country;


    @BindView(R.id.idData)
    TextView mIdData;

    @BindView(R.id.nameData)
    TextView mNameData;

    @BindView(R.id.surnameData)
    TextView mSurnameData;

    @BindView(R.id.emailData)
    TextView mEmailData;

    @BindView(R.id.countryData)
    TextView mCountryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new DownloadDropboxTask().execute();
        mCountryData.setText(country);



    }

    public class DownloadDropboxTask extends AsyncTask<String, Integer, String> {

        String result = null;


        @Override
        protected String doInBackground(String... params) {

            try {
                result = sendPost();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonString = null;
//            JSONObject jsonName = null;

            try {
                jsonString = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            try {
//                jsonName = new JSONObject(jsonString.getString("name"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            Log.i("JSON", jsonString.toString());
            try {
                id = jsonString.getString("account_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            try {
//                name = jsonName.getString("given_name");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            try {
//                surname = jsonName.getString("familiar_name");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            try {
                email = jsonString.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                country = jsonString.getString("country");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("JSON - email", email);
            super.onPostExecute(result);
        }
    }

    private String sendPost() throws IOException {


        MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.dropboxapi.com/2/users/get_current_account";
        String json = "null";

        RequestBody body = RequestBody.create(jsonMediaType, json);

        Request.Builder builder = new Request.Builder();
        builder.addHeader("Authorization", "Bearer dDMgJ7xRgDAAAAAAAAAAEqcYA8eVmS_U6gKpcFFNM8SroZZe69BgNM36ABaE3Ykg");
        builder.addHeader("Content-Type", "application/json");
        builder.url(url);
        builder.post(body);

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response.body().string();

    }
}
