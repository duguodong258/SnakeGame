package max.com.snakegame.base;

import android.app.Application;
import android.content.Context;

/**
 * @author jelly
 * @TIME 2018/1/4
 * @DES ${TODO}
 */

public class SnakeApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
