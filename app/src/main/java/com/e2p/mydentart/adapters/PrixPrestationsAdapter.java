package com.e2p.mydentart.adapters;


import static com.e2p.mydentart.helpers.ConstantConfig.SELECTED_PRIX_PRESTATIONS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.e2p.mydentart.models.PrixPrestation;
import com.e2p.mydentart.R;

import java.util.ArrayList;

public class PrixPrestationsAdapter extends RecyclerView.Adapter<PrixPrestationsAdapter.ItemViewHolder> {

    private ArrayList<PrixPrestation> mList;
    private Context mContext;

    public PrixPrestationsAdapter(Context context, ArrayList<PrixPrestation> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prix_prestation_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        PrixPrestation model = mList.get(position);

        holder.tvTitle.setText(model.getName());
        holder.tvTotal.setText(String.valueOf(model.getPrix()));
        holder.cbxSelected.setChecked((SELECTED_PRIX_PRESTATIONS != null) && (SELECTED_PRIX_PRESTATIONS.contains(model)));

        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SELECTED_PRIX_PRESTATIONS.contains(model)) {
                    SELECTED_PRIX_PRESTATIONS.remove(model);
                } else {
                    SELECTED_PRIX_PRESTATIONS.add(model);
                }

                holder.cbxSelected.setChecked(SELECTED_PRIX_PRESTATIONS.contains(model));

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
        private AppCompatCheckBox cbxSelected;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMain = itemView.findViewById(R.id.rl_item_prix_prestation_main);
            tvTitle = itemView.findViewById(R.id.tv_item_prix_prestation_title);
            tvTotal = itemView.findViewById(R.id.tv_item_prix_prestation_total);
            cbxSelected = itemView.findViewById(R.id.cbx_item_prix_prestation);
        }
    }
}