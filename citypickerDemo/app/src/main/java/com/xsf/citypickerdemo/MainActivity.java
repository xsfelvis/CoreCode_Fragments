package com.xsf.citypickerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xsf.citypickerdemo.Adapter.CityListAdapter;
import com.xsf.citypickerdemo.Adapter.ResultListAdapter;
import com.xsf.citypickerdemo.db.TestDb;
import com.xsf.citypickerdemo.model.City;
import com.xsf.citypickerdemo.views.SideSelectBar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lv_cityname;
    private ListView lv_result_name;
    private SideSelectBar sideBar;
    private EditText et_searchBox;
    private ImageView iv_clear;
    private ImageView iv_back;
    private ViewGroup vg_empty;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultLsiResultListAdapter;
    private List<City> mAllCities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
     
        mAllCities = TestDb.getAllCities();



        mCityAdapter = new CityListAdapter(this, mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                itemCallback(name);
            }

            @Override
            public void onLocateClick() {

            }
        });

        mResultLsiResultListAdapter = new ResultListAdapter(this, null);

    }


    private void initView() {
        lv_cityname = (ListView) findViewById(R.id.lv_all_city);
        lv_cityname.setAdapter(mCityAdapter);


        TextView text_overlay = (TextView) findViewById(R.id.tv_select_overlay);
        vg_empty = (ViewGroup) findViewById(R.id.empty_view);

        sideBar = (SideSelectBar) findViewById(R.id.side_selectbar);
        sideBar.setOverlay(text_overlay);
        sideBar.setOnSideSelectBarChangedListener(new SideSelectBar.OnSideSelectBarChangedListener() {
            @Override
            public void OnSideSelectBarChanged(String select) {
                int position = mCityAdapter.getLetterPosition(select);
                lv_cityname.setSelection(position); //listview定位到选中的城市
            }
        });

        et_searchBox = (EditText) findViewById(R.id.et_search);
        et_searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    iv_clear.setVisibility(View.GONE);
                    vg_empty.setVisibility(View.GONE);
                    lv_result_name.setVisibility(View.GONE);
                } else {
                    iv_clear.setVisibility(View.VISIBLE);
                }


            }
        });

        lv_result_name = (ListView) findViewById(R.id.lv_search_result);
        lv_result_name.setAdapter(mResultLsiResultListAdapter);
        lv_result_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemCallback(mResultLsiResultListAdapter.getItem(position).getName());
            }
        });


        iv_clear = (ImageView) findViewById(R.id.iv_search_clear);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_clear.setOnClickListener(this);
        iv_back.setOnClickListener(this);


    }

    private void itemCallback(String name) {
        Toast.makeText(MainActivity.this, "点击的城市：" + name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search_clear:
                et_searchBox.setText("");
                iv_clear.setVisibility(View.GONE);
                vg_empty.setVisibility(View.GONE);
                lv_result_name.setVisibility(View.GONE);
                break;
        }

    }
}
