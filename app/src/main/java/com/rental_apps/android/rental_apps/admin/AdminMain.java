package com.rental_apps.android.rental_apps.admin;

import android.support.v4.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.rental_apps.android.rental_apps.ActivityLogin;
import com.rental_apps.android.rental_apps.R;
import com.rental_apps.android.rental_apps.SPreferenced.SPref;
import com.rental_apps.android.rental_apps.api.client;
import com.rental_apps.android.rental_apps.myinterface.InitComponent;
import com.rental_apps.android.rental_apps.utils.move;
import com.squareup.picasso.Picasso;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationLiveo;
import es.dmoral.toasty.Toasty;

/**
 * Created by Muhajir on 29/09/2017.
 */

public class AdminMain extends NavigationLiveo implements OnItemClickListener {
    Context mContext;
    private HelpLiveo mHelpLiveo;

    @Override
    public void onInt(Bundle savedInstanceState) {

        mContext=this;
        // User Information
        this.userName.setText(Prefs.getString(SPref.getNAME(),""));
        this.userEmail.setText(Prefs.getString(SPref.getEMAIL(),""));
        this.userBackground.setImageResource(R.drawable.bg_drawer);
        Picasso.with(mContext)
                .load(client.getBaseUrlImage()+Prefs.getString(SPref.getPHOTO(),""))
                .resize(250, 250)
                .centerCrop()
                .into(this.userPhoto);

        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.dashboard), R.drawable.ic_action_dock);
        mHelpLiveo.add(getString(R.string.pesanan), R.drawable.ic_action_email,10);
        mHelpLiveo.add(getString(R.string.mobil), R.drawable.ic_nav_transport,10);
        mHelpLiveo.add(getString(R.string.user), R.drawable.ic_action_person);
        mHelpLiveo.add(getString(R.string.admin), R.drawable.ic_action_cc_bcc);

        with(this).startingPosition(0)
                .addAllHelpItem(mHelpLiveo.getHelp())
                .selectorCheck(R.color.nliveo_purple_colorPrimaryDark)
                .colorItemDefault(R.color.white)
                .colorItemSelected(R.color.white)
                .backgroundList(R.color.nliveo_black_light)
                .colorItemIcon(R.color.colorAccent)
                .footerItem(getString(R.string.setting), R.drawable.ic_action_settings)
                .footerSecondItem(R.string.logout, R.drawable.ic_action_screen_locked_to_portrait)
                .footerNameColor(R.color.white)
                .footerIconColor(R.color.colorAccent)
                .footerSecondNameColor(R.color.white)
                .footerSecondIconColor(R.color.colorAccent)
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickFooter)
                .setOnClickFooterSecond(onClickFooter)
                .build();

        int position = this.getCurrentPosition();
        this.setElevationToolBar(position != 2 ? 15 : 0);
    }

    @Override
    public void onItemClick(int position) {
        Fragment mFragment=null;
        FragmentManager mFragmentManager = getSupportFragmentManager();

        switch (position){
            case 0:
                mFragment = AdminListCart.newInstance(mHelpLiveo.get(position).getName());
                break;
            case 1:
                mFragment = AdminListCart.newInstance(mHelpLiveo.get(position).getName());
                break;
            case 2:
                mFragment = AdminListCart.newInstance(mHelpLiveo.get(position).getName());
                break;
            case 3:
                mFragment = AdminListUser.newInstance(mHelpLiveo.get(position).getName(),2);
                break;
            case 4:
                mFragment = AdminListUser.newInstance(mHelpLiveo.get(position).getName(),1);
                break;
        }
        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }

        setElevationToolBar(position != 2 ? 15 : 0);
    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Prefs.clear();
            move.moveActivity(mContext,ActivityLogin.class);
        }
    };


}