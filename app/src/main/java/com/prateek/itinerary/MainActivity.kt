
package com.prateek.itinerary

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  private lateinit var webView: WebView
  private var filePathCallback: ValueCallback<Array<Uri>>? = null
  private val pickFiles = registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uris ->
    filePathCallback?.onReceiveValue(uris.toTypedArray()); filePathCallback = null
  }
  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    webView = WebView(this); setContentView(webView)
    val ws = webView.settings
    ws.javaScriptEnabled = true; ws.domStorageEnabled = true; ws.databaseEnabled = true
    ws.allowContentAccess = true; ws.allowFileAccess = true
    ws.allowUniversalAccessFromFileURLs = true; ws.allowFileAccessFromFileURLs = true
    ws.useWideViewPort = true; ws.loadWithOverviewMode = true
    webView.webViewClient = object: WebViewClient() {}
    webView.webChromeClient = object: WebChromeClient() {
      override fun onShowFileChooser(webView: WebView?, cb: ValueCallback<Array<Uri>>?, params: FileChooserParams?): Boolean {
        filePathCallback = cb
        return try { pickFiles.launch(arrayOf("*/*")); true } catch (e: ActivityNotFoundException) { filePathCallback?.onReceiveValue(null); filePathCallback = null; false }
      }
    }
    webView.loadUrl("file:///android_asset/index.html")
  }
  override fun onBackPressed() { if (this::webView.isInitialized && webView.canGoBack()) webView.goBack() else super.onBackPressed() }
}
