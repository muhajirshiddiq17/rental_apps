package com.rental_apps.android.rental_apps.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rental_apps.android.rental_apps.R;
import com.rental_apps.android.rental_apps.SPreferenced.SPref;
import com.rental_apps.android.rental_apps.helper.DateDifference;
import com.rental_apps.android.rental_apps.model.model_carts.DataCarts;
import com.rental_apps.android.rental_apps.user.ActivityListTransaksi;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import customfonts.MyTextView;

/**
 * Created by Muhajir on 08/10/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    private List<DataCarts> cartList;
    Context mContext;
    private int lastPosition=-1;
    private View mView;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private MyTextView nama_mobil,merk_mobil,tanggal,plat;
        private TextView total,cancel;
        private View view;

        public MyViewHolder(View view) {
            super(view);
            mView=view;
            nama_mobil=(MyTextView)view.findViewById(R.id.nama_mobil);
            merk_mobil=(MyTextView)view.findViewById(R.id.merk_mobil);
            tanggal=(MyTextView)view.findViewById(R.id.tanggal);
            plat=(MyTextView)view.findViewById(R.id.plat);
            total=(TextView)view.findViewById(R.id.total);
            cancel=(TextView)view.findViewById(R.id.cancel);
            this.view=view;
        }

        public void bindItem(final DataCarts cart) {
            long jumlah_hari=DateDifference.betweenDates(cart.getTGL_SEWA(),cart.getTGL_AKHIR_PENYEWAAN())+1;
            nama_mobil.setText(cart.getNAMA_MOBIL());
            merk_mobil.setText(cart.getMERK_MOBIL());
            tanggal.setText(cart.getTGL_SEWA()+" s/d "+cart.getTGL_AKHIR_PENYEWAAN()+" ("+ jumlah_hari +" Hari)");
            total.setText("Rp. "+String.format("%,.2f", Double.parseDouble(cart.getTOTAL())));
            plat.setText(cart.getPLAT_NO_MOBIL());
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Won't be able to recover this file!")
                            .setConfirmText("Yes,delete it!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Carts.cancel(SPref.getCARTS(),getAdapterPosition());
                                    cartList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    ((ActivityListTransaksi)mContext).setCheckout();

                                    sDialog
                                            .setTitleText("Deleted!")
                                            .setContentText("Your data has been deleted!")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            })
                            .show();

                }
            });
        }



    }


    public CartAdapter(Context mContext, List<DataCarts> cartList) {
        this.mContext = mContext;
        this.cartList = cartList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_cart,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, int position) {
        holder.bindItem(cartList.get(position));
        setAnimation(mView, position);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    private void setAnimation(View viewToAnimate,int position) {
        if (position > lastPosition) {
            lastPosition = position;
            Animation animation = AnimationUtils.loadAnimation(mView.getContext(), R.anim.slide_left_to_right);
            viewToAnimate.startAnimation(animation);
        }
    }
}


