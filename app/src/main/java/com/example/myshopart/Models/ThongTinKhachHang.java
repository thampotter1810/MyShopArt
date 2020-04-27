package com.example.myshopart.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThongTinKhachHang {

@SerializedName("Hoten")
@Expose
private String hoten;
@SerializedName("Sdt")
@Expose
private String sdt;
@SerializedName("Diachi")
@Expose
private String diachi;

public String getHoten() {
return hoten;
}

public void setHoten(String hoten) {
this.hoten = hoten;
}

public String getSdt() {
return sdt;
}

public void setSdt(String sdt) {
this.sdt = sdt;
}

public String getDiachi() {
return diachi;
}

public void setDiachi(String diachi) {
this.diachi = diachi;
}

}