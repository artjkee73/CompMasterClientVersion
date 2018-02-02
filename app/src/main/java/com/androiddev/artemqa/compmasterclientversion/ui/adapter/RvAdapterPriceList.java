package com.androiddev.artemqa.compmasterclientversion.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Price;

import java.util.ArrayList;
import java.util.zip.Inflater;

import io.realm.RealmResults;

/**
 * Created by artemqa on 01.02.2018.
 */

public class RvAdapterPriceList extends RecyclerView.Adapter<RvAdapterPriceList.PriceViewHolder> {
    private RealmResults<Price> prices;
    private boolean isVisibleCB;
    private ArrayList<String> positionIsChecked;

    public RvAdapterPriceList(RealmResults<Price> prices, boolean isVisibleCB, ArrayList<String> namePositionIsChecked) {
        this.prices = prices;
        this.isVisibleCB = isVisibleCB;
        this.positionIsChecked = namePositionIsChecked;
    }

    @Override
    public RvAdapterPriceList.PriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_price_list_item, parent, false);
        return new PriceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvAdapterPriceList.PriceViewHolder holder, int position) {
        final String currentItem = prices.get(position).getNamePosition();
        holder.tvName.setText(prices.get(position).getNamePosition());
        holder.tvPrice.setText(String.valueOf(prices.get(position).getCostPosition()));
        holder.tvHours.setText(String.valueOf(prices.get(position).getTimeExecuting()));
        holder.cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    positionIsChecked.add(currentItem);
                } else if (!compoundButton.isChecked()) {
                    positionIsChecked.remove(currentItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }



    public class PriceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName, tvPrice, tvHours;
        CheckBox cbItem;

        public PriceViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_item_rv_price_list);
            tvPrice = itemView.findViewById(R.id.tv_price_item_rv_price_list);
            tvHours = itemView.findViewById(R.id.tv_hours_item_rv_price_list);
            cbItem = itemView.findViewById(R.id.cb_price_list);
            if (isVisibleCB) {
                cbItem.setVisibility(View.VISIBLE);
                cbItem.setChecked(false);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

