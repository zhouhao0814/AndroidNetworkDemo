package com.example.testdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdemo.domain.CommentItem;
import com.example.testdemo.domain.GetTextItem;
import com.example.testdemo.domain.MomentItem;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Handler mHandler;
    public static final int WHAT_LOADER_RESULT = 1;
    private ResultAdapter mResultAdapter;
    private RecyclerView mResultList;
    private Button mButton;
    private Button mBtnJson;
    private Button mLoadImage;
    private Button mPostResult;
    private ImageView mImageResult;
    private TextView mPostResult1;
    private String result = null;
    String BASE_URL = "http://192.168.0.2:9102";
    private Request mRequest;
    private OkHttpClient mOkHttpClient;
    
    //Activity start .
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.btn_data);
        mBtnJson = findViewById(R.id.btn_json);
        mLoadImage = findViewById(R.id.btn_image);
        mPostResult = findViewById(R.id.btn_post_result);
        mPostResult1 = findViewById(R.id.text_result_post);
        
        mHandler = new Handler(){
            @Override
            public void handleMessage( Message msg) {
                switch (msg.what){
                    case WHAT_LOADER_RESULT:
                        MomentItem result = (MomentItem) msg.obj;
                        refreshResultList(result);
                        break;
                }
            }
        };
        initView();
    }
    
    private void refreshResultList(MomentItem result) {
        Log.d(TAG,"refreshResultList---");
        mResultAdapter.setData(result);
    }
    
    private void initView() {
        mResultList = findViewById(R.id.result_list);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mResultAdapter = new ResultAdapter();
        mResultList.setAdapter(mResultAdapter);
        
    }
    
    public void loadjson(View view){
        mButton.setVisibility(View.GONE);
        mBtnJson.setVisibility(View.GONE);
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   
                   URL url = new URL("http://192.168.0.2:9102/get/text");
//                   URL url = new URL("http://sunofbeaches.com/shop/api/discovery/categories");
                   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                   connection.setConnectTimeout(1000);
                   connection.setRequestMethod("GET");
                   connection.setRequestProperty("Accept-Language", "zh-CN,zh");
//                   connection.setRequestProperty("Accept-Encoding","gzip,deflate");
                   connection.setRequestProperty("Accept","*/*");
                   Log.d(TAG, "1111111");
                   connection.connect();
                   Log.d(TAG, "1111112");
                   //结构码
                   int responseCode = connection.getResponseCode();
                   Log.d(TAG, "responseCode"+responseCode);
                   //拿头部信息
                   if (responseCode == 200) {
                       Map<String, List<String>> headerFields = connection.getHeaderFields();
                       //遍历map集合的方法
                       Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
                       for (Map.Entry<String, List<String>> entry : entries) {
                           Log.d(TAG, entry.getKey() + " == " + entry.getValue());
                       }
                   }
                   InputStream inputStream = connection.getInputStream();
                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                   String json = bufferedReader.readLine();
                   Log.d(TAG, "content   " + json);
//                   Gson gson = new Gson();
//                   GetTextItem getTextItem = gson.fromJson(json, GetTextItem.class);
//                   Log.d(TAG, "content   " + json);
//                   updateUI(getTextItem);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           private void updateUI(GetTextItem getTextItem) {
               //这个就是子线程不能更新UI
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {

                   }
               });
           }
       }).start();
    }
    
    public void loadData(View view){
        mButton.setVisibility(View.GONE);
        mBtnJson.setVisibility(View.GONE);
        mPostResult1.setVisibility(View.GONE);
        mResultList.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(("https://www.sunofbeach.net/content/content/moment/list/1153952789488054272/1"));
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(1000);
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                        String line = bufferedReader.readLine();
                        Log.d(TAG, "line -- >" + line);
                        bufferedReader.close();
                        Message message = mHandler.obtainMessage();
                        message.what = WHAT_LOADER_RESULT;
                        Gson gson = new Gson();
                        message.obj = gson.fromJson(line, MomentItem.class);
                        mHandler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void loadImage(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
               
                try {
                    URL url = new URL("https://imgs.sunofbeaches.com/group1/M00/00/05/rBsADV2rEz-AIzSoAABi-6nfiqs456.png");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    runOnUiThread(new Runnable() {
    
               
    
                        @Override
                        public void run() {
                            mImageResult = findViewById(R.id.image_result);
                            mImageResult.setVisibility(View.VISIBLE);
                            mImageResult.setImageBitmap(bitmap);
                            
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void postRequest(View view){
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream outputStream = null;
                InputStream inputStream = null;
                try {
                    URL url = new URL("http://192.168.0.2:9102/post/comment");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(1000);
                    httpURLConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept","application/json, text/plain, */*");
                    CommentItem commentItem = new CommentItem("234134123","你的文章写得也太好了！");
                    Gson gson = new Gson();
                    String jsonStr =  gson.toJson(commentItem);
                    byte[] bytes = jsonStr.getBytes("UTF-8");
                    Log.d(TAG, "jsonStr length -- >" +  bytes.length);
                    httpURLConnection.setRequestProperty("Content-Length",String.valueOf(bytes.length));
                    //连接
                    httpURLConnection.connect();
                    //把数据给服务
                    outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(bytes);
                    outputStream.flush();
                    //拿结果
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == httpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        result = bufferedReader.readLine();
                      
                    }
                    runOnUiThread(new Runnable() {//一定要在这里设置，不然显示不出来UI。
                        @Override
                        public void run() {
//                            mImageResult.setVisibility(View.GONE);
                            mPostResult1.setVisibility(View.VISIBLE);
                            mPostResult1.setText(result);
                            Log.d(TAG,"result -- > " + result);
                        }
                    });
    
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
//                    IOUtils.ioClose(inputStream);
//                    IOUtils.ioClose(outputStream);
                }
        
            }
        }).start();
        
    }
    public void postWithParams(View view){
        Map<String, String> params = new HashMap<>();
        params.put("string", "这是我提交的字符串");
        startRequest(params,"POST","/post/string");
    }
    public void getWithParams(View view){
        Map<String, String> params = new HashMap<>();
        params.put("keyword","这是我的关键字Keyword");
        params.put("page","12");
        params.put("order", "0");
        startRequest(params,"GET","/get/param");
    }
    
    private void startRequest(final Map<String, String> params, final String method, final String api) {
        new Thread(new Runnable() {
    
            private BufferedReader mBufferedReader;
            private URL mUrl;
    
            @Override
            public void run() {
                try {
                    //组装参数
                    StringBuilder sb = new StringBuilder();
                    if (params != null && params.size() > 0) {
                        sb.append("?");
                        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> next = iterator.next();
                            sb.append(next.getKey());
                            sb.append("=");
                            sb.append(next.getValue());
                            if (iterator.hasNext()) {
                                sb.append("&");
                            }
                        }
                        Log.d(TAG,"sb result -->" + sb.toString());
                    }
                    String params = sb.toString();
                 
                    
                    if (params != null && params.length() > 0) {
                        mUrl = new URL(BASE_URL + api + params);
//                        Log.d(TAG,"mUrl1" +mUrl);
                    }else {
                        mUrl = new URL(BASE_URL + api);
//                        Log.d(TAG,"mUrl2" +mUrl);
                    }
                    Log.d(TAG,"mUrl" +mUrl.toString());
                    HttpURLConnection httpURLConnection = (HttpURLConnection) mUrl.openConnection();
                    httpURLConnection.setRequestMethod(method);
                    httpURLConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept","*/*");
                    httpURLConnection.connect();
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        mBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String json = mBufferedReader.readLine();
                        Log.d(TAG,"result -=-=- >" + json);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (mBufferedReader != null) {
                        try {
                            mBufferedReader.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
    
    public void downFile(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                InputStream inputStream = null;
//                FileOutputStream fileOutputStream = null;
//                try {
//                    URL url = new URL(BASE_URL + "/download/11");
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.setConnectTimeout(10000);
//                    httpURLConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
//                    httpURLConnection.setRequestProperty("Accept","*/*");
//                    httpURLConnection.connect();
//                    int responseCode = httpURLConnection.getResponseCode();
//                    if (responseCode == httpURLConnection.HTTP_OK) {
//                        String headerField = httpURLConnection.getHeaderField("\"Content-disposition\"");
//                        Log.d(TAG,"headerField == > " + headerField);
//                        String fileName = headerField.replace("attachment; filename=","");
//                        Log.d(TAG,"fileName -- > " + fileName);
//                        File picFile =  RequestTestActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//                        if (!picFile.exists()) {
//                            picFile.mkdirs();
//                        }
//                        File file = new File(picFile + File.separator + fileName);
//                        if (!file.exists()) {
//                            file.createNewFile();
//                        }
//                        fileOutputStream = new FileOutputStream(file);
//                        inputStream = httpURLConnection.getInputStream();
//                        byte[] buffer = new byte[1024];
//                        int len;
//                        while((len = inputStream.read(buffer,0,buffer.length)) != -1){
//                          fileOutputStream.write(buffer,0,len);
//                        }
//                        fileOutputStream.flush();

//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }finally {
////                    IOUtils.ioClose(inputStream);
////                    IOUtils.ioClose(fileOutputStream);
//                }
                Toast.makeText(MainActivity.this,"麻烦你别再点我了，再点你也不会得到我！！！",Toast.LENGTH_SHORT).show();
            }
        }).start();
    }
    public void asyncGet(View view){
        //获取商城的分类信息
        String url = "https://www.sunofbeach.net/shop/api/discovery/categories";
        //创建client，理解为创建浏览器
        mOkHttpClient = new OkHttpClient();
        //创建请求内容
        mRequest = new Request.Builder()
                .url(url)
                .get()
                .build();
    }
    
    //用浏览器创建调用任务
//    Call mCall = OkHttpClient.newCall(mRequest);
//    mCall.enqueue(new Handler.Callback(){
//
//    });
    Call mCall = mOkHttpClient.newCall(mRequest);
    //执行任务
    
}
