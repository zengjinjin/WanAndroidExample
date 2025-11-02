package com.zoe.wan.android.example


data class Person (var name: String, var address: Address){
    fun deepCopy(): Person = Person(name, address.deepCopy())
}