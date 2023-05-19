package com.example.coursework.screens.trainings

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coursework.R
import com.example.coursework.databinding.ActivityMainBinding
import com.example.coursework.databinding.FragmentTrainDetailsBinding
import com.firebase.ui.auth.AuthUI.getApplicationContext


class TrainDetails : AppCompatActivity() {

    private lateinit var binding: FragmentTrainDetailsBinding

    private lateinit var webView: WebView


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTrainDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webView = binding.webview.findViewById(R.id.webview)
        webView.settings.builtInZoomControls = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.defaultTextEncodingName = "UTF-8"
        webView.webViewClient = MyWebViewClient()
        webView.clearCache(true)
        WebView.setWebContentsDebuggingEnabled(true)
        webView.loadDataWithBaseURL("file:///android_asset/", "<html><body>Percent test: 100% </body></html>", "text/html", "UTF-8", null)
        //webView.loadUrl("file:///assets/files/index.html")
        //Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
    }

     private class MyWebViewClient : WebViewClient() {
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url!!)
            return false
        }

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return false
        }

        @SuppressLint("RestrictedApi")
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            view?.evaluateJavascript("document.documentElement.innerHTML") { html ->
                Log.d("WebViewContent", html)
            }
            Toast.makeText(getApplicationContext(), "Загрузка завершена", Toast.LENGTH_SHORT)
                .show()
        }


        @SuppressLint("RestrictedApi")
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Toast.makeText(getApplicationContext(), "Начата загрузка страницы", Toast.LENGTH_SHORT)
                .show()
        }
         override fun onReceivedError(
             view: WebView?,
             request: WebResourceRequest?,
             error: WebResourceError?
         ) {
             super.onReceivedError(view, request, error)
             val errorMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                 error?.description?.toString()
             } else {
                 "Unknown error"
             }
             Log.e("WebViewError", "Error: $errorMessage")
         }

     }
}