package com.joeyshelton.neatview;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Patterns;
import android.widget.TextView;
import java.util.regex.*;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        // Handle text being sent to this activity
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        }

        // Handle user entering URL manually in UI
        // Find the EditText and Button by their IDs
        EditText urlEditText = findViewById(R.id.urlEditText);
        Button openUrlButton = findViewById(R.id.openUrlButton);

        // Set a click listener on the button to open the URL in WebViewActivity
        openUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString();
                if (!url.isEmpty()) {
                    // Assuming you have a method to validate the URL format
                    // If not, consider adding one to ensure proper URLs are handled
                    Intent webViewIntent = new Intent(MainActivity.this, WebViewActivity.class);
                    webViewIntent.putExtra("url", url);
                    startActivity(webViewIntent);
                }
            }
        });

        // Setup a button for opening the URL from the clipboard
        Button openClipboardUrlButton = findViewById(R.id.openClipboardUrlButton);
        openClipboardUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrlFromClipboard();
            }
        });
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Get URL from user's "Share" action
            String sharedUrl = extractURL(sharedText);

            // Open the URL in a WebView with JavaScript disabled
            Intent webViewIntent = new Intent(this, WebViewActivity.class);
            webViewIntent.putExtra("url", sharedUrl);
            startActivity(webViewIntent);
        }
    }

    String extractURL(String str){
        // Regular Expression to extract URL from the string
        String regex = "\\b((?:https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:, .;]*[-a-zA-Z0-9+&@#/%=~_|])";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        if (m.find())
            return str.substring(m.start(0), m.end(0));
        return "";
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            checkClipboardForUrl();
        }
    }

    private void checkClipboardForUrl() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        TextView clipboardUrlLabel = findViewById(R.id.clipboardUrlLabel);
        Button openClipboardUrlButton = findViewById(R.id.openClipboardUrlButton);

        if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            String pasteData = item.getText().toString();

            // Check if the clipboard content is a URL
            if (Patterns.WEB_URL.matcher(pasteData).matches()) {
                try {
                    // Extract the primary domain from the URL
                    URL url = new URL(pasteData);
                    String host = url.getHost();
                    // Simplify to primary domain if needed, you might adjust this logic as per your requirement
                    String primaryDomain = host.startsWith("www.") ? host.substring(4) : host;

                    // Update the label to show the primary domain
                    clipboardUrlLabel.setText("A link from " + primaryDomain + " was found on your clipboard, open it now?");
                    clipboardUrlLabel.setVisibility(View.VISIBLE);
                    openClipboardUrlButton.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Hide the label and button if URL is invalid
                    clipboardUrlLabel.setVisibility(View.GONE);
                    openClipboardUrlButton.setVisibility(View.GONE);
                }
            } else {
                // Hide the label and button if not a URL
                clipboardUrlLabel.setVisibility(View.GONE);
                openClipboardUrlButton.setVisibility(View.GONE);
            }
        } else {
            // Hide the label and button if clipboard is empty or not text
            clipboardUrlLabel.setVisibility(View.GONE);
            openClipboardUrlButton.setVisibility(View.GONE);
        }
    }


    private void openUrlFromClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            String url = item.getText().toString();
            if (Patterns.WEB_URL.matcher(url).matches()) {
                Intent webViewIntent = new Intent(MainActivity.this, WebViewActivity.class);
                webViewIntent.putExtra("url", url);
                startActivity(webViewIntent);
            }
        }
    }

}