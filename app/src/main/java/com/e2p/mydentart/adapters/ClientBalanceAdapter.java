package com.e2p.mydentart.adapters;


import static com.e2p.mydentart.helpers.ConstantConfig.SELECTED_PRIX_PRESTATIONS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.e2p.mydentart.R;
import com.e2p.mydentart.models.BalanceClient;
import com.e2p.mydentart.models.PrixPrestation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ClientBalanceAdapter extends RecyclerView.Adapter<ClientBalanceAdapter.ItemViewHolder> {

    private ArrayList<BalanceClient> mList;
    private Context mContext;
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    public ClientBalanceAdapter(Context context, ArrayList<BalanceClient> mList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance_client_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        BalanceClient model = mList.get(position);

        holder.tvTitle.setText(model.getName());
        holder.tvFact.setText(formatter.format(model.getFacturation()));
        holder.tvReg.setText(formatter.format(model.getReglement()));
        holder.tvBal.setText(formatter.format(model.getBalance()));

//        if ((model.getFacturation() == 0) && (model.getReglement() == 0) && (model.getBalance() == 0)) {
//            holder.rlColor.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.grey_500, null));
//        }

//        if ((model.getFacturation() > 0) && (model.getReglement() == 0) && (model.getBalance() == 0)) {
//            holder.rlColor.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.blue_500, null));
//        }

        if ((model.getFacturation() > 0) && (model.getReglement() > 0) && (model.getBalance() == 0)) {
            holder.rlColor.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.green_500, null));
        }

        if ((model.getFacturation() > 0) && (model.getReglement() > 0) && (model.getBalance() < 0)) {
            holder.rlColor.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.red_500, null));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlMain;
        private RelativeLayout rlColor;
        private AppCompatTextView tvTitle;
        private AppCompatTextView tvFact;
        private AppCompatTextView tvReg;
        private AppCompatTextView tvBal;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMain = itemView.findViewById(R.id.rl_item_balance_client_main);
            rlColor = itemView.findViewById(R.id.rl_balance_client_color_layout_left);
            tvTitle = itemView.findViewById(R.id.tv_item_balance_client_title);
            tvFact = itemView.findViewById(R.id.tv_item_balance_client_fact);
            tvReg = itemView.findViewById(R.id.tv_item_balance_client_reg);
            tvBal = itemView.findViewById(R.id.tv_item_balance_client_bal);
        }
    }
}