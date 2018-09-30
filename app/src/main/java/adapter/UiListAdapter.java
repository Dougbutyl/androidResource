package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import robot.neocean.uidemo.MainActivity;
import robot.neocean.uidemo.R;

/**
 * Created by Administrator on 2018/9/26.
 */

public class UiListAdapter extends RecyclerView.Adapter<UiListAdapter.UiListViewHolder> {

    View view = null;

    @Override
    public UiListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uilsitadapter_item, parent, false);
        UiListViewHolder uiListViewHolder = new UiListViewHolder(view);

        return uiListViewHolder;
    }

    @Override
    public void onBindViewHolder(UiListViewHolder holder, int position) {

        holder.tvUititle.setText(MainActivity.Title.values()[position].getTilteName());


    }

    @Override
    public int getItemCount() {
        return MainActivity.Title.values().length;
    }

    class UiListViewHolder extends RecyclerView.ViewHolder {

        TextView tvUititle;

        public UiListViewHolder(View itemView) {
            super(itemView);
            tvUititle = (TextView) itemView.findViewById(R.id.tv_uititle);
        }
    }
}
