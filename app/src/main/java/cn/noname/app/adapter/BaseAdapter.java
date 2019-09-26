package cn.noname.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected Context context;
    protected List<T> list;
    protected LayoutInflater inflater;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BaseAdapter(Context context) {
        this(context, new ArrayList());
    }

    public BaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.list = data;
        inflater = LayoutInflater.from(this.context);
    }

    public Context getContext() {
        return this.context;
    }

    public void addData(T data) {
        if (data != null) {
            this.list.add(data);
        }
    }
    public void addData(T data, int position) {
        if (data != null)
        this.list.add(position, data);
    }
    public void addData(Collection<T> data) {
        if (data != null) {
            this.list.addAll(data);
        }
    }
    public void addData(T ... collection) {

        for (T t : collection) {
            this.list.add(t);
        }
    }
    public void addData(int index, Collection<T> data) {
        if (data != null) {
            this.list.addAll(index, data);
        }
    }

    public void removeData(Collection<T> data) {
        if (data != null) {
            this.list.removeAll(data);
        }
    }
    public void removeAll() {
        this.list.clear();
    }
    public void remove(T data) {
        if (data != null) {
            this.list.remove(data);
        }
    }
    public void remove(int position) {
        this.list.remove(position);
    }

    public List<T> subData(int index, int count) {
        return this.list.subList(index, index + count);
    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;

        return this.list.size();
    }

    @Override
    public T getItem(int position) {
        if (list == null)
            return null;

        if (position >= list.size())
            return null;

        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
/*    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = newView(context, position, parent);
        }
        bindView(view, position, getItem(position));
        return view;
    }*/

    public List<T> getList() {
        return list;
    }
    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }
    public int findPosition(T message) {
        int index = getCount();
        int position = -1;
        while (index-- > 0) {
            if (message.equals(getItem(index))) {
                position = index;
                break;
            }
        }
        return position;
    }
    public int findPosition(long id) {
        int index = getCount();
        int position = -1;
        while (index-- > 0) {
            if (getItemId(index) == id) {
                position = index;
                break;
            }
        }
        return position;
    }
    public void setList(List<T> mList) {
        this.list = mList;
    }

//    protected abstract View newView(Context context, int position, ViewGroup group);
//    protected abstract void bindView(View v, int position, T data);
}
