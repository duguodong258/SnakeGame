package max.com.snakegame.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * @author jelly
 * @TIME 2018/1/3
 * @DES ${TODO}
 */

public class BaseActivity extends AppCompatActivity {

    protected static final String MODE_SPRING = "spring";
    protected static final String MODE_SUMMER = "summer";
    protected static final String MODE_AUTUMN = "autumn";
    protected static final String MODE_WINTER = "winter";
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
