package com.xiaolijuan.parcelabledome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaolijuan.parcelabledome.model.ProductModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next2();
            }
        });
    }

    private void next1() {
        ProductModel productModel = new ProductModel(0, "白色T恤", 36, true);
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("productModel",productModel);
        startActivity(intent);
    }
    private void next2() {
        List<ProductModel> productModelList = new ArrayList<ProductModel>();
        ProductModel productModel1 = new ProductModel(0, "白色T恤", 36, true);
        ProductModel productModel2 = new ProductModel(1, "粉色裙子", 48, true);
        productModelList.add(productModel1);
        productModelList.add(productModel2);

        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putParcelableArrayListExtra("productModelList",
                (ArrayList<ProductModel>) productModelList);
        startActivity(intent);
    }
}
