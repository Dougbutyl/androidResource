package tools;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/9/30.
 */

public class KeyboardUtil {


    /** 
     * 禁止Edittext弹出软件盘，光标依然正常显示。 
     */
    public static void disableShowSoftInput(EditText editText){
        if(android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        }else{
            Class<EditText>cls=EditText.class;
            Method method;
            try{
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                method=cls.getMethod("setSoftInputShownOnFocus",boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }



}
