package cn.noname.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.loader.GlideImageLoader;
import cn.noname.app.server.response.SearchResponse;

/**
 * Created by Bob on 2015/3/26.
 */

public class SearchAdapter extends BaseAdapter {
    private ViewHoler holder;
    private GlideImageLoader glideImageLoader;

    private OnItemClick onItemClick;
    public OnItemClick getOnItemClick() {
        return onItemClick;
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    public interface OnItemClick {
        boolean onItemClick(int position, View view, SearchResponse.ResultEntity entity);

    }

    public SearchAdapter(Context context) {
        super(context);
        glideImageLoader=new GlideImageLoader(GlideImageLoader.ROUNDED);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHoler();
            convertView = inflater.inflate(R.layout.item_user, null);
            holder.nickName = (TextView) convertView.findViewById(R.id.nick_name);
            holder.avatar =  convertView.findViewById(R.id.img_avatar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoler) convertView.getTag();
        }

        final SearchResponse.ResultEntity entity=(SearchResponse.ResultEntity) list.get(position);
        holder.nickName.setText(entity.getNickName());
        String avatar= NnyConst.SERVERURI+entity.getIconSmall();
        glideImageLoader.displayImage(context,avatar,holder.avatar);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position, v, entity);

            }
        });
//        final UserRelationshipResponse.ResultEntity bean = (UserRelationshipResponse.ResultEntity) list.get(position);
//        holder.nickName.setText(bean.getUser().getNickname());
//        if (TextUtils.isEmpty(bean.getUser().getPortraitUri())) {
//            ImageLoader.getInstance().displayImage(RongGenerate.generateDefaultAvatar(bean.getUser().getNickname(), bean.getUser().getId()), holder.avatar, App.getOptions());
//        } else {
//            ImageLoader.getInstance().displayImage(bean.getUser().getPortraitUri(), holder.avatar, App.getOptions());
//        }
//        holder.mMessage.setText(bean.getMessage());
//        holder.mState.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItemButtonClick != null) {
//                    onItemButtonClick.onButtonClick(position, v, bean.getStatus());
//                }
//            }
//        });

        return convertView;
    }


    /**
     * avatar :头像
     * nickName : 昵称
     * iconThumbsup : 查看人数icon
     * createDate : 查看次数
     * item : {"avatar":"","nickName":"","iconThumbsup":"","createDate":""}
     */

    class ViewHoler {
        ImageView avatar;
        TextView nickName;
        ImageView iconThumbsup;
        TextView count;
    }



}
