package com.finalproject.profitableshopping.data.models

import com.google.gson.annotations.SerializedName

class Product (
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("quantity")
    var quantity:Int = 0,
    @SerializedName("description")
    var description: String = "",
    @SerializedName("rial_price")
    var rialPrice: Double = 0.0,
    @SerializedName("dollar_price")
    var dollarPrice: Double = 0.0,
    @SerializedName("category_id")
    var categoryId:Int=0,
    @SerializedName("user_id")
    var userId:Int=0,
    @SerializedName("is_active")
    var isHide:Boolean=false,
    @SerializedName("images")
    var images:List<ImageUrl> = emptyList()
)