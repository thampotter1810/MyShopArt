package com.example.myshopart.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhanXet {

    @SerializedName("Idnhanxet")
    @Expose
    private String idnhanxet;
    @SerializedName("Emailkhachhang")
    @Expose
    private String emailkhachhang;
    @SerializedName("Noidung")
    @Expose
    private String noidung;
    @SerializedName("Masp")
    @Expose
    private String masp;

    public NhanXet(String idnhanxet, String emailkhachhang, String noidung, String masp) {
        this.idnhanxet = idnhanxet;
        this.emailkhachhang = emailkhachhang;
        this.noidung = noidung;
        this.masp = masp;
    }

    public String getIdnhanxet() {
        return idnhanxet;
    }

    public void setIdnhanxet(String idnhanxet) {
        this.idnhanxet = idnhanxet;
    }

    public String getEmailkhachhang() {
        return emailkhachhang;
    }

    public void setEmailkhachhang(String emailkhachhang) {
        this.emailkhachhang = emailkhachhang;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

}