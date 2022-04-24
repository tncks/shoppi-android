package com.shoppi.app.ui

data class Model_images(
    var str_folder: String,
    var al_imagepath: ArrayList<String>
)

fun Model_images.getAl_imagepath(): ArrayList<String> {
    return this.al_imagepath
}

fun Model_images.getStr_folder(): String {
    return this.str_folder
}

fun Model_images.setAl_imagepath(al_imagepath: ArrayList<String>) {
    this.al_imagepath = al_imagepath
}

fun Model_images.setStr_folder(str_folder: String) {
    this.str_folder = str_folder
}

//class Model_images {
//    fun getStr_folder(): String {
//        return this.str_folder
//    }
//
//    fun getAl_imagepath(): ArrayList<String> {
//        return this.al_imagepath
//    }
//
//    fun setStr_folder(str_folder: String) {
//        this.str_folder = str_folder
//    }
//
//    fun setAl_imagepath(al_imagepath: ArrayList<String>) {
//        this.al_imagepath = al_imagepath
//    }
//
//    private lateinit var str_folder: String
//    private lateinit var al_imagepath: ArrayList<String>
//
//}