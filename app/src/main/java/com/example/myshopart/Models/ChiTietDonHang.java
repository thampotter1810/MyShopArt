package com.example.myshopart.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietDonHang {

@SerializedName("Id")
@Expose
private String id;
@SerializedName("TenSp")
@Expose
private String tenSp;
@SerializedName("Soluong")
@Expose
private String soluong;
@SerializedName("GiaTien")
@Expose
private String giaTien;
@SerializedName("Iddonhang")
@Expose
private String iddonhang;
@SerializedName("Masanpham")
@Expose
private String masanpham;
@SerializedName("Hinhanh")
@Expose
private String hinhanh;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getTenSp() {
return tenSp;
}

public void setTenSp(String tenSp) {
this.tenSp = tenSp;
}

public String getSoluong() {
return soluong;
}

public void setSoluong(String soluong) {
this.soluong = soluong;
}

public String getGiaTien() {
return giaTien;
}

public void setGiaTien(String giaTien) {
this.giaTien = giaTien;
}

public String getIddonhang() {
return iddonhang;
}

public void setIddonhang(String iddonhang) {
this.iddonhang = iddonhang;
}

public String getMasanpham() {
return masanpham;
}

public void setMasanpham(String masanpham) {
this.masanpham = masanpham;
}

public String getHinhanh() {
return hinhanh;
}

public void setHinhanh(String hinhanh) {
this.hinhanh = hinhanh;
}

}