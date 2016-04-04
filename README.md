## Android中Intent传递类对象的方法二(Parcelable)
####上一篇博客写到了通过Serializable方式实现序列化的方法，没有看过的可以点击![Android中Intent传递类对象的方法一(Serializable)](http://blog.csdn.net/qq_20785431/article/details/51053974)，现在我们接着介绍另一种序列化方式：Android中Intent传递类对象的方法二(Parcelable)，Parcelable也是一个接口，只要实现了这个接口，一个类的对象就可以实现序列化并通过Intent和Binder传递。 
####下面看一下实例的ProductModel.java
```Java
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
```
####Parcel内部包装了可序列化的数据，可以在Binder中自由传输，从上面代码中可以看到，在序列化过程中需要实现的功能有序列化、反序列化和功能描述，下面逐一来介绍： 
####（1）序列化功能是由writeToParcel方法来完成，通过Parcel中一系列的的write方法来完成； 
####（2）反序列化功能由CREATOR 来完成，里边标明了如何创建序列化对象和数组，并通过一系列的read方法来完成反序列化过程； 
####（3）内容描述功能由describeContents方法来完成，这个方法一般情况下都返回0；

####下面大致说明一下Parcelable的方法
####createFromParcel(Parcel in)	从序列化后的对象中创建原始对象	
####newArray(int size)	创建指定长度的原始对象数组	
####ProductModel(Parcel in)	从序列化后的对象中创建原始对象	
####writeToParcel(Parcel dest, int flags)	将当前对象写入序列化结构中，flags标识有两种值，分别是0或者1，一般情况都返回0
####describeContents()	返回当前对象的内容描述，如果含有文件描述符就返回1，否则返回0，一般情况返回0
####现在这个实现了Parcelable接口的对象就可以很方便的通过Intent来传递数据啦： 
####（1）可以通过Bundle.putParcelable(Key, Object)方法传递一个对象； 
####（2）可以通过putParcelableArrayListExtra()方法传递一个序列化对象的数组；

####接下来我们简单示范一下第一种方法，通过Bundle.putParcelable(Key, Object)方法传递一个对象 
####首先我们在MainActivity.java中传递实现了Parcelable接口的ProductModel对象
```Java
//序列化过程
ProductModel productModel = new ProductModel(0, "白色T恤", 36, true);
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("productModel",productModel);
        startActivity(intent);
```
####现在在SecondActivity.java中就可以接收到从MainActivity.java传递过来的ProductModel啦
```Java
//反序列化过程
 ProductModel productModel = (ProductModel) getIntent().getParcelableExtra("productModel");
        Log.e("TAG", "产品Id：" + productModel.getProductId() + "，产品名：" + productModel.getProductName() + "，价格：" + productModel.getPrice() + "，销售状态？" + productModel.isSell());
```
####此时在日志中看一下打印出来的信息
```Java
04-04 15:28:28.736    1352-1352/com.xiaolijuan.parcelabledome E/TAG﹕ 产品Id：0，产品名：白色T恤，价格：36.0，销售状态？true
```
####接着我们介绍一下通过putParcelableArrayListExtra()方法传递一个序列化对象的数组，同样我们先在MainActivity.java中传递序列化对象的数组
```Java
//序列化过程
 List<ProductModel> productModelList = new ArrayList<ProductModel>();
        ProductModel productModel1 = new ProductModel(0, "白色T恤", 36, true);
        ProductModel productModel2 = new ProductModel(1, "粉色裙子", 48, true);
        productModelList.add(productModel1);
        productModelList.add(productModel2);

        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putParcelableArrayListExtra("productModelList",
                (ArrayList<ProductModel>) productModelList);
        startActivity(intent);
```
####在SecondActivity.java中我们接收到从MainActivity.java传递过来的数组并打印数组长度
```Java
List<ProductModel> list = getIntent().getParcelableArrayListExtra("productModelList");
        Log.e("TAG",list.size()+"");
```
####打印的日志
```Java
04-04 15:34:50.322    4733-4733/com.xiaolijuan.parcelabledome E/TAG﹕ 2
```
####系统为我们提供了很多例如Intent、Bundle、Bitmap等实现了Parcelable接口的类，它们都是可以直接序列化的。

####有一个小问题，既然Parcelable和Serializable都可以实现序列化并用于Intent间的数据传递，那么两者之间我们应该选取哪个呢？ 
####（1）Serializable是Java中的序列化接口，其使用起来非常简单但是开销很大，因为序列化过程和反序列化过程都需要大量的I/O操作; 
####（2）Parcelable是Android推荐的序列化方式，所以更适合使用在Android平台上，但是这个使用过程会稍显复杂，但是它的效率很高； 
####从我个人来讲，如果不涉及很复杂的一些操作，我就直接使用Serializable，但如果需要传递一个序列化对象的数组等，建议还是使用Parcelable。

####好啦，本篇博客在这里就结束了，如果存在理解偏差甚至错误的地方，请多多交流指正！谢谢各位！











