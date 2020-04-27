package com.example.myshopart.Services;

import com.example.myshopart.Models.ChiTietDonHang;
import com.example.myshopart.Models.DonHang;
import com.example.myshopart.Models.KhachHang;
import com.example.myshopart.Models.SanPham;
import com.example.myshopart.Models.ThongTinKhachHang;


import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataService {

    @GET("Server/danhsachsanphamhot.php")
    Call<List<SanPham>> GetDataBanner();
    @GET("Server/danhsachsanphamnulimit.php")
    Call<List<SanPham>> GetLimitProductsGirl();
    @GET("Server/danhsachsanphamnamlimit.php")
    Call<List<SanPham>> GetLimitProductsBoy();
    @GET("Server/danhsachsanphamnu.php")
    Call<List<SanPham>> GetAllProductsGirl(@Query("page") int page);
    @GET("Server/danhsachsanphamnam.php")
    Call<List<SanPham>> GetAllProductsBoy(@Query("page") int page);

    @FormUrlEncoded
    @POST("Server/themkhachhang.php")
    Call<String> InsertKhachHang(@Field("username") String username,
                                 @Field("pass") String password,
                                 @Field("email") String email);

    @FormUrlEncoded
    @POST("Server/themthongtinkhachhang.php")
    Call<String> InsertThongTinKhachHang(@Field("hoten") String hoten,
                                 @Field("sdt") String sdt,
                                 @Field("diachi") String diachi, @Field("username") String username);

    @FormUrlEncoded
    @POST("Server/dangnhap.php")
    Call<ArrayList<KhachHang>> LoginUser(@Field("username") String username, @Field("password") String pass);

    @FormUrlEncoded
    @POST("Server/kiemtrathongtinkhachhang.php")
    Call<String> CheckDataUser(@Field("idkhachang") String idkhachhang);

    @FormUrlEncoded
    @POST("Server/laythongtinkhachhang.php")
    Call<ArrayList<ThongTinKhachHang>> GetInforKhachHang(@Field("username") String idkhachhang);

    @FormUrlEncoded
    @POST("Server/thanhtoanoffline.php")
    Call<String> InsertDonHang(@Field("hoten") String hotenkhachhang,
                               @Field("sdt") String sdt,
                               @Field("diachi") String diachi,
                               @Field("email") String email,
                               @Field("trangthai") String trangthai);

    @FormUrlEncoded
    @POST("Server/insertchitietdonhang.php")
    Call<Integer> InsertChiTietDonHang(@Field("tensp") String tensp,
                               @Field("soluong") String soluong,
                               @Field("giatien") String giatien,
                               @Field("iddonhang") String iddonhang,
                               @Field("idsp") String idsp);

    @GET("Server/danhsachdonhang.php")
    Call<ArrayList<DonHang>> GetDanhSachDonHang();

    @GET("Server/danhsachtatcasanpham.php")
    Call<ArrayList<SanPham>> GetAllProducts();

    @FormUrlEncoded
    @POST("Server/xoasanpham.php")
    Call<Integer> DeleteProduct(@Field("masp") String masp);

    @FormUrlEncoded
    @POST("Server/chinhsuasanpham.php")
    Call<Integer> EditProduct(@Field("masp") String masp,
                              @Field("tensp") String tensp,
                              @Field("giaban") String gia,
                              @Field("kichthuoc") String kichthuoc,
                              @Field("chatlieu") String chatlieu,
                              @Field("soluong") String soluong);
    @Multipart
    @POST("Server/uploadsanpham.php")
    Call<String> UploadHinhAnh(@Part MultipartBody.Part hinhanh);

    @GET("Server/danhsachloaisanpham.php")
    Call<ArrayList<String>> GetLoaiSanPham();

    @FormUrlEncoded
    @POST("Server/themsanpham.php")
    Call<Integer> UploadSanPham(@Field("masp") String ma,
                                @Field("tensp") String ten,
                                @Field("giasp") String gia,
                                @Field("kichthuoc") String kt,
                                @Field("chatlieu") String cl,
                                @Field("hinhanh") String hinhanh,
                                @Field("soluong") String sl,
                                @Field("loaisp") String loai,
                                @Field("gioitinh") String gt);

    @FormUrlEncoded
    @POST("Server/tatcasanphamtheoten.php")
    Call<ArrayList<SanPham>> TimKiemSanPham(@Field("timkiem") String tenorma);

    @FormUrlEncoded
    @POST("Server/chitietdonhang.php")
    Call<ArrayList<ChiTietDonHang>> GetChiTietDonHang(@Field("iddonhang") String iddonhang);

    @FormUrlEncoded
    @POST("Server/xacnhandonhang.php")
    Call<Integer> XacNhanDonHang(@Field("iddonhang") String iddonhang);


}
