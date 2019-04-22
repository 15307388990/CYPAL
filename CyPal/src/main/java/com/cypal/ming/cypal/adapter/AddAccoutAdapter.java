package com.cypal.ming.cypal.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.bean.AccountEntity;
import com.cypal.ming.cypal.bean.IndexEntity;
import com.cypal.ming.cypal.utils.ImageLoaderUtil;
import com.cypal.ming.cypal.utils.OrderListState;
import com.cypal.ming.cypal.utils.SavePreferencesData;
import com.cypal.ming.cypal.utils.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

/**
 * Created by wood121 on 2017/12/12.
 */

public class AddAccoutAdapter extends RecyclerView.Adapter<AddAccoutAdapter.ViewHoler> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOODER = 2;
    private final Context mContext;
    private List<AccountEntity> mList;
    private OnClickListener onClickListener;
    private SavePreferencesData mSavePreferencesData;
    private View mHeaderView;
    private View mFooderView;


    public AddAccoutAdapter(Context context, List<AccountEntity> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        this.onClickListener = onClickListener;
        mSavePreferencesData = new SavePreferencesData(context);
    }

    /**
     * 定义结果回调接口
     */
    public interface OnClickListener {
        //添加图片
        void AddImg(int position);

    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooderView(View fooderView) {
        mFooderView = fooderView;
        notifyItemInserted(mList.size() + 1);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        if (position > mList.size()) return TYPE_FOODER;
        return TYPE_NORMAL;
    }


    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new ViewHoler(mHeaderView);
        if (mFooderView != null && viewType == TYPE_FOODER)
            return new ViewHoler(mFooderView);
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.account_add_item, parent, false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOODER)
            return;
        int pos = getRealPosition(holder);
        final AccountEntity sellBean = mList.get(pos);
        holder.tv_money1.setText(sellBean.accountMoney + "元");
        if (!TextUtils.isEmpty(sellBean.accountData)) {
            Bitmap bitmap = CodeUtils.createImage(sellBean.accountData, 160, 160, null);
            holder.iv_pay1.setImageBitmap(bitmap);
        }
        holder.iv_pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.AddImg(position);
            }
        });


    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);

            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_HEADER:
                            return 3;
                        case TYPE_NORMAL:
                            return 1;
                        default:
                            return 3;
                    }
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {

        return mHeaderView == null ? mList.size() : mList.size() + 2;
    }


    public void updateAdapter(List<AccountEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private ImageView iv_pay1;
        private TextView tv_money1;


        public ViewHoler(View itemView) {
            super(itemView);
            iv_pay1 = (ImageView) itemView.findViewById(R.id.iv_pay1);
            tv_money1 = (TextView) itemView.findViewById(R.id.tv_money1);
        }
    }
}
