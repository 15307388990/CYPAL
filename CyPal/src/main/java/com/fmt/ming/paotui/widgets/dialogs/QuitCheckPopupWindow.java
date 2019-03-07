package com.fmt.ming.paotui.widgets.dialogs;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ViewFlipper;

import com.fmt.ming.paotui.R;

/**
 * @author Administrator QuitCheckPopupWindow 是否进行退出操作
 */
public class QuitCheckPopupWindow extends PopupWindow implements OnClickListener {

    protected Context mContext;
    private onConfirmListener listener = null;

    private View mMenuView;
    private ViewFlipper viewfipper;

    public QuitCheckPopupWindow(Context context, onConfirmListener listener) {
        super(context);
        this.mContext = context;
        this.listener = listener;

        //展示列表
        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.pop_quit_check, null);

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
        Button btn_colse = (Button) mMenuView.findViewById(R.id.btn_colse);
        Button btn_ok = (Button) mMenuView.findViewById(R.id.btn_ok);
        btn_colse.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_colse:
                dismiss();
                break;
            case R.id.btn_ok:
                listener.onConfirm();
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface onConfirmListener {
        void onConfirm();
    }
}
