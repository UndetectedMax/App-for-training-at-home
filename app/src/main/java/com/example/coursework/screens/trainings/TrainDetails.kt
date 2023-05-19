package com.example.coursework.screens.trainings

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.coursework.databinding.FragmentTrainDetailsBinding


class TrainDetails : Fragment() {

    private lateinit var binding: FragmentTrainDetailsBinding

    private lateinit var webView: WebView

    private val webViewClient = TrainWebViewClient()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainDetailsBinding.inflate(layoutInflater, container, false)

        webView = binding.webview
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.domStorageEnabled = true
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.webViewClient = webViewClient
        webView.loadUrl("file:///android_asset/index.html")
        val title = arguments?.getString("text").toString()
        //Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
        return binding.root
    }

    class TrainWebViewClient : WebViewClient() {

        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

    }

}