package com.xsf.citypickerdemo.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xsf.citypickerdemo.R;
import com.xsf.citypickerdemo.model.City;
import com.xsf.citypickerdemo.model.LocateState;
import com.xsf.citypickerdemo.util.PinyinUtils;
import com.xsf.citypickerdemo.views.WrapHeightGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author hzxushangfei
 * @time Created at 2016/3/2.
 * @email xsf_uestc_ncl@163.com
 */
public class CityListAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private List<City> mCities;
    private HashMap<String, Integer> letterIndex;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;

    public CityListAdapter(Context mContext, List<City> mCities) {
        this.mCities = mCities;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null) {
            mCities = new ArrayList<>();
        }
        mCities.add(0, new City("定位", "0"));
        mCities.add(1, new City("热门", "1"));
        int size = mCities.size();
        letterIndex = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++) {
            //获取当前城市的拼音字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getPinyin());

            //获取上个拼音首字母，若不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).getPinyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndex.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     *
     * @param state
     * @param city
     */
    public void updateLocalState(int state, String city) {
        this.locatedCity = city;
        this.locateState = state;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引位置
     *
     * @param Letter
     * @return
     */
    public int getLetterPosition(String Letter) {
        Integer integer = letterIndex.get(Letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public City getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                //定位相关
                convertView = inflater.inflate(R.layout.view_loacte_city, parent, false);
                ViewGroup container = (ViewGroup) convertView.findViewById(R.id.layout_locate);
                TextView state = (TextView) convertView.findViewById(R.id.tv_located_city);
                switch (locateState) {
                    case LocateState.LOCATING:
                        state.setText("正在定位");
                        break;
                    case LocateState.SUCCESS:
                        state.setText(locatedCity);
                        break;
                    case LocateState.FAILED:
                        state.setText("定位失败");
                        break;
                }
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(CityListAdapter.this, "暂无定位功能", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 1://热门
                convertView = inflater.inflate(R.layout.view_hot_city, parent, false);
                WrapHeightGridView gridview = (WrapHeightGridView) convertView.findViewById(R.id.gridview_hot_city);
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext);
                gridview.setAdapter(hotCityGridAdapter);
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null) {
                            onCityClickListener.onCityClick(hotCityGridAdapter.getItem(position));
                        }
                    }
                });

                break;
            case 2://其他
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) convertView.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) convertView.findViewById(R.id.tv_item_city_listview_name);
                    convertView.setTag(holder);
                } else {
                    holder = (CityViewHolder) convertView.getTag();
                }

                if (position >= 1) {
                    final String city = mCities.get(position).getName();
                    holder.name.setText(city);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getPinyin());
                    String previousLetter = position>=1? PinyinUtils.getFirstLetter(mCities.get(position-1).getPinyin()):"";
                    if (!TextUtils.equals(currentLetter, previousLetter)) {
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    } else {
                        holder.letter.setVisibility(View.GONE);
                    }

                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null) {
                                onCityClickListener.onCityClick(city);
                            }
                        }
                    });
                }


                break;
        }

        return convertView;
    }

    public static class CityViewHolder {
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener {
        void onCityClick(String name);

        void onLocateClick();
    }
}
