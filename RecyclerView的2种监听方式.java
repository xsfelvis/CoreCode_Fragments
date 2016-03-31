 //RecyclerView适配器
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private String[] mCityName;

        public RecyclerViewAdapter(Context mContext) {
            this.mContext = mContext;
            mLayoutInflater = LayoutInflater.from(mContext);
            mCityName = mContext.getResources().getStringArray(R.array.city_name);
        }

        //创建新ViewHolder，被LayoutManager所调用
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerViewViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((RecyclerViewViewHolder) holder).mTextView.setText(mCityName[position]);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mCityName == null ? 0 : mCityName.length;
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public RecyclerViewViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_city);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show(mContext, getPosition() + "");
                    }
                });
            }
        }
    }


NO.2：封装个监听类

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }
}

使用方法:

mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
    @Override
    public void onItemClick(View view, int position) {
         ToastUtil.show(OneActivity.this, position + "");
         }
    }));