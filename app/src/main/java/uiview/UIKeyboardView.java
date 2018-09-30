package uiview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

import robot.neocean.uidemo.R;

/**
 * Created by Administrator on 2018/9/26.
 */

public class UIKeyboardView extends KeyboardView {
    private static final int KEY_EMPTY = -10;
    private Keyboard keyboardNum = null;
    private Keyboard keyboardCase = null;
    private boolean isPsdNum=false;
    private Context context;

    public UIKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.keyboard);
        String keyboardType = typedArray.getString(R.styleable.keyboard_keyboardtype);
        typedArray.recycle();
        init(context, keyboardType);

    }

    public UIKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.keyboard);
        String keyboardType = typedArray.getString(R.styleable.keyboard_keyboardtype);
        typedArray.recycle();
        init(context, keyboardType);
    }


    private void init(Context context, String keyboardType) {


        if (!TextUtils.isEmpty(keyboardType) && keyboardType.equals("0")) {
            isNum = true;
            isPsdNum=true;
            keyboardNum = new Keyboard(context, R.xml.key_password_number);
            setKeyboard(keyboardNum);
        } else {
            isNum = false;
            isPsdNum=false;
            keyboardCase = new Keyboard(context, R.xml.qwerty);
            keyboardNum = new Keyboard(context, R.xml.key_password_number);
            List<Keyboard.Key> keyList = keyboardNum.getKeys();
            for (Keyboard.Key key : keyList) {
                if (key.codes[0] == -10) {
                    //key.icon = context.getResources().getDrawable(R.mipmap.ic_launcher);
                    key.label = "返回";
                }
            }

            setKeyboard(keyboardCase);
        }

        setEnabled(true);
        setFocusable(true);
        setPreviewEnabled(false);//设置点击按键不显示预览气泡
        setOnKeyboardActionListener(listener);


    }

    boolean isupper = false;//大小写
    boolean isNum = true;//数字键盘

    /**
     *          * 键盘大小写切换         
     */
    private void changeKey() {
        List<Keyboard.Key> keylist = keyboardCase.getKeys();

        if (isupper) {//大写切换小写   
            isupper = false;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {
            //小写切换大写 
            isupper = true;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;

                }
            }
        }
        setKeyboard(keyboardCase);
    }


    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }

    private void drawKeyIcon(Keyboard.Key key, Drawable iconDrawable) {
        Rect keyIconRect = null;
        if (iconDrawable == null) {
            return;
        }
        // 计算按键icon 的rect 范围
        if (keyIconRect == null || keyIconRect.isEmpty()) {
            // 得到 keyicon 的显示大小，因为图片放在不同的drawable-dpi目录下，显示大小也不一样
            int intrinsicWidth = iconDrawable.getIntrinsicWidth();
            int intrinsicHeight = iconDrawable.getIntrinsicHeight();
            int drawWidth = intrinsicWidth;
            int drawHeight = intrinsicHeight;
            // 限制图片的大小，防止图片按键范围
            if (drawWidth > key.width) {
                drawWidth = key.width;
                // 此时高就按照比例缩放
                drawHeight = (int) (drawWidth * 1.0f / intrinsicWidth * intrinsicHeight);
            } else if (drawHeight > key.height) {
                drawHeight = key.height;
                drawWidth = (int) (drawHeight * 1.0f / intrinsicHeight * intrinsicWidth);
            }
            // 获取图片的 x,y 坐标,图片在按键的正中间
            int left = key.x + key.width / 2 - drawWidth / 2;
            int top = key.y + key.height / 2 - drawHeight / 2;
            keyIconRect = new Rect(left, top, left + drawWidth, top + drawHeight);
        }

        if (keyIconRect != null && !keyIconRect.isEmpty()) {
            iconDrawable.setBounds(keyIconRect);
            //iconDrawable.draw(canvas);
        }
    }


    OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int primaryCode, int[] ints) {
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {
                //完成
                setVisibility(View.GONE);
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
                //删除（回退）
                if (onkeyListener != null) {
                    onkeyListener.onDelete();
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
                //大小写切换
                changeKey();
            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
                //数字键盘切换
                if (isNum) {
                    isNum = false;
                    setKeyboard(keyboardCase);
                } else {
                    isNum = true;
                    setKeyboard(keyboardNum);
                }

            } else if (!isPsdNum && primaryCode == -10) {
                setKeyboard(keyboardCase);
            } else {
                if (onkeyListener != null) {
                    onkeyListener.onInsert(Character.toString((char) primaryCode));
                }
            }

        }


        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    private OnkeyListener onkeyListener;

    public void setOnkeyListener(OnkeyListener onkeyListener) {
        this.onkeyListener = onkeyListener;
    }

    public interface OnkeyListener {
        void onInsert(String contentString);

        void onDelete();
    }
}
