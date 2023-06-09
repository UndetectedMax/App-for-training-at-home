package com.example.coursework.screens.trainings

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.coursework.R
import com.example.coursework.databinding.TrainDetailsBinding


class TrainDetails : AppCompatActivity() {

    private lateinit var binding: TrainDetailsBinding

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TrainDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webView = binding.webview.findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.defaultTextEncodingName = "UTF-8"
        webView.webViewClient = MyWebViewClient()
        webView.clearCache(true)
        WebView.setWebContentsDebuggingEnabled(true)
        when (intent.getStringExtra("text").toString()) {
            "Cardio" -> webView.loadUrl("file:///android_asset/files/cardio_trainings.html")
            "Trainings for arms" -> webView.loadUrl("file:///android_asset/files/arm_trainings.html")
            "Trainings for legs" -> webView.loadUrl("file:///android_asset/files/leg_trainings.html")
            "Complex trainings" -> webView.loadUrl("file:///android_asset/files/complex_trainings.html")
            "Trainings for recovery" -> webView.loadUrl("file:///android_asset/files/recovery.html")
        }
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

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            val errorMessage =
                error?.description?.toString()
            Log.e("WebViewError", "Error: $errorMessage")
        }

    }
}