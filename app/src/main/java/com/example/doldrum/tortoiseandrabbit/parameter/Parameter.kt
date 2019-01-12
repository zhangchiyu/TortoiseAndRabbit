package com.example.doldrum.tortoiseandrabbit.parameter



data class SendCode(val mobile:String,val action:String="sendCode")
//action=sendCode&mobile=手机号&verifyId=图片标志id&verifyCode=验证码
