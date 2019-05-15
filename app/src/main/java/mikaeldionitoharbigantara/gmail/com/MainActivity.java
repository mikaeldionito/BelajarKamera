package mikaeldionitoharbigantara.gmail.com;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btnCamera;
    ImageView imageView;
    Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cekTulis();
        cekBaca();
        // TODO 1 : Mendeklarasikan sebuah fungsi cekTulis() dan cekBaca() yang digunakan untuk mengerjakan proses Pengecekan Eksternal pada Perangkat.
        btnCamera = findViewById(R.id.btnCamera);
        imageView = findViewById(R.id.imageView);
        // TODO 2 : Mendeklarasikan sebuah variabel btnCamera dan imageView untuk sebuah id pada ImageView dan Button di layout activity_main

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 0);
                // TODO 3 : Mendeklarsaikan fungsi Intent dengan nama takePictureIntent yang digunakan untuk jika Button dengan variabel btnCamera di klik maka akan membuka aplikasi Kamera.
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO 4 : Mendeklarasikan Nilai Parameter ke super Class
        if (resultCode == RESULT_OK) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(imageBitmap);
            // TODO 5 : Mendeklarasikan sebuah Result Jika Gambar yang di capture selesai maka akan ditampilkan di ImageView
            SimpanGambar(imageBitmap);
            // TODO 6 : Mendeklarasikan Fungsi untuk menyimpan Hasil Gamabar Ke Memori Penyimpanan Device.
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            // TODO 7 : Mendeklarasikan Jika gambar tidak jadi disimpan maka akan kembali ke menu awal.
        }
    }
    private void SimpanGambar(Bitmap finalBitmap) {
        File mediaStorageDir= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        // TODO 8 : Mendeklarasikan Lokasi Penyimpanan dari File Gambar yang akan disimpan

        Toast.makeText(this, mediaStorageDir.toString(), Toast.LENGTH_LONG).show();
        // TODO 9 : Mendeklarasikan Toast dimana File Gambar akan disimpan.

        Random generator = new Random();
        int m = 10000;
        m = generator.nextInt(m);
        File fileMik = new File (mediaStorageDir,  "IMG_"+ m +".jpg");
        if (fileMik.exists ())
            fileMik.delete ();
        // TODO 10 : Mendeklarasikan Pembuatan nama secara Random dengan awalah IMG_......jpg

        try {
            FileOutputStream out = new FileOutputStream(fileMik);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            // TODO 11 : Mendeklarsaikan fungsi Kompres File ke dalam JPEG dengan kualitas 90.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void cekTulis() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(this,"READY!",Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this,"FAILED!",Toast.LENGTH_LONG).show();
    }
    // TODO 12 : Mendeklarasikan sebuah Method cekTulis() dengan fungsi untuk Mengetest Ruang Penyimpanan dapat Disimpan Oleh file yang akan disimpan atau tidak jika Bisa Maka Toast READY! akan muncul Jika tidak Maka Toast FAILED Akan Muncul.
    public void cekBaca() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Toast.makeText(this,"OK!",Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this,"FAILURE!",Toast.LENGTH_LONG).show();
        // TODO 13 : Mendeklarasikan sebuah Method cekBaca() yang digunakan untuk test apakah Penyimpanan dapat membaca file yang akan disimpan nantinya.
        // TODO 14 : Terimakasih Tidak Copy langsung RUN - Mikael Dionito.H!!!
    }
}
