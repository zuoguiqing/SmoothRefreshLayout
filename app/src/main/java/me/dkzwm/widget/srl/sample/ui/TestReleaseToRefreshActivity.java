package me.dkzwm.widget.srl.sample.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import me.dkzwm.widget.srl.RefreshingListenerAdapter;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.IRefreshView;
import me.dkzwm.widget.srl.sample.R;
import me.dkzwm.widget.srl.sample.header.StoreHouseHeader;
import me.dkzwm.widget.srl.utils.PixelUtl;

/**
 * Created by dkzwm on 2017/6/1.
 *
 * @author dkzwm
 */
public class TestReleaseToRefreshActivity extends AppCompatActivity {
    private SmoothRefreshLayout mRefreshLayout;
    private TextView mTextView;
    private Handler mHandler = new Handler();
    private int mCount = 0;
    private StoreHouseHeader mStoreHouseHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_refresh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.release_to_refresh);
        mTextView = (TextView) findViewById(R.id.textView_test_refresh_activity_desc);
        mRefreshLayout = (SmoothRefreshLayout) findViewById(R.id.smoothRefreshLayout_test_refresh_activity);
        mStoreHouseHeader = new StoreHouseHeader(this);
        mStoreHouseHeader.initPathWithString("RELEASE TO REFRESH",PixelUtl.dp2px(this,18),
                PixelUtl.dp2px(this,24));
        mStoreHouseHeader.setTextColor(Color.WHITE);
        mStoreHouseHeader.setPadding(0, PixelUtl.dp2px(this, 20), 0, PixelUtl.dp2px(this, 20));
        mRefreshLayout.setHeaderView(mStoreHouseHeader);
        mRefreshLayout.setOffsetRatioToKeepHeaderWhileLoading(1);
        mRefreshLayout.setRatioOfHeaderHeightToRefresh(1);
        mRefreshLayout.setDisableLoadMore(true);
        mRefreshLayout.setOnRefreshListener(new RefreshingListenerAdapter() {
            @Override
            public void onRefreshBegin(boolean isRefresh) {
                mCount++;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.refreshComplete();
                        String times = getString(R.string.number_of_refresh) + mCount;
                        mTextView.setText(times);
                    }
                }, 2000);
            }
        });
        mRefreshLayout.autoRefresh(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case Menu.FIRST:
                if (mStoreHouseHeader.getStyle() == IRefreshView.STYLE_SCALE)
                    mStoreHouseHeader.setStyle(IRefreshView.STYLE_DEFAULT);
                else
                    mStoreHouseHeader.setStyle(IRefreshView.STYLE_SCALE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.change_style);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TestReleaseToRefreshActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
