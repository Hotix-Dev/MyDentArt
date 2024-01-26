package com.e2p.mydentart.adapters;


import static com.e2p.mydentart.helpers.ConstantConfig.SELECTED_CLIENT_BALANCE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.e2p.mydentart.R;
import com.e2p.mydentart.activites.BalanceDetailsActivity;
import com.e2p.mydentart.models.BalanceClient;
import com.e2p.mydentart.models.BalanceClientDetail;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailBalanceAdapter extends RecyclerView.Adapter<DetailBalanceAdapter.ItemViewHolder> {

    private ArrayList<BalanceClientDetail> mList;
    private Context mContext;
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    public DetailBalanceAdapter(Context context, ArrayList<BalanceClientDetail> mList) {
        this.mContext = context;
        this.mList = mList;

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance_detail_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        BalanceClientDetail model = mList.get(position);

        if (model.getFacturation() > 0) {
            holder.rlColor.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.green_500, null));
            holder.tvHint.setText("F");
        }
        else if (model.getReglement() > 0) {
            holder.rlColor.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.red_500, null));
            holder.tvHint.setText("R");
        }
        else {
            holder.rlColor.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.grey_500, null));
            holder.tvHint.setText("-");
        }

        holder.tvTitle.setText(model.getDate());
        holder.tvAmmount.setText(formatter.format(model.getFacturation()));
        holder.tvRef.setText(model.getReference());
        holder.tvSolde.setText(formatter.format(model.getSolde()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlMain;
        private RelativeLayout rlColor;
        private AppCompatTextView tvHint;
        private AppCompatTextView tvTitle;
        private AppCompatTextView tvAmmount;
        private AppCompatTextView tvRef;
        private AppCompatTextView tvSolde;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMain = itemView.findViewById(R.id.rl_item_balance_detail_main);
            rlColor = itemView.findViewById(R.id.rl_balance_detail_color_layout_left);
            tvHint = itemView.findViewById(R.id.tv_item_balance_detail_hint);
            tvTitle = itemView.findViewById(R.id.tv_item_balance_detail_title);
            tvAmmount = itemView.findViewById(R.id.tv_item_balance_detail_amount);
            tvRef = itemView.findViewById(R.id.tv_item_balance_detail_ref);
            tvSolde = itemView.findViewById(R.id.tv_item_balance_detail_solde);
        }
    }
}