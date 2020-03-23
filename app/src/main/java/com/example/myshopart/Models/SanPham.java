package com.example.myshopart.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SanPham implements Serializable {

@SerializedName("Masanpham")
@Expose
private String masanpham;
@SerializedName("Tensanpham")
@Expose
private String tensanpham;
@SerializedName("Giaban")
@Expose
private String giaban;
@SerializedName("Kichthuoc")
@Expose
private String kichthuoc;
@SerializedName("Chatkieu")
@Expose
private String chatkieu;
@SerializedName("Hinhanh")
@Expose
private String hinhanh;
@SerializedName("Soluong")
@Expose
private String soluong;
@SerializedName("Loaisanpham")
@Expose
private String loaisanpham;
@SerializedName("Gioitinh")
@Expose
private String gioitinh;

public String getMasanpham() {
return masanpham;
}

public void setMasanpham(String masanpham) {
this.masanpham = masanpham;
}

public String getTensanpham() {
return tensanpham;
}

public void setTensanpham(String tensanpham) {
this.tensanpham = tensanpham;
}

public String getGiaban() {
return giaban;
}

public void setGiaban(String giaban) {
this.giaban = giaban;
}

public String getKichthuoc() {
return kichthuoc;
}

public void setKichthuoc(String kichthuoc) {
this.kichthuoc = kichthuoc;
}

public String getChatkieu() {
return chatkieu;
}

public void setChatkieu(String chatkieu) {
this.chatkieu = chatkieu;
}

public String getHinhanh() {
return hinhanh;
}

public void setHinhanh(String hinhanh) {
this.hinhanh = hinhanh;
}

public String getSoluong() {
return soluong;
}

public void setSoluong(String soluong) {
this.soluong = soluong;
}

public String getLoaisanpham() {
return loaisanpham;
}

public void setLoaisanpham(String loaisanpham) {
this.loaisanpham = loaisanpham;
}

public String getGioitinh() {
return gioitinh;
}

public void setGioitinh(String gioitinh) {
this.gioitinh = gioitinh;
}

}