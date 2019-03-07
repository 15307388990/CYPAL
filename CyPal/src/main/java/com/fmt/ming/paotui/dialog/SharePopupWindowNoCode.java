package com.fmt.ming.paotui.dialog;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.fmt.ming.paotui.R;
import com.fmt.ming.paotui.config.Const;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * 分享弹框
 */

public class SharePopupWindowNoCode extends PopupWindow implements OnClickListener {

    private Context context;

    private ImageView iv_share_wechat, iv_share_friend;
    String invite_code;
    private LinearLayout rl;
    private LinearLayout ll;
    private View view;
    private Button btn_cancel;
    IWXAPI msgApi;
    private String url;

    public SharePopupWindowNoCode(Context mContext) {
        this.context = mContext;
        msgApi = WXAPIFactory.createWXAPI(mContext.getApplicationContext(), Const.APP_ID, true);
        msgApi.registerApp(Const.APP_ID);
        initView();
        showShareWindow();
    }


    public void showShareWindow() {

        this.setContentView(view);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        // this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

    }


    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.share_popup_window, null);
        iv_share_wechat = (ImageView) view.findViewById(R.id.iv_share_wechat);
        iv_share_friend = (ImageView) view.findViewById(R.id.iv_share_friend);
        rl = (LinearLayout) view.findViewById(R.id.dialog_rootView);
        ll = (LinearLayout) view.findViewById(R.id.dialog_main);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        initListener();
    }

    /**
     * 拓客分享
     */
    public void initCommonShareParams(String url) {
        this.url = url;
    }

    /**
     * @param type 1为 微信聊天界面 2为微信朋友圈
     */
    private void inireq(int type) {
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.title = "街边go|拓客招募令";
        mediaMessage.description = "”街边go“是专业的无人零售解决方案供应商，邀您一同开启无人零售新时代";
        mediaMessage.setThumbImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        mediaMessage.mediaObject = webpageObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = mediaMessage;
        if (type == 1) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        Boolean boolean1 = msgApi.sendReq(req);
    }

    private void initListener() {
        rl.setOnClickListener(this);
        ll.setOnClickListener(this);
        iv_share_friend.setOnClickListener(this);
        iv_share_wechat.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_share_friend:
                inireq(1);
                break;
            case R.id.iv_share_wechat:
                inireq(2);
                break;
            case R.id.dialog_rootView:
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

}
