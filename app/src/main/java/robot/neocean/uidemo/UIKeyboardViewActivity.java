package robot.neocean.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import tools.KeyboardUtil;
import uiview.UIKeyboardView;

/**
 * Created by Administrator on 2018/9/26.
 */

public class UIKeyboardViewActivity extends Activity {
    @BindView(R.id.edittext)
    EditText edittext;
    @BindView(R.id.uikeyboardview)
    UIKeyboardView uikeyboardview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uikeyboardview);
        ButterKnife.bind(this);
        init();
    }
    private void init(){


      KeyboardUtil.disableShowSoftInput(edittext);
        uikeyboardview.setOnkeyListener(new UIKeyboardView.OnkeyListener() {

            @Override
            public void onInsert(String contentString) {
                Editable editable=edittext.getText();
                int startPostion=edittext.getSelectionStart();
                editable.insert(startPostion,contentString);

            }

            @Override
            public void onDelete() {
                Editable editable=edittext.getText();
                int startPostion=edittext.getSelectionStart();
                if(editable!=null&&editable.length()>0){
                    if(startPostion>0){
                        editable.delete(startPostion-1,startPostion);
                    }
                }

            }
        });



    }
}
