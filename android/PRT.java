package MsgMgr;


import com.example.msgmgr.R;

import android.app.Activity;
import android.content.Context;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PRT {
	private final static String TAG = "printer";

	private Activity activity;
	/**
	 * 
	 * @param Activity 
	 * @param html
	 */
	public void doWebViewPrint(Activity act,String html) {
		activity=act;
	    // Create a WebView object specifically for printing
	    WebView webView = new WebView(activity);
	    webView.setWebViewClient(new WebViewClient() {

	            @Override
	            public void onPageFinished(WebView view, String url) {
	                Log.i(TAG, "page finished loading " + url);
	                createWebPrintJob(view);
	            }
	    });
	    //mWebView = webView;
	    webView.loadUrl(html);        
	}
	
	private void createWebPrintJob(WebView webView) {

	    // Get a PrintManager instance
	    PrintManager printManager = (PrintManager) activity
	            .getSystemService(Context.PRINT_SERVICE);

	    // Get a print adapter instance
	    PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter("Bill");

	    // Create a print job with name and adapter instance
	    String jobName = Integer.toString(R.string.app_name) + " Document";
	    printManager.print(jobName, printAdapter,null);

	}
}
