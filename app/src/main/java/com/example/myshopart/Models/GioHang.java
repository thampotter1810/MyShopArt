package com.example.myshopart.Models;

public class GioHang {
    public String idSanPham;
    public String tenSanPham;
    public long giasp;
    public String hinhsp;
    public int soluong;

    public GioHang(String idSanPham, String tenSanPham, long giasp, String hinhsp, int soluong) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
        this.soluong = soluong;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
