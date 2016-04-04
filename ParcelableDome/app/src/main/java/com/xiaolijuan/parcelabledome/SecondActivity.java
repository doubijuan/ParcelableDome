package com.xiaolijuan.parcelabledome;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.xiaolijuan.parcelabledome.model.ProductModel;

import java.util.List;

/**
 * @author: adan
 * @description:
 * @projectName: ParcelableDome
 * @date: 2016-04-04
 * @time: 13:37
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

//        ProductModel productModel = (ProductModel) getIntent().getParcelableExtra("productModel");
//        Log.e("TAG", "产品Id：" + productModel.getProductId() + "，产品名：" + productModel.getProductName() + "，价格：" + productModel.getPrice() + "，销售状态？" + productModel.isSell());

        List<ProductModel> list = getIntent().getParcelableArrayListExtra("productModelList");
        Log.e("TAG",list.size()+"");
    }
}
