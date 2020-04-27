package com.example.myshopart.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KhachHang {

@SerializedName("Idkhachhang")
@Expose
private String idkhachhang;
@SerializedName("Email")
@Expose
private String email;

public String getIdkhachhang() {
return idkhachhang;
}

public void setIdkhachhang(String idkhachhang) {
this.idkhachhang = idkhachhang;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

}