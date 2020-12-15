//QR코드를 이용하여 음악 듣기
package com.bdoks54.qrlastfm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button showbio;
    Vibrator vib;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showbio = findViewById(R.id.showbio);
        showbio.setOnClickListener(this);

        vib = (Vibrator)getSystemService(this.VIBRATOR_SERVICE);
        webView = findViewById(R.id.webView);
    }

    @Override
    public void onClick(View v) {
        new IntentIntegrator(this).initiateScan();  //버튼을 클릭하면 QR스캔시작
    }
    public void makeToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //QR스캔 후
        IntentResult result =
                IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        //스캔결과를 받는다
        if (result != null){
            if (result.getContents() != null){
                vib.vibrate(300);   //0.3초간 진동
                String contents = result.getContents(); //경로
                contents = "https://www.youtube.com/result?search_query="+contents;
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(contents);
            }else{
                makeToast("잘못된 URL입니다. QR코드를 다시 확인하세요.");
            }
        }
    }
}