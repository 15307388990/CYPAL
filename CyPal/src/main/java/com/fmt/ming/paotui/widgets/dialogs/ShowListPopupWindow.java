package com.fmt.ming.paotui.widgets.dialogs;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.adapter.DeviceListAdapter;
import com.fmt.ming.paotui.bean.DeviceListBean;

import java.util.List;

/**
 * @author Administrator ShowListPopupWindow 展示列表的对话框
 */
public class ShowListPopupWindow extends PopupWindow implements OnClickListener {

    private final List<DeviceListBean> deviceListBean;
    protected Context mContext;
    private onConfirmListener listener = null;

    private View mMenuView;
    private ViewFlipper viewfipper;

    public ShowListPopupWindow(Context context, onConfirmListener listener, List<DeviceListBean> deviceListBean) {
        super(context);
        this.mContext = context;
        this.listener = listener;
        this.deviceListBean = deviceListBean;

        //展示列表
        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.pop_device_list, null);

        viewfipper = new ViewFlipper(context);
        viewfipper.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        viewfipper.addView(mMenuView);
        viewfipper.setFlipInterval(6000000);
        this.setContentView(viewfipper);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        this.update();

        initViews();
    }

    private void initViews() {
        final List<DeviceListBean> deviceListBeanList = deviceListBean;
        TextView tv_whole_device = (TextView) mMenuView.findViewById(R.id.tv_whole_device);
        tv_whole_device.setOnClickListener(this);
        ListView lv_device_list = (ListView) mMenuView.findViewById(R.id.lv_device_list);
        lv_device_list.setSelector(R.color.transparent);
        lv_device_list.setDividerHeight(0);
        lv_device_list.setDivider(null);
        lv_device_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeviceListBean deviceBean = deviceListBeanList.get(i);
                listener.onConfirm(deviceBean.getBox_id() + "", deviceBean.getDevice_name());
                dismiss();
            }
        });

        if (deviceListBean != null) {
            tv_whole_device.setText("全部设备 (" + deviceListBean.size() + ")");
            DeviceListAdapter deviceListAdapter = new DeviceListAdapter(mContext, deviceListBeanList);
            lv_device_list.setAdapter(deviceListAdapter);
        }

        TextView tv_close = (TextView) mMenuView.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
                dismiss();
                break;
            case R.id.tv_whole_device:
                listener.onConfirm("-1", "全部设备");
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface onConfirmListener {
        void onConfirm(String s, String device_name);
    }
}
