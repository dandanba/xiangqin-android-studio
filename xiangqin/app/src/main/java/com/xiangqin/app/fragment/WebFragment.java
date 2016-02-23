package com.xiangqin.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiangqin.app.R;

/**
 * @author Lenovo
 */
public class WebFragment extends BaseFragment {
    private static final String TAG = WebFragment.class.getSimpleName();

    public static WebFragment newInstance(String url) {
        final WebFragment fragment = new WebFragment();
        final Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    private WebView mWebView;

    public WebView getWebView() {
        return mWebView;
    }

    public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        mWebView = (WebView) view.findViewById(R.id.web);
        initView(view);
        return view;
    }

    public void initView(View view) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });

        loadUrl(getArguments().getString("url"));
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }


}
