package com.androiddev.artemqa.compmasterclientversion.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Order;

import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by artemqa on 01.02.2018.
 */

public class RvAdapterOrderList extends RecyclerView.Adapter<RvAdapterOrderList.PriceViewHolder> {
    private RealmResults<Order> orders;

    public RvAdapterOrderList(RealmResults<Order> orders) {
        this.orders = orders;
    }

    @Override
    public RvAdapterOrderList.PriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_order_list_item, parent, false);
        return new PriceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvAdapterOrderList.PriceViewHolder holder, int position) {
        holder.tvNumber.setText(String.valueOf(orders.get(position).getId()));
        holder.tvCost.setText(String.valueOf(orders.get(position).getPriceListPosition().sum("costPosition")));
        holder.tvDate.setText(String.valueOf( new SimpleDateFormat("yyyy-MM-dd").format(orders.get(position).getDateOrder())));
        holder.tvStatus.setText(orders.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class PriceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvNumber, tvCost, tvDate,tvStatus;

        public PriceViewHolder(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number_item_rv_order_list);
            tvCost = itemView.findViewById(R.id.tv_cost_item_rv_order_list);
            tvDate = itemView.findViewById(R.id.tv_date_item_rv_order_list);
            tvStatus = itemView.findViewById(R.id.tv_status_order_rv_order_list);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

