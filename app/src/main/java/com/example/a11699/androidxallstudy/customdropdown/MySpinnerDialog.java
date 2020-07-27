package com.example.a11699.androidxallstudy.customdropdown;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.a11699.androidxallstudy.R;
import com.example.a11699.androidxallstudy.myseekbar.ViewUtil;

import java.util.List;

/**
 * Create time 2020/7/23
 * Create Yu
 * description:
 */
public class MySpinnerDialog extends RelativeLayout implements View.OnClickListener {
    Context context;
    TextView textView;
    List<String> datas;
    Dialog dialog;//下拉列表弹框
    int index = -1;//选中下标
    int thiswidth;//控件宽度
    int thisheight;//控件高度
    private OnSpinnerItemClick onSpinnerItemClick;

    public MySpinnerDialog(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public MySpinnerDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public MySpinnerDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_spinner, null);
        textView = inflate.findViewById(R.id.spinner_text);
        inflate.setOnClickListener(this);//设置控件点击事件
        addView(inflate);
    }

    /**
     * 设置选择内容
     *
     * @param index 下标
     */
    public void setIndex(int index) {
        if (index > datas.size() - 1) {
            return;
        }
        this.index = index;
        textView.setText(datas.get(index));
        if (onSpinnerItemClick != null) {//通知页面刷新相关数据
            onSpinnerItemClick.onSpinnerItemClick(index, datas.get(index));
        }
    }

    @Override
    protected void onFinishInflate() {//确保宽高被获取到，没获取到宽高是显示不出列表弹框的（弹框的宽高根据控件的宽高设置的）
        super.onFinishInflate();
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                MySpinnerDialog.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                thiswidth = MySpinnerDialog.this.getMeasuredWidth();
                thisheight = MySpinnerDialog.this.getMeasuredHeight();
                //Log.d("宽度1",""+thiswidth);
                if (datas != null)
                    initData();
            }
        });

    }

    /**
     * 设置通知接口
     *
     * @param onSpinnerItemClick
     */
    public void setOnSpinnerItemClick(OnSpinnerItemClick onSpinnerItemClick) {
        this.onSpinnerItemClick = onSpinnerItemClick;
    }

    /**
     * 设置数据源
     *
     * @param datas
     */
    public void setDatas(List<String> datas) {
        this.datas = datas;
        if (thiswidth != 0) {//判断宽高是否有获取到，如果已获取到宽高则直接更新dialog
            initData();
        }
    }

    private void initData() {
        if (datas.size() > 0) {
            textView.setText(datas.get(0));//设置默认值
            index = 0;//保存默认下标
            View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_my_spinner, null);//下拉列表弹框布局
            ListView listView = inflate.findViewById(R.id.spinner_list);//数据展示列表
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    textView.setText(datas.get(i));//设置选中值
                    index = i;//保存选中下标
                    dialog.dismiss();//隐藏列表
                    if (onSpinnerItemClick != null) {//返回数据
                        onSpinnerItemClick.onSpinnerItemClick(i, datas.get(i));
                    }
                }
            });
            ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();//获得listView的params
            layoutParams.width = thiswidth;//将弹框的宽设置为和控件宽度一样
            layoutParams.height = datas.size() > 5 ? thisheight * 5 : thisheight * datas.size();//如果条数小于等于5条则将弹框高度设置为条数*控件高度，大于5条则设置5个控件的高度
            listView.setLayoutParams(layoutParams);
            listView.setAdapter(new ThisAdapter(context, datas));
            dialog = new Dialog(context, R.style.dialog);//设置弹窗的style
            dialog.setContentView(inflate);
            int[] location = new int[2];
            getLocationOnScreen(location);//获取控件在整个屏幕内的绝对坐标
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);//将弹框显示的位置参数修正为左上角
            //dialogWindow.clearFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            lp.x = location[0]; // 新位置X坐标，即弹框在屏幕中的X值
            lp.y = location[1] + thisheight - ViewUtil.dip2px(22); // 新位置Y坐标，即弹框距离顶部的位置，ContextUtil.dip2px(context,22)为手机状态栏的高度，getLocationOnScreen获取到的位置是包含了状态栏的高度的
            lp.width = layoutParams.width; // 宽度
            lp.height = layoutParams.height; // 高度
            lp.alpha = 1f; // 透明度
            dialogWindow.setAttributes(lp);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onClick(View view) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            } else {
                dialog.show();
            }
        }
    }

    class ThisAdapter extends BaseAdapter {
        Context context;
        List<String> datas;

        public ThisAdapter(Context context, List<String> datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public void notifyDataSetChanged(List<String> array) {
            datas = array;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ThisItem item = null;
            if (view == null) {
                item = new ThisItem();
                view = LayoutInflater.from(context).inflate(R.layout.item_dialog_spinner, null);
                item.t1 = view.findViewById(R.id.spinner_item);
                view.setTag(item);
                ViewGroup.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, thisheight);//将item的高度设置为控件的高度
                //layoutParams.height = thisheight;
                view.setLayoutParams(layoutParams);
            } else {
                item = (ThisItem) view.getTag();
            }
            item.t1.setText(datas.get(i));
            return view;
        }
    }

    class ThisItem {
        TextView t1;
    }

    public interface OnSpinnerItemClick {//返回参数的接口

        /**
         * @param i   选中的下标
         * @param str 对应的内容
         */
        public void onSpinnerItemClick(int i, String str);
    }

}
