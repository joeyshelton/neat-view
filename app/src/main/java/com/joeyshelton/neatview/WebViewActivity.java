package com.joeyshelton.neatview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WebViewActivity extends AppCompatActivity {

    private String currentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webview);
        Button archiveButton = findViewById(R.id.archiveButton);

        webView.getSettings().setJavaScriptEnabled(false);

        // Get the URL passed from MainActivity
        currentUrl = getIntent().getStringExtra("url");
        if (currentUrl != null) {
            webView.loadUrl(currentUrl);
        }

        archiveButton.setOnClickListener(v -> {
            if (currentUrl != null) {
                try {
                    String archiveUrl = "https://archive.ph/submit/?url=" +
                            URLEncoder.encode(currentUrl, StandardCharsets.UTF_8.toString());
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(archiveUrl));
                    startActivity(browserIntent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
