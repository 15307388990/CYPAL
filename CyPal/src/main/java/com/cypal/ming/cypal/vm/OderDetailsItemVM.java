package com.cypal.ming.cypal.vm;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.media.Image;
import android.widget.ImageView;

import com.cypal.ming.cypal.BR;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bindingdata.BindingAdapterItemType;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

public class OderDetailsItemVM implements BindingAdapterItemType, Observable {


    /**
     * accountData : 62108755431255
     * accountName : 中国农业银行
     * accountType : BANKCARD
     * createTime : 1553786815000
     * deleted : false
     * id : 4
     * realName : test@merchant
     * used : false
     * userId : 10
     */

    private String accountData;
    private String accountName;
    private String accountType;
    private long createTime;
    private boolean deleted;
    private int id;
    private String realName;
    private boolean used;
    private int userId;
    private int layout_id;

    private int imgSrc;
    private String typeText;


    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add( callback );
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry != null) {
            propertyChangeRegistry.remove( callback );
        }
    }

    @Override
    public int getViewType() {
        return this.layout_id;
    }

    public void setViewType(int layout_id) {
        this.layout_id = layout_id;
    }

    private synchronized void notifyChange(int propertyId) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange( this, propertyId );
    }

    @Bindable
    public String getAccountData() {
        return accountData;
    }

    public void setAccountData(String accountData) {
        this.accountData = accountData;
        notifyChange( BR.accountData );
    }

    @Bindable
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        notifyChange( BR.accountName );
    }

    @Bindable
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
        notifyChange( BR.accountType );
    }

    @Bindable
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
        notifyChange( BR.createTime );
    }

    @Bindable
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
        notifyChange( BR.deleted );
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyChange( BR.id );
    }

    @Bindable
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
        notifyChange( BR.realName );
    }

    @Bindable
    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
        notifyChange( BR.used );
    }

    @Bindable
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        notifyChange( BR.userId );
    }

    @Bindable
    public int getImgSrc() {
        if (accountType.equals( "BANKCARD" )) {
            imgSrc = R.drawable.icon_banl;

        } else if (accountType.equals( "WXPAY" )) {
            imgSrc = R.drawable.icon_weixin;

        } else if (accountType.equals( "ALIPAY" )) {
            imgSrc = R.drawable.icon_cod_alipay;

        } else {
            imgSrc = R.drawable.icon_yun;

        }
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
        notifyChange( BR.imgSrc );
    }

    @Bindable
    public String getTypeText() {
        if (accountType.equals( "BANKCARD" )) {
            typeText = "银行卡";

        } else if (accountType.equals( "WXPAY" )) {
            typeText = "收款微信";

        } else if (accountType.equals( "ALIPAY" )) {
            typeText = "收款支付宝";

        } else {
            typeText = "收款云支付";

        }
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
        notifyChange( BR.typeText );
    }

    @BindingAdapter(value = {"src"})
    public static void setImgSrc(ImageView imageView, int src) {
        imageView.setImageResource( src );
    }

    @BindingAdapter(value = {"qrcodeSrc"})
    public static void setImgQrcode(ImageView imageView, String qrcodeSrc) {
        imageView.setImageBitmap( CodeUtils.createImage( qrcodeSrc, 150, 150, null ) );
    }
}
