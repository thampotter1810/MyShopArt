package com.example.myshopart.Services;

import com.example.myshopart.Models.SanPham;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @GET("danhsachsanphamhot.php")
    Call<List<SanPham>> GetDataBanner();
    @GET("danhsachsanphamnulimit.php")
    Call<List<SanPham>> GetLimitProductsGirl();
    @GET("danhsachsanphamnamlimit.php")
    Call<List<SanPham>> GetLimitProductsBoy();
    @GET("danhsachsanphamnu.php")
    Call<List<SanPham>> GetAllProductsGirl(@Query("page") int page);
    @GET("danhsachsanphamnam.php")
    Call<List<SanPham>> GetAllProductsBoy(@Query("page") int page);
}
