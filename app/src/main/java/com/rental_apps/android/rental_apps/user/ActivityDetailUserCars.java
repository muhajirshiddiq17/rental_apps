package com.rental_apps.android.rental_apps.user;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rental_apps.android.rental_apps.R;
import com.rental_apps.android.rental_apps.SPreferenced.SPref;
import com.rental_apps.android.rental_apps.adapter.Carts;
import com.rental_apps.android.rental_apps.api.client;
import com.rental_apps.android.rental_apps.helper.DateDifference;
import com.rental_apps.android.rental_apps.helper.DatePickerView;
import com.rental_apps.android.rental_apps.helper.DrawableColor;
import com.rental_apps.android.rental_apps.helper.DrawableCounter;
import com.rental_apps.android.rental_apps.model.model_carts.DataCarts;
import com.rental_apps.android.rental_apps.model.model_mobil.DataCars;
import com.rental_apps.android.rental_apps.myinterface.InitComponent;
import com.rental_apps.android.rental_apps.utils.move;
import com.rental_apps.android.rental_apps.utils.validate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import customfonts.MyEditText;
import customfonts.MyTextView;
import es.dmoral.toasty.Toasty;

/**
 * Created by Muhajir on 08/10/2017.
 */

public class ActivityDetailUserCars extends AppCompatActivity implements InitComponent, View.OnClickListener{
    private Context mContext;
    private DataCars car;
    private Toolbar toolbar;
    private MyTextView description;
    private TextView merk;
    private TextView year;
    private TextView capacity;
    private TextView plat;
    private TextView warna_mobil;
    private TextView bensin_mobil;
    private TextView price;

    private MyTextView nama_mobil;

    private ImageView ic_merk;
    private ImageView ic_year;
    private ImageView ic_capacity;
    private ImageView ic_plat;
    private ImageView ic_warna_mobil;
    private ImageView ic_bensin_mobil;
    private ImageView mainbackdrop;

    private DatePickerView tgl_awal;
    private DatePickerView tgl_akhir;
    private MyTextView add_cart;

    @Override
    protected void onCreate(Bundle SavedInstance) {
        super.onCreate(SavedInstance);
        setContentView(R.layout.activity_user_detail_cars);
        mContext=this;
        Gson gson = new Gson();
        car= gson.fromJson(getIntent().getStringExtra("car"), DataCars.class);

        startInit();
    }

    @Override
    public void startInit() {
        initToolbar();
        initUI();
        initValue();
        initEvent();
    }

    @Override
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(car.getNAMAMOBIL());
    }

    @Override
    public void initUI() {
        merk        =(TextView)findViewById(R.id.merk);
        year        =(TextView)findViewById(R.id.year);
        plat        =(TextView)findViewById(R.id.plat);
        price       =(TextView)findViewById(R.id.price);
        capacity    =(TextView)findViewById(R.id.capacity);
        warna_mobil =(TextView)findViewById(R.id.warna);
        bensin_mobil=(TextView)findViewById(R.id.bensin);
        description =(MyTextView) findViewById(R.id.description);

        nama_mobil=(MyTextView)findViewById(R.id.nama_mobil);

        ic_merk        =(ImageView) findViewById(R.id.ic_merk);
        ic_year        =(ImageView)findViewById(R.id.ic_year);
        ic_plat        =(ImageView)findViewById(R.id.ic_plat);
        ic_capacity    =(ImageView)findViewById(R.id.ic_capacity);
        ic_warna_mobil =(ImageView)findViewById(R.id.ic_warna);
        ic_bensin_mobil=(ImageView)findViewById(R.id.ic_bensin);
        mainbackdrop   =(ImageView)findViewById(R.id.mainbackdrop);

        tgl_awal=(DatePickerView)findViewById(R.id.tgl_awal);
        tgl_akhir=(DatePickerView)findViewById(R.id.tgl_akhir);
        add_cart=(MyTextView)findViewById(R.id.add_cart);


        Drawable yearIcon= ContextCompat.getDrawable(mContext, R.drawable.ic_action_go_to_today);
        Drawable capacityIcon= ContextCompat.getDrawable(mContext, R.drawable.ic_action_cc_bcc);
        Drawable colorIcon= ContextCompat.getDrawable(mContext, R.drawable.ic_action_picture);
        Drawable fuelIcon= ContextCompat.getDrawable(mContext, R.drawable.ic_action_fuel);
        Drawable merkIcon= ContextCompat.getDrawable(mContext, R.drawable.ic_action_storage);
        Drawable platIcon= ContextCompat.getDrawable(mContext, R.drawable.ic_action_screen_locked_to_landscape);

        ic_year.setImageDrawable(DrawableColor.setColor(yearIcon,R.color.nliveo_orange_colorPrimaryDark));
        ic_capacity.setImageDrawable(DrawableColor.setColor(capacityIcon,R.color.nliveo_orange_colorPrimaryDark));
        ic_warna_mobil.setImageDrawable(DrawableColor.setColor(colorIcon,R.color.nliveo_orange_colorPrimaryDark));
        ic_bensin_mobil.setImageDrawable(DrawableColor.setColor(fuelIcon,R.color.nliveo_orange_colorPrimaryDark));
        ic_merk.setImageDrawable(DrawableColor.setColor(merkIcon,R.color.nliveo_orange_colorPrimaryDark));
        ic_plat.setImageDrawable(DrawableColor.setColor(platIcon,R.color.nliveo_orange_colorPrimaryDark));
    }

    @Override
    public void initValue() {
        description.setText(car.getDESKRIPSIMOBIL());
        merk.setText(car.getMERKMOBIL());
        year.setText(car.getTAHUNMOBIL());
        capacity.setText(car.getKAPASITASMOBIL());
        plat.setText(car.getPLATNOMOBIL());
        warna_mobil.setText(car.getWARNAMOBIL());
        bensin_mobil.setText(car.getBENSINMOBIL());
        price.setText("Rp. "+String.format("%,.2f", Double.parseDouble(car.getHARGAMOBIL().toString())));
        nama_mobil.setText(car.getNAMAMOBIL());
        if (car.getIMAGE().size()>0)
            Picasso.with(mContext).load(client.getBaseImg()+"mobil/"+car.getIMAGE().get(0)).into(mainbackdrop);

    }


    @Override
    public void initEvent() {
        add_cart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_cart:
                if (validasi()){
                    if ((DateDifference.betweenDates(tgl_awal.getText().toString(),tgl_akhir.getText().toString())+1)>0){
                        Carts.order(new DataCarts(car.getIDMOBIL(),car.getNAMAMOBIL(),car.getMERKMOBIL(),car.getPLATNOMOBIL(),tgl_awal.getText().toString(),tgl_akhir.getText().toString(),car.getHARGAMOBIL(),""+(Integer.parseInt(car.getHARGAMOBIL())*(DateDifference.betweenDates(tgl_awal.getText().toString(),tgl_akhir.getText().toString())+1))), SPref.getCARTS());
                        invalidateOptionsMenu();
                        Toasty.success(mContext,"Mobil Berhasil Disimpan",Toast.LENGTH_SHORT).show();
                    }else{
                        Toasty.error(mContext,"Inputan Tanggal Tidak Sesuai",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.menu_user_icon, menu);
        setCart(menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setCart(Menu menu){
        MenuItem menuItem = menu.findItem(R.id.cart);
        MenuItem menuRefresh = menu.findItem(R.id.refresh);
        MenuItem menuSetting= menu.findItem(R.id.action_settings);
        menuRefresh.setVisible(false);
        menuSetting.setVisible(false);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        DrawableCounter badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof DrawableCounter) {
            badge = (DrawableCounter) reuse;
        } else {
            badge = new DrawableCounter(mContext);
        }

        badge.setCount(""+Carts.getSize(SPref.getCARTS()));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.cart:
                move.moveActivity(mContext,ActivityListTransaksi.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validasi(){
        return (!validate.cek(tgl_awal)&&!validate.cek(tgl_akhir)) ? true : false;
    }

    @Override
    public void onResume(){
        super.onResume();
        invalidateOptionsMenu();
    }
}
