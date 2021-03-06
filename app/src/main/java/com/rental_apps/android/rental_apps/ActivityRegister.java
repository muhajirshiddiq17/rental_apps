package com.rental_apps.android.rental_apps;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.rental_apps.android.rental_apps.api.client;
import com.rental_apps.android.rental_apps.model.model_user.DataUser;
import com.rental_apps.android.rental_apps.model.model_user.ResponseRegister;
import com.rental_apps.android.rental_apps.myinterface.InitComponent;
import com.rental_apps.android.rental_apps.utils.validate;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muhajir on 03/09/2017.
 */
public class ActivityRegister extends AppCompatActivity implements InitComponent, View.OnClickListener{

    //declare component
    private EditText etNama;
    private EditText etUsername;
    private EditText etNumber;
    private EditText etAlamat;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Character JK;
    private RadioButton rbl;
    private RadioButton rbp;
    private Button btnRegister;
    private CoordinatorLayout coordinatorLayout;

    //declare context
    private Context mContext;

    //declare variable
    private DataUser userData;

    //declare sweet alert
    private SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext=this;
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
        getSupportActionBar().hide();
    }

    @Override
    public void initUI() {
        etNama=(EditText)findViewById(R.id.et_nama);
        etEmail=(EditText)findViewById(R.id.et_email);
        etNumber=(EditText)findViewById(R.id.et_no_telp);
        etAlamat=(EditText)findViewById(R.id.et_alamat);
        etUsername=(EditText)findViewById(R.id.et_username);
        etPassword=(EditText)findViewById(R.id.et_password);
        etConfirmPassword=(EditText)findViewById(R.id.et_confirm_password);
        rbl=(RadioButton)findViewById(R.id.jkl);
        rbp=(RadioButton)findViewById(R.id.jkp);
        btnRegister=(Button)findViewById(R.id.btn_register);

    }

    @Override
    public void initValue() {

    }

    @Override
    public void initEvent() {
        btnRegister.setOnClickListener(this);
        rbl.setOnClickListener(this);
        rbp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                if(validasi())
                    register();
                break;
            case R.id.jkl:
                JK='L';
                rbp.setChecked(false);
                break;
            case R.id.jkp:
                JK='P';
                rbl.setChecked(false);
                break;
        }
    }

    private void register(){
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<ResponseRegister> register;
        register = client.getApi().userRegister(etNama.getText().toString(),
                                                etUsername.getText().toString(),
                                                etEmail.getText().toString(),
                                                etNumber.getText().toString(),
                                                JK,
                                                etAlamat.getText().toString(),
                                                etPassword.getText().toString(),
                                                1,2);

        register.enqueue(new Callback<ResponseRegister>() {

            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                pDialog.hide();
                if (response.isSuccessful()){
                    if (response.body().getStatus()) {
                        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Info")
                                .setContentText("Akun Berhasil Di Buat!")
                                .show();
                    }else {
                        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Info")
                                .setContentText("Akun Gagal Di Buat!")
                                .show();
                    }
                }else{
                    new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Info")
                            .setContentText("Akun Gagal Di Buat!")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                pDialog.hide();
                new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Koneksi bermasalah!")
                        .show();
            }
        });
    }

    private Boolean validasi(){
        if (!validate.cek(etNama)
         &&!validate.cek(etUsername)
         &&!validate.cek(etEmail)
         &&!validate.cek(etNumber)
         &&!validate.cek(etAlamat)
         &&!validate.cek(etPassword)
         &&!validate.cek(etConfirmPassword)) {
            if (validate.cekPassword(etConfirmPassword,etPassword.getText().toString(),etConfirmPassword.getText().toString())){
                return false;
            }else{
                return true;
            }
        } else{ return false; }
    }
}
