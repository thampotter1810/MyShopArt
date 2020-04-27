package com.example.myshopart.Fragment.quanly;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Session2Command;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myshopart.R;
import com.example.myshopart.Services.APIRetrofitClient;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;
import com.example.myshopart.unlimit.CheckConnection;
import com.google.android.gms.common.api.Result;
import com.squareup.picasso.RequestHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadSanPhamFragment extends Fragment {

    View view;
    ImageView imgupload;
    EditText edma, edten,edgia, edkichthuoc,edchatlieu,edsoluong;
    RadioButton rdnam, rdnu;
    Spinner spinner;
    Button btnupload;
    public static int REQUEST_CODE_IMAGE = 123;
    int STORAGE_PERMISSION_CODE = 234;
    String realpath = "";
    ArrayList<String> listloaisanpham;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_uploadsanpham,container,false);

        anhXa();
        requestStoragePermission();
        getLoaiSanPham();
        catchEventSpinner();
        onClick();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void catchEventSpinner() {

    }

    private void getLoaiSanPham() {
        DataService dataService = APIService.getService();
        Call<ArrayList<String>> callback = dataService.GetLoaiSanPham();
        callback.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                listloaisanpham = new ArrayList<>();
                listloaisanpham = response.body();
//                Log.e("LIST", ""+listloaisanpham.get(0));
                Log.e("AABB", "onResponse: "+ response.body());

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item, listloaisanpham);
                //spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }

    private void onClick() {
        imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!realpath.isEmpty()){
                    final String masanpham = edma.getText().toString();
                    final String tensp = edten.getText().toString();
                    final String gia = edgia.getText().toString();
                    final String kichthuoc = edkichthuoc.getText().toString();
                    final String chatlieu = edchatlieu.getText().toString();
                    final String soluong = edsoluong.getText().toString();
                    final int gt;
                    if (rdnam.isChecked()){
                        gt = 1;
                    }else {
                        gt = 2;
                    }
                    final int loai = spinner.getSelectedItemPosition() + 1;

                    if (masanpham.isEmpty() || tensp.isEmpty() || gia.isEmpty()
                            || kichthuoc.isEmpty() || chatlieu.isEmpty() || soluong.isEmpty()){
                        Toast.makeText(getActivity(), "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else {
                        File file = new File(realpath);
                        String filepath = file.getAbsolutePath();

                        Log.e("FILE", filepath);

                        String[] namefiles = filepath.split("\\.");

                        filepath = namefiles[0] + System.currentTimeMillis()+ "." + namefiles[1];
                        Log.e("FILENAME", filepath);
                        final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/foem-data"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file",filepath,requestBody);

                        DataService dataService = APIService.getService();
                        Call<String> callback = dataService.UploadHinhAnh(part);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (!response.body().equals("Failed")){

                                    String hinhanh = APIService.baseURL + "MyShopArt/"+response.body();

                                    DataService dataService1 = APIService.getService();
                                    Call<Integer> integerCall = dataService1.UploadSanPham(masanpham,tensp,gia,kichthuoc,chatlieu,hinhanh,soluong,String.valueOf(loai),String.valueOf(gt));
                                    integerCall.enqueue(new Callback<Integer>() {
                                        @Override
                                        public void onResponse(Call<Integer> call, Response<Integer> response1) {
                                            if (response1.body() == 1){
                                                Log.e("CCC", "onResponse: "+response1.body());
                                                //CheckConnection.ShowAlert(getActivity(),"Thêm thành công");
                                                edma.setText("");
                                                edten.setText("");
                                                edgia.setText("");
                                                edkichthuoc.setText("");
                                                edchatlieu.setText("");
                                                edsoluong.setText("");
                                                rdnam.setChecked(true);
                                                spinner.setSelection(0);
                                                imgupload.setImageResource(R.drawable.ic_error);
                                                if(new QuanLySanPhamFragment() != null){
                                                    new QuanLySanPhamFragment().updateFragment1ListView();
                                                }else {
                                                    Toast.makeText(getActivity(), "đẽo đc", Toast.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                CheckConnection.ShowAlert(getActivity(), "Mã sản phẩm này đã tồn tại");
                                                edma.setText("");
                                                edten.setText("");
                                                edgia.setText("");
                                                edkichthuoc.setText("");
                                                edchatlieu.setText("");
                                                edsoluong.setText("");
                                                rdnam.setChecked(true);
                                                spinner.setSelection(0);
                                                imgupload.setImageResource(R.drawable.ic_alert);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Integer> call, Throwable t) {

                                        }
                                    });
                                }else {
                                    Toast.makeText(getActivity(), "không thể thêm sản phẩm!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }


                }else {
                    Toast.makeText(getActivity(), "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void requestStoragePermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return;
        }
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(), "permission granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
//            File file = new File(realpath);
   //         String filepath = file.getAbsolutePath();
            Toast.makeText(getActivity(), ""+realpath, Toast.LENGTH_SHORT).show();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgupload.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhXa() {
        imgupload = view.findViewById(R.id.imgupload);
        edma = view.findViewById(R.id.eduploadmasanpham);
        edten = view.findViewById(R.id.eduploadtensp);
        edgia = view.findViewById(R.id.eduploadgiaban);
        edkichthuoc = view.findViewById(R.id.eduploadkichthuoc);
        edchatlieu = view.findViewById(R.id.eduploadchatlieu);
        edsoluong = view.findViewById(R.id.eduploadsoluong);
        rdnam = view.findViewById(R.id.rduploadnam);
        rdnu = view.findViewById(R.id.rduploadnu);
        spinner = view.findViewById(R.id.spinnerupload);
        btnupload = view.findViewById(R.id.btnupload);
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
