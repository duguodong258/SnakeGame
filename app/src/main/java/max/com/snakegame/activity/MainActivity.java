package max.com.snakegame.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import max.com.snakegame.R;
import max.com.snakegame.base.BaseActivity;
import max.com.snakegame.util.AnimUtil;


public class MainActivity extends BaseActivity {

    private int timeDown = 3;
    private TextView mTvTimeCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTimeCountDownAnim();

    }

    private void initView() {
        mTvTimeCountDown = (TextView) findViewById(R.id.tv_timeCountDown);
    }


    private void initTimeCountDownAnim() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(timeDown >= 1){
                    mTvTimeCountDown.setText(String.valueOf(timeDown));
                    AnimUtil.scaleANim(mTvTimeCountDown);
                    handler.postDelayed(this,800);
                }else{
                    mTvTimeCountDown.setVisibility(View.GONE);
                }
                timeDown--;
            }
        }, 100);

    }

}
