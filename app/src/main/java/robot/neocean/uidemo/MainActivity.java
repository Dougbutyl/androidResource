package robot.neocean.uidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import adapter.UiListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.uirecyclerview)
    RecyclerView uirecyclerview;

    private UiListAdapter uiListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        uirecyclerview.setLayoutManager(linearLayoutManager);
        uiListAdapter=new UiListAdapter();
        uirecyclerview.setAdapter(uiListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
    public static enum Title {
        KEYBOARDUI("自定义软键盘");

        private String tilteName;

        Title(String tilteName) {
            this.tilteName = tilteName;
        }

        public String getTilteName() {
            return tilteName;
        }

        public void setTilteName(String tilteName) {
            this.tilteName = tilteName;
        }
    }
}
