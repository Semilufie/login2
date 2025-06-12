package com.example.login;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.db.CarDbHelper;
import com.example.login.entity.ProductInfo;
import com.example.login.entity.UserInfo;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView productImg;
    private TextView productName;
    private TextView actor;
    private TextView type;
    private TextView dataTime;
    private TextView location;
    private TextView detail;
    private TextView price;
    private TextView grade;

    private ProductInfo productInfo;
    private Button buttonSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonSelect = findViewById(R.id.button_select);
        // 点击加入购物车
        buttonSelect.setOnClickListener(v -> {
            // 跳转到购物车
            // 加入到购物车
            new AlertDialog.Builder(ProductDetailActivity.this)
                    .setTitle("Spanish jasmine")
                    .setMessage("Add to cart or not")
                    .setPositiveButton("Make certain", (dialog, which) -> {
                        int row = CarDbHelper.getInstance(ProductDetailActivity.this).addCar(UserInfo.getsUserInfo().getUsername(), productInfo.getProductId(), productInfo.getProductImg(), productInfo.getProductName(), productInfo.getPrice());
                        if (row > 0) {
                            Toast.makeText(ProductDetailActivity.this, "Add to Cart Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Failed to add shopping cart", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancellations", (dialog, which) -> {
                    })
                    .show();
        });
        // 获取传递时的数据
        productInfo = (ProductInfo) getIntent().getSerializableExtra("productInfo");

        // 点击返回
        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
        // 初始化控件
        productImg = findViewById(R.id.detail_img);
        productName = findViewById(R.id.detail_name);
        actor = findViewById(R.id.detail_actor);
        type = findViewById(R.id.detail_type);
        dataTime = findViewById(R.id.detail_dataTime);
        location = findViewById(R.id.detail_location);
        detail = findViewById(R.id.detail_content);
        price = findViewById(R.id.detail_price);
        grade = findViewById(R.id.detail_grade);
        // 设置数据
        if (productInfo != null) {
            productImg.setImageResource(productInfo.getProductImg());
            productName.setText(productInfo.getProductName());
            actor.setText(productInfo.getActor());
            type.setText(productInfo.getType());
            dataTime.setText(productInfo.getDataTime());
            location.setText(productInfo.getLocation());
            detail.setText(productInfo.getDetail());
            price.setText(productInfo.getPrice() + "Yuan");
            grade.setText(productInfo.getGrade() + "Fen");
        }
    }
}