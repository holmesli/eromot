package com.app.tomore.beans;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;

public class SystemUtils {
	
	/** æ£?Ÿ¥SDå¡çŠ¶æ€?**/
    public static boolean isSdCardAvailable() {
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState != null) {
            return externalStorageState.equals(Environment.MEDIA_MOUNTED);
        }

        return false;
    }
	
	//æ£?Ÿ¥SDå¡æ˜¯å¦æ˜¯å¯æ’æ‹”çš„ã€?    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        
        return true;
    }
    
    public static boolean hasGingerbread() {
        return false;
     }
	
	public static boolean hasFroyo() {
	    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
     }
	
}
