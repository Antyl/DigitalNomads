package com.antyl.digitalnomadstest.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.antyl.digitalnomadstest.R
import kotlinx.android.synthetic.main.fragment_article_details.*

class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {

    private var webViewState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()

        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                if (articlesDetailsToolbar != null)
                    articlesDetailsToolbar.title = title
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (progressBar != null)
                    progressBar.progress = newProgress
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (progressBar != null)
                    progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (progressBar != null)
                    progressBar.visibility = View.GONE
            }
        }

        webView.canGoBack()
        webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action === MotionEvent.ACTION_UP && webView.canGoBack()) {
                webView.goBack()
                return@OnKeyListener true
            }
            false
        })

        if (webViewState == null) {
            val url = getUrl()

            if (!url.isNullOrEmpty()) {
                webView.loadUrl(url)
            }
        } else {
            webView.restoreState(webViewState!!)
        }
    }

    override fun onPause() {
        webViewState = Bundle()
        webView.saveState(webViewState!!)
        super.onPause()
    }

    private fun getUrl(): String? {
        return arguments?.getString("urlToDetails")
    }

    private fun setToolbar() {
        articlesDetailsToolbar.navigationIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
        articlesDetailsToolbar.setNavigationOnClickListener {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                activity?.onBackPressed()
            }
        }
        setHasOptionsMenu(true)
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String?) =
            ArticleDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("urlToDetails", url)
                }
            }
    }
}