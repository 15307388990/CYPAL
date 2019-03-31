package com.cypal.ming.cypal.vm;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.widget.TextView;

import com.cypal.ming.cypal.BR;
import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bindingdata.BindingAdapterItemType;
import com.cypal.ming.cypal.utils.TopUpState;

public class OderDetailsVM implements BindingAdapterItemType, Observable {

    /**
     * id : 6
     * createTime : 2019-03-30 00:44:21
     * deleted : false
     * rwpId : 3
     * orderNo : RW201903300044218622151
     * amount : 1100
     * withdrawUserId : 10
     * withdrawUserNickName : 3
     * rechargeUserId : 18
     * paymentVoucher : null
     * payAccountJson : [{"accountData":"62108755431255","accountName":"中国农业银行","accountType":"BANKCARD","createTime":1553786815000,"deleted":false,"id":4,"realName":"test@merchant","used":false,"userId":10}]
     * remark : null
     * statusEnum : TRADING
     * confirmTime : null
     * serviceTime : null
     * successTime : null
     * cancelTime : null
     */

    private int id;
    private String createTime;
    private boolean deleted;
    private int rwpId;
    private String orderNo;
    private String amount;
    private int withdrawUserId;
    private String withdrawUserNickName;
    private int rechargeUserId;
    private String payAccountJson;
    private String remark;
    private String statusEnum;
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
        return R.layout.layout_details_head;
    }

    @Bindable
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
        notifyChange( BR.amount );
    }

    @Bindable
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        notifyChange( BR.orderNo );
    }

    private synchronized void notifyChange(int propertyId) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange( this, propertyId );
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
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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
    public int getRwpId() {
        return rwpId;
    }

    public void setRwpId(int rwpId) {
        this.rwpId = rwpId;
        notifyChange( BR.rwpId );
    }

    @Bindable
    public int getWithdrawUserId() {
        return withdrawUserId;
    }

    public void setWithdrawUserId(int withdrawUserId) {
        this.withdrawUserId = withdrawUserId;
        notifyChange( BR.withdrawUserId );
    }

    @Bindable
    public String getWithdrawUserNickName() {
        return withdrawUserNickName;
    }

    public void setWithdrawUserNickName(String withdrawUserNickName) {
        this.withdrawUserNickName = withdrawUserNickName;
        notifyChange( BR.withdrawUserNickName );
    }

    @Bindable
    public int getRechargeUserId() {
        return rechargeUserId;
    }

    public void setRechargeUserId(int rechargeUserId) {
        this.rechargeUserId = rechargeUserId;
        notifyChange( BR.rechargeUserId );
    }

    @Bindable
    public String getPayAccountJson() {
        return payAccountJson;
    }

    public void setPayAccountJson(String payAccountJson) {
        this.payAccountJson = payAccountJson;
        notifyChange( BR.payAccountJson );
    }

    @Bindable
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
        notifyChange( BR.remark );
    }

    @BindingAdapter(value = {"state"})
    public static void setStateTextView(TextView textView, String state) {
        if (state.equals( TopUpState.TRADING.toString())) {
            textView.setText( "未付款" );
            textView.setBackgroundResource( R.drawable.toup_ff9901_bg );
        } else if (state.equals( TopUpState.CANCEL.toString() )) {
            textView.setText( "已取消" );
            textView.setBackgroundResource( R.drawable.toup_bbbbbb_bg );
        } else if (state.equals( TopUpState.SERVICE.toString() )) {
            textView.setText( "申诉中" );
            textView.setBackgroundResource( R.drawable.toup_3776fb_bg );
        } else if (state.equals( TopUpState.SUCCESS.toString() )) {
            textView.setText( "已完成" );
            textView.setBackgroundResource( R.drawable.toup_00bd00_bg );
        } else {
            textView.setText( "待确认" );
            textView.setBackgroundResource( R.drawable.toup_ff9901_bg );
        }

    }

    @Bindable
    public String getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(String statusEnum) {
        this.statusEnum = statusEnum;
        notifyChange( BR.statusEnum );
    }
}
