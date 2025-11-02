package com.zoe.wan.android.example

class Address (var city: String, var street: String){
    fun deepCopy(): Address = Address(city, street)
}