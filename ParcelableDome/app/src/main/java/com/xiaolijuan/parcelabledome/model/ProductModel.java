package com.xiaolijuan.parcelabledome.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: adan
 * @description:
 * @date: 2016-04-04
 * @time: 12:40
 */
public class ProductModel implements Parcelable {
    public int productId;
    public String productName;
    private float price;
    public boolean isSell;

    public ProductModel(int productId, String productName, float price, boolean isSell) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.isSell = isSell;
    }

    public ProductModel(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        price = in.readFloat();
        isSell = in.readInt() == 1;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isSell() {
        return isSell;
    }

    public void setIsSell(boolean isSell) {
        this.isSell = isSell;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(productName);
        dest.writeFloat(price);
        dest.writeInt(isSell ? 1 : 0);
    }

    public static final Parcelable.Creator<ProductModel> CREATOR = new Creator<ProductModel>() {

        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

}
