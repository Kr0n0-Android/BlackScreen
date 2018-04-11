package metrixapp.caseonit.blackscreen;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainService extends Service {

    LinearLayout mView;

    public static int STATE;
    public static final int INACTIVE=0;
    public static final int ACTIVE=0;
    // ALPHA R G B
    public static String codcolor="ff000000";

    static{
        STATE=INACTIVE;
    }

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mView = new LinearLayout(this);
        int color=(int)Long.parseLong(codcolor,16);
        mView.setBackgroundColor(color);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                0 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (wm != null) {
            wm.addView(mView, params);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mView!=null){
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            if (wm != null) {
                wm.removeView(mView);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int color=(int)Long.parseLong(codcolor,16);
        mView.setBackgroundColor(color);
        return super.onStartCommand(intent, flags, startId);
    }
}



