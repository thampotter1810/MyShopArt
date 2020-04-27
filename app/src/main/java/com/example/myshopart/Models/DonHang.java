package com.example.myshopart.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DonHang implements Serializable {

    @SerializedName("Iddonhang")
    @Expose
    private String iddonhang;
    @SerializedName("Tenkhachhang")
    @Expose
    private String tenkhachhang;
    @SerializedName("Sodienthoai")
    @Expose
    private String sodienthoai;
    @SerializedName("Diachi")
    @Expose
    private String diachi;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Trangthai")
    @Expose
    private String trangthai;

    public String getIddonhang() {
        return iddonhang;
    }

    public void setIddonhang(String iddonhang) {
        this.iddonhang = iddonhang;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

}