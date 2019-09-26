package cn.noname.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.loader.GlideImageLoader;
import cn.noname.app.server.response.ContactsResponse;
import cn.noname.app.server.response.CountryResponse;

/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
public class FriendListAdapter extends BaseAdapter implements SectionIndexer {
    private final GlideImageLoader glideImageLoader;
    private Context mContext;
    private List<CountryResponse> list;
    private ItemClickListener itemClickListener;



    public interface ItemClickListener {
        void OnItemClick(CountryResponse entity);
    }
    public void setItemClickListener(ItemClickListener i){
        itemClickListener=i;
    }

    public FriendListAdapter(Context context, List<CountryResponse> list) {
        this.mContext = context;
        this.list = list;
        glideImageLoader=new GlideImageLoader(GlideImageLoader.ROUNDED);
    }


    /**
     * 传入新的数据 刷新UI的方法
     */
    public void updateListView(List<CountryResponse> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list == null)
            return null;

        if (position >= list.size())
            return null;

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final CountryResponse mContent = list.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_friend, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.friendname);
            viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            viewHolder.mImageView =  convertView.findViewById(R.id.frienduri);
            viewHolder.tvUserId = (TextView) convertView.findViewById(R.id.friend_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            String letterFirst = mContent.getNameSpelling();
            if(!TextUtils.isEmpty(letterFirst)){
                letterFirst = String.valueOf(letterFirst.toUpperCase().charAt(0));
            }
            viewHolder.tvLetter.setText(letterFirst);
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        final CountryResponse entity=list.get(position);
        viewHolder.tvTitle.setText(entity.getCountryName()+"("+entity.getCountryCode()+")");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.OnItemClick(entity);
            }
        });

        //glideImageLoader.displayImage(mContext,NnyConst.SERVERURI+mContent.getObjectIcon(),viewHolder.mImageView);
//        if (mContent.isExitsDisplayName()) {
//            viewHolder.tvTitle.setText(this.list.get(position).getDisplayName());
//        } else {
//            viewHolder.tvTitle.setText(this.list.get(position).getName());
//        }
        //String portraitUri = SealUserInfoManager.getInstance().getPortraitUri(list.get(position));
        //ImageLoader.getInstance().displayImage(portraitUri, viewHolder.mImageView, App.getOptions());
//        if (mContext.getSharedPreferences("config", Context.MODE_PRIVATE).getBoolean("isDebug", false)) {
//            viewHolder.tvUserId.setVisibility(View.VISIBLE);
//            viewHolder.tvUserId.setText(list.get(position).getObjectNickName());
//        }
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getLetters();
            char firstChar = sortStr.charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getLetters().charAt(0);
    }


    final static class ViewHolder {
        /**
         * 首字母
         */
        TextView tvLetter;
        /**
         * 昵称
         */
        TextView tvTitle;
        /**
         * 头像
         */
        ImageView mImageView;
        /**
         * userid
         */
        TextView tvUserId;
    }
}
