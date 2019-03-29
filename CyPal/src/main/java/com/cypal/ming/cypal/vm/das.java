package com.cypal.ming.cypal.vm;

import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.cypal.ming.cypal.bindingdata.BindingAdapterItemType;

public class das implements BindingAdapterItemType, Observable {
//   "id": 6,
//           "createTime": "2019-03-30 00:44:21",
//           "deleted": false,
//           "rwpId": 3,
//           "orderNo": "RW201903300044218622151",
//           "amount": 1100,
//           "withdrawUserId": 10,
//           "withdrawUserNickName": "3",
//           "rechargeUserId": 18,
//           "paymentVoucher": null,
//           "payAccountJson": "[{\"accountData\":\"62108755431255\",\"accountName\":\"中国农业银行\",\"accountType\":\"BANKCARD\",\"createTime\":1553786815000,\"deleted\":false,\"id\":4,\"realName\":\"test@merchant\",\"used\":false,\"userId\":10}]",
//           "remark": null,
//           "statusEnum": "TRADING",
//           "confirmTime": null,
//           "serviceTime": null,
//           "successTime": null,
//           "cancelTime": null
    private String amount;
    private String orderNo;
    //private String amount;

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
        return 0;
    }
}
