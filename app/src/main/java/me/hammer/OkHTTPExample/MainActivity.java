package me.hammer.OkHTTPExample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();
    }

    public void btnGetRequest(View view) {
        final TextView txtView_GET = findViewById(R.id.txtView_GET);
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtView_GET.setText("HTTP call failed: " + e);
                    }
                });
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            txtView_GET.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void btnPostRequest(View view) {
        final TextView txtView_POST = findViewById(R.id.txtView_POST);
        MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

        //Nyt JSONObject som vi kan sende.
        JSONObject postData = new JSONObject();
        try {
            postData.put("userId", 2);
            postData.put("title", "test123");
            postData.put("body", "23124");
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        //JSONObject til plaintext som vi propper ind i en RequestBody
        RequestBody postBody = RequestBody.create(MEDIA_TYPE, postData.toString());

        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .post(postBody)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtView_POST.setText("HTTP Post failed: " + e);
                    }
                });
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            txtView_POST.setText(response.toString());
                    }
                });
            }
        });
    }

    public void btnDeleteRequest(View view){
        final TextView txtView_DELETE = findViewById(R.id.txtView_DELETE);
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
                .delete()
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtView_DELETE.setText("HTTP Delete failed: " + e);
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            txtView_DELETE.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
