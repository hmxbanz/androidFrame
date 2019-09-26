package cn.noname.app.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.loader.GlideImageLoader;
import cn.noname.app.server.response.HomeResponse;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.DataHolder>  {


    private List<HomeResponse.ResultEntity> listItems;
    private LayoutInflater layoutInflater;
    private  final int TYPE_HEADER = 0;
    private  final int TYPE_NORMAL = 1;
    private  final int TYPE_FOOTER = 2;
    private View headerView;
    private View footerView;
    private ItemClickListener listener;
    private RecyclerView recyclerView;
    private GlideImageLoader glideImageLoader;
    private Context context;

    public HomeRecyclerViewAdapter(List<HomeResponse.ResultEntity> l, Context c){
        this.listItems=l;
        this.context=c;
        this.layoutInflater=LayoutInflater.from(c);
        glideImageLoader=new GlideImageLoader();
    }

    public void setListItems(List<HomeResponse.ResultEntity> listItems) {
        this.listItems = listItems;
    }
    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);//告知Adapter首位置项变动了
    }

    public View getFooterView() {
        return footerView;
    }
    public void setFooterView(View headerView) {
        footerView = headerView;
        notifyItemInserted(0);//告知Adapter首位置项变动了
    }

    public View getHeaderView() {
        return headerView;
    }



    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(headerView != null && viewType == TYPE_HEADER)
        {
            return new DataHolder(headerView);
        }
        else if(footerView != null &&viewType == TYPE_FOOTER)
        {
          return new DataHolder(footerView);
        }
        else {
            View v = layoutInflater.inflate(R.layout.recyclerview_listitem, parent, false);
            return new DataHolder(v);
        }
    }
    @Override
    public void onBindViewHolder(DataHolder holder, final int position) {

        if(getItemViewType(position) == TYPE_FOOTER) return;

        final int pos = getRealPosition(holder);
        final HomeResponse.ResultEntity listItem = listItems.get(position);
        if(holder instanceof DataHolder) {
            holder.nickName.setText("("+listItem.getCityName()+listItem.getAge()+"岁)");
            glideImageLoader.displayImage(context,NnyConst.IMGURI+listItem.getIconSmall(),holder.imageView);
            //Glide.with(context).load(listItem.getAvatar()).asBitmap().into(holder.imageView);
            //holder.imageView.setImageResource(listItem.getImgResource());
            if(listener == null) return;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position,String.valueOf(listItem.getUserInfoID()));
                }
            });
        }


    }
    @Override
    public int getItemCount() {
        int count = (listItems == null ? 0 : listItems.size());
        if (headerView != null)   count++;
        if (footerView != null)   count++;
        return count;
    }
    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (this.recyclerView == null && this.recyclerView != recyclerView) {
                this.recyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ifGridLayoutManager() {
        if (recyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }
    private boolean isHeaderView(int position) {
        return (headerView != null) && (position == 0);
    }

    private boolean isFooterView(int position) {
        return (footerView != null) && (position == getItemCount() - 1);
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }
    public interface ItemClickListener {
        void onItemClick(int position, String data);
    }
    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView nickName;
        private ImageView imageView;
        private View listLayoutView;

        public DataHolder(View itemView) {
            super(itemView);
            nickName = (TextView) itemView.findViewById(R.id.list_item_text);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_icon);
            listLayoutView = itemView.findViewById(R.id.list_item_layout);
        }

        public View getListLayoutView() {
            return listLayoutView;
        }
        public void setListLayoutView(View listLayoutView) {
            this.listLayoutView = listLayoutView;
        }
        public ImageView getImageView() {
            return imageView;
        }
        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
        public TextView getNickName() {
            return nickName;
        }
        public void setNickName(TextView nickName) {
            this.nickName = nickName;
        }

        @Override
        public void onClick(View v) {
            //listener.onItemClick(getAdapterPosition(),"a");
            switch (v.getId())
            {
                case R.id.list_item_layout:


            }
        }
    }
}
