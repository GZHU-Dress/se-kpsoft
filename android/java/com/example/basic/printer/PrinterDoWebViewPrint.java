package com.example.basic.printer;

import android.app.Activity;
import android.content.Context;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.basic.Command;

/**
 * Created by visn on 17-12-8.
 */

public class PrinterDoWebViewPrint implements Command {

    private Activity activity;
    private String html;
    private String jobName;

    public PrinterDoWebViewPrint(Activity activity, String html, String jobName) {
        this.activity = activity;
        this.html = html;
        this.jobName = jobName;
    }

    @Override
    public Object execute() {
        WebView webView = new WebView(activity);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("printer", "page finished loading " + url);
                createWebPrintJob(view);
            }
        });
        //mWebView = webView;
        webView.loadUrl(html);


        return null;
    }

    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) activity
                .getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter("Bill");

        // Create a print job with name and adapter instance
        printManager.print(jobName, printAdapter,null);

    }
}
