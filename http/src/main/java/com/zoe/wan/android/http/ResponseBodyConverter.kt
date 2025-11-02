package com.zoe.wan.android.http

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

class ResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>): Converter<ResponseBody, T> {

//    override fun convert(value: ResponseBody): T {
//        val response = value.string()
//        try {
//            val httpResult = gson.fromJson(response, HttpResult::class.java)
//            if (httpResult.code != HttpResult.SUCCESS) {
//                throw ApiException(httpResult.code, httpResult.msg)
//            }
//
//            return httpResult as T
//        } catch (e: JsonSyntaxException) {
//            throw ApiException(HttpResult.JSON_SYNTAX_ERROR, e.message.toString())
//        } finally {
//            value.close()
//        }
//    }
    
    
    override fun convert(value: ResponseBody): T? {
        val jsonReader = gson.newJsonReader(value.charStream())
        return try {
            val result: T = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            if (result is HttpResult<*>) {
                // todo 服务端bug未修复，修复后此处复原
                if (result.errorCode != HttpResult.SUCCESS) {
                    throw ApiException(result.errorCode, result.errorMsg)
                }
            }
            result
        } catch (e: IOException) {
            throw ApiException(HttpResult.JSON_SYNTAX_ERROR, e.message.orEmpty().ifEmpty { "bad request" })
        } finally {
            value.close()
        }
    }
}