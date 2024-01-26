package com.e2p.mydentart.adapters;


import static android.graphics.Color.parseColor;
import static com.e2p.mydentart.helpers.ConstantConfig.SELECTED_PRIX_PRESTATIONS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.e2p.mydentart.models.PrixPrestation;
import com.e2p.mydentart.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PrixPrestationsAdapter extends RecyclerView.Adapter<PrixPrestationsAdapter.ItemViewHolder> {

    private ArrayList<PrixPrestation> mList;
    private Context mContext;
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    public PrixPrestationsAdapter(Context context, ArrayList<PrixPrestation> mList) {
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
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prix_prestation_row, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prix_prestation_row_new, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        PrixPrestation model = mList.get(position);

        holder.tvTitle.setText(model.getName());
        holder.tvTotal.setText(formatter.format(model.getPrix()));

        if (SELECTED_PRIX_PRESTATIONS.contains(model)) {
            holder.rlMain.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimary, null));
            holder.tvTitle.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.grey_300, null));
            holder.tvTotal.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.white, null));
        } else {
            holder.rlMain.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.white, null));
            holder.tvTitle.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.grey_700, null));
            holder.tvTotal.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimary, null));
        }


        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SELECTED_PRIX_PRESTATIONS.contains(model)) {
                    SELECTED_PRIX_PRESTATIONS.remove(model);
                    holder.rlMain.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.white, null));
                    holder.tvTitle.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.grey_700, null));
                    holder.tvTotal.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimary, null));
                } else {
                    SELECTED_PRIX_PRESTATIONS.add(model);
                    holder.rlMain.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimary, null));
                    holder.tvTitle.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.grey_300, null));
                    holder.tvTotal.setTextColor(ResourcesCompat.getColor(mContext.getResources(), R.color.white, null));
                }

                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlMain;
        private AppCompatTextView tvTitle;
        private AppCompatTextView tvTotal;
        //private AppCompatCheckBox cbxSelected;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMain = itemView.findViewById(R.id.rl_item_prix_prestation_main);
            tvTitle = itemView.findViewById(R.id.tv_item_prix_prestation_title);
            tvTotal = itemView.findViewById(R.id.tv_item_prix_prestation_total);
            //cbxSelected = itemView.findViewById(R.id.cbx_item_prix_prestation);
        }
    }
}