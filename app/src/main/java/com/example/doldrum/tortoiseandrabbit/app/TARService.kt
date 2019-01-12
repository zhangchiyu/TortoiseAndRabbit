package com.example.doldrum.tortoiseandrabbit.app

import com.example.doldrum.tortoiseandrabbit.bean.*
import com.example.doldrum.tortoiseandrabbit.wxapi.WXUserResault
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable

interface TARService {
    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun getverifCode(@Field("mobile") mobile: String, @Field("action") action: String): Observable<MessageCode>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun register(@Field("action") action: String, @Field("mobile") mobile: String,
                 @Field("user_name") user_name: String,
                 @Field("sessionId") sessionId: String, @Field("sessionCode") sessionCode: String,
                 @Field("password") password: String): Observable<Result>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun longin(@Field("action") action: String,
               @Field("mobile") mobile: String,
               @Field("password") password: String): Observable<UserBean>

    /**
     * 通过手机验证码重置登录密码
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun findPassword(@Field("action") action: String,
                     @Field("mobile") mobile: String,
                     @Field("sessionId") sessionId: String,
                     @Field("sessionCode") sessionCode: String,
                     @Field("password") password: String): Observable<Result>



    /**
     * 保险箱余额
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun wode_yue(@Field("action") action: String,
                     @Field("sessionToken") sessionToken: String
                     ): Observable<BaoXianXiangBean>



    /**
     * 保险箱余额
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun wode_baoxian_yue(@Field("action") action: String,
                 @Field("sessionToken") sessionToken: String,
                 @Field("paypassword") paypassword: String
    ): Observable<BaoXianXiangBean>


    /**
     * 存
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun cunbaoxian(@Field("action") action: String,
                 @Field("sessionToken") sessionToken: String,
                   @Field("jine") jine: String
    ): Observable<BaoXianXiangBean>

    /**
     * 取
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun qubaoxian(@Field("action") action: String,
                   @Field("sessionToken") sessionToken: String,
                   @Field("jine") jine: String
    ): Observable<BaoXianXiangBean>


    /**
     * 修改登录密码
     * action=resetPassword&
    sessionToken=用户标志&
    orgPassword= 原用户密码MD5加密 &
    password=新密码MD5加密
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun resetPassword(@Field("action") action: String,
                      @Field("sessionToken") sessionToken: String,
                      @Field("orgPassword") orgPassword: String,
                      @Field("password") password: String): Observable<Result>




    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun updateUserInfor(@Field("action") action: String,
                      @Field("sessionToken") sessionToken: String,
                      @Field("username") username: String): Observable<Result>

    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun updateAvatar(@Field("action") action: String,
                        @Field("sessionToken") sessionToken: String,
                        @Field("avatar") avatar: String): Observable<Result>

    /**
     * 修改登录密码
     * action=resetPassword&
    sessionToken=用户标志&
    orgPassword= 原用户密码MD5加密 &
    password=新密码MD5加密
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun resetPayPassword(@Field("action") action: String,
                      @Field("sessionToken") sessionToken: String,
                      @Field("password") password: String,
                      @Field("payPassword") payPassword: String): Observable<Result>


    /*23、ID列表
    Post值
    action=list_id&&sessiontoken=用户标志&
    userid=用户ID&
    pagesize=一页显示多少条&
    pageindex=第几页*/

    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_id(@Field("action") action: String,
                @Field("sessiontoken") sessiontoken: String,
                @Field("userid") userid: String,
                @Field("pagesize") pagesize: String,
                @Field("pageindex") pageindex: String): Observable<IdBean>


    /**
     * 24、购买ID
    Post值
    action=goumai_id&sessiontoken=用户标志&userid=用户ID&id=要购买的ID号&jiage=id的价格
    返回值：{code：0,message:”购买成功” }

    {code：1,message:“购买失败” }
    {code：2,message:“余额不足”}
    {code：3,message:“已被他人抢购！”}
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun goumai_id(@Field("action") action: String,
                  @Field("sessiontoken") sessiontoken: String,
                  @Field("userid") userid: Int,
                  @Field("id") id: Int,
                  @Field("jiage") jiage: String): Observable<Result>


    /**
     * action=getUserInfor& sessionToken=用户标志
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun getUserInfor(@Field("action") action: String,
                     @Field("sessionToken") sessionToken: String): Observable<UserInfo>


    /**
     * action=list_tuandui_jinbi & sessionToken=用户标志&userid=用户id&
     * stime=开始日期&etime=结束日期&pageIndex=当前页面（从1开始）&pageSize=每页数据量
     */

    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_tuandui_jinbi(@Field("action") action: String,
                           @Field("sessiontoken") sessiontoken: String,
                           @Field("user_bh") user_bh: String,
                           @Field("stime") stime: String,
                           @Field("etime") etime: String,
                           @Field("pageIndex") pageIndex: Int,
                           @Field("pageSize") pageSize: Int): Observable<TeamRecordBean>


    //action=list_wode_jinbi & sessionToken=用户标志&userid=用户id&stime=开始日期&etime=结束日期&pageIndex=当前页面（从1开始）&pageSize=每页数据量
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_wode_jinbi(@Field("action") action: String,
                        @Field("sessiontoken") sessiontoken: String,
                        @Field("userid") userid: Int,
                        @Field("stime") stime: String,
                        @Field("etime") etime: String,
                        @Field("pageIndex") pageIndex: Int,
                        @Field("pageSize") pageSize: Int
    ): Observable<PurchaseHistoryBean>

    /*action=list_kaijiang
    返回值：{code：0,message:“获取成功”, data :[{ qishu:第几期,shijian:开奖时间，haoma:开奖号码}] }
    {code：1,message:“获取失败”, data :[] }*/
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_kaijiang(@Field("action") action: String): Observable<LotteryRecord>


    /**
     * action=getuserinfo_userbh
     *
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun getuserinfo_userbh(@Field("action") action: String,
                           @Field("sessiontoken") sessiontoken: String,
                           @Field("userbh") userbh: String): Observable<AccountBean>


    /**
     * action=zhuanzhan
     *
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun zhuanzhan(@Field("action") action: String,
                  @Field("sessiontoken") sessiontoken: String,
                  @Field("from_userid") from_userid: Int,
                  @Field("to_userid") to_userid: Int,
                  @Field("to_username") to_username: String,
                  @Field("jine") jine: Double
                  ): Observable<Result>

    /*19、游戏介绍(用来显示玩法，赔率等信息)
    Post值
    action=youxi_jieshao
    返回值：{code：0,message:“成功获取”,guitu:”龟兔游戏的详细介绍”,caichi:”彩池游戏介绍”,
        shuzi:” 数字游戏介绍”,longhu:” 龙虎游戏介绍”,tuibing:”推饼游戏介绍” }*/
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun youxi_jieshao(@Field("action") action: String): Observable<Result>

    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun caichi_jine(@Field("action") action: String,
                    @Field("sessiontoken") sessiontoken: String): Observable<ColorPoolAmount>


    /**
     * action=list_lushu&&sessiontoken=用户标志
     * &userid=用户ID&
     * pagesize=一页显示多少条&
     * pageindex=第几页
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_lushu(@Field("action") action: String,
                   @Field("sessiontoken") sessiontoken: String,
                   @Field("userid") userid: Int,
                   @Field("pageIndex") pageIndex: Int,
                   @Field("pageSize") pageSize: Int): Observable<LuShuBean>


    /**
     * 9、上传图片
    Post值
    action=saveImg& sessionToken=用户标志& Filedata=图片二进制流
     */
    @Multipart
    @POST("tools/guitu_api.ashx")
    fun saveImg(@Part("action") action: RequestBody,
                @Part("sessiontoken") sessiontoken: RequestBody,
                @Part file: MultipartBody.Part): Observable<SaveImageBena>

    /**
     * action=touzhu_zhuangtai&sessiontoken=用户标志&userid=用户ID
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun touzhu_zhuangtai(@Field("action") action: String,
                         @Field("sessiontoken") sessiontoken: String,
                         @Field("userid") userid: Int
    ): Observable<Result>

    /**
     * action=touzhu_zhuangtai&sessiontoken=用户标志&userid=用户ID
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_wode_touzhu(@Field("action") action: String,
                         @Field("sessiontoken") sessiontoken: String,
                         @Field("userid") userid: Int,
                         @Field("pagesize") pagesize: Int,
                         @Field("pageindex") pageindex: Int
    ): Observable<TouzhuBena>


    /**
     * 21、彩池游戏投注
    Post值
    action=caichi_touzhu&sessiontoken=用户标志&userid=用户ID&qishu=第几期
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun caichi_touzhu(@Field("action") action: String,
                      @Field("sessiontoken") sessiontoken: String,
                      @Field("userid") userid: Int
    ): Observable<Result>


    /*26、金币排行榜
    Post值
    action=list_paihangbang&&sessiontoken=用户标志
    返回值：{code：0,message:“获取成功”, data :[{user_bh: id号码， jinbi:金币}] }
    {code：1,message:“获取失败”, data :[] }*/
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_paihangbang(@Field("action") action: String,
                         @Field("sessiontoken") sessiontoken: String): Observable<RankBean>


    /**
     * 34.申请上庄
     * action=shenqing_shangzhuang&sessiontoken=用户标识&userid=用户Id&金币=上庄金额
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun shenqing_shangzhuang(@Field("action") action: String,
                             @Field("sessiontoken") sessiontoken: String,
                             @Field("userid") userid: Int,
                             @Field("jinbi") jinbi: Double): Observable<Result>

    /**
     * 35.撤销上庄
     * action=chexiao_shangzhuang_dui
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun chexiao_shangzhuang_dui(@Field("action") action: String): Observable<Result>

    /**
     *36.申请上庄列表
     * action=list_shangzhuang_dui
     *
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_shangzhuang_dui(@Field("action") action: String): Observable<SzBean>

    /**
     * 37.庄家输赢情况
     * action=zhuangjia_shuying
     * sessiontoken=用户标志
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun zhuangjia_shuying(@Field("action") action: String,
                          @Field("sessiontoken") sessiontoken: String): Observable<Result>

    /**
     * 38.投骰子
     * action=tou_saizi
     * sessiontoken=用户标志
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun tou_saizi(@Field("action") action: String,
                  @Field("sessiontoken") sessiontoken: String): Observable<Tousaizi>

    /**
     * 39.下庄
     * action=xiazhuang
     * sessiontoken=用户标志
     * userid=用户ID
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun xiaozhuang(@Field("action") action: String,
                   @Field("sessiontoken") sessiontoken: String,
                   @Field("userid") userid: Int): Observable<Result>


    /**
     * 40.游戏赔率
     * action= youxi_peilv
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun youxi_peilv(@Field("action") action: String): Observable<PeiLv>

    /**
     * 7微信登录
     * action = wxlogin
     * code = "授权临时票据code参数"
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun wxlogin(@Field("action") action: String): Observable<Result>

    /**
     * 14.会员间转账
     * action = zhuanzhuang
     * sessionToken = 用户标识
     * from_userid = 转出用户ＩＤ
     * to_userid = 转入的用户名
     * to_username = 转入的用户名
     * jine = 转帐金额
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun zhuanzhang(@Field("action") action: String,
                   @Field("sessiontoken") sessiontoken: String,
                   @Field("from_userid") from_userid: Int,
                   @Field("to_userid") to_userid: Int,
                   @Field("to_username") to_username: String,
                   @Field("jine") jine: Double): Observable<Result>

    /**
     * 41.推饼游戏
     * action = tuibing_touzu
     * sessiontoken
     * men(2：北门,3：天门,4：南门)
     * jine = 122
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun tuibing_touzu(@Field("action") action: String,
                      @Field("sessiontoken") sessiontoken: String,
                      @Field("men") men: Int,
                      @Field("jine") jine: Double): Observable<Result>


    /**
     * 27.游戏撤单
     * action = chedan
     * sessionToken = 用户标识
     * userid = 用户ＩＤ
     * qishu　＝　
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun chedan(@Field("action") action: String,
               @Field("sessiontoken") sessiontoken: String,
               @Field("userid") userid: Int): Observable<Result>



    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun san_touzhu(@Field("action") action: String,
    @Field("sessiontoken") sessiontoken: String,
    @Field("jsondata") jsondata: String): Observable<Result>


    /**
     * 微信登陆
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun wx_login(@Field("action")action:String,
                 @Field("code")code:String):Observable<WXUserResault>


    /**
     *公告列表
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_gonggao(@Field("action")action:String):Observable<GongGaoBean>


    /**
     * 关于游戏
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun gunayu_youxi(@Field("action")action:String):Observable<GuanyuYouxiBean>

    /**
     * 分享页面
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun fenxiang(@Field("action")action: String,
                 @Field("sessiontoken") sessiontoken: String
    ):Observable<FenxiangBean>

    /**
     * 版本升级
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun lastVersion(@Field("action")action: String,@Field("source")source:String):Observable<UpdateBean>


    /**
     * 今日战绩
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun zhanji(@Field("action")action: String,@Field("sessiontoken")sessiontoken:String):Observable<ZhanjiBean>


    /**
     * 今日战绩
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun xitongshijian(@Field("action")action: String):Observable<XiTongShijianBean>


    /**
     * 公告栏列表
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_gonggaolan(@Field("action")action: String):Observable<GonggaolanBean>


    /**
     * 富豪榜
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_fuhaobang(@Field("action")action: String,
                       @Field("sessiontoken") sessiontoken: String):Observable<FuhaobangBean>


    /**
     * 充值页面
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun chongzhi_yemian(@Field("action")action: String):Observable<CongZhi>


    /**
     * 43.进入或离开28杠页面，用于显示28页面的排行榜
     Post值
        action=erba_yemian&sessiontoken=&iferba=(1表示进入，0表示离开)
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun erba_yemian(@Field("action")action: String,
    @Field("sessiontoken") sessiontoken: String, @Field("iferba")iferba:Int):Observable<Result>


    /**
     *41.推饼投注28游戏投注 （投注时，要把一个人每门投注的金额 保存在APP上，方便投骰子之后，
     * 根据 赢的门和赔率来计算自己赚了多少钱，这个赚了多少钱，后台 会自动 计算，这个只是 显示作用）
    Post值
    action=tuibing_touzhu&sessiontoken=&men=2,3,4(2 北门,3天门 4南门)&jine=122&qi=当前的期数
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun tuibing_touzhu(@Field("action")action: String,
                       @Field("sessiontoken") sessiontoken: String,
                       @Field("men")men:Int,
                       @Field("jine")jine: Int,
                       @Field("qi")qi:Int):Observable<Result>

    /**
     * 28游戏的路子（28也叫推饼游戏）
     * list_28_luzi
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun list_28_luzi(@Field("action")action: String,
                    @Field("sessiontoken") sessiontoken: String):Observable<Result>


    /**
     * 54.每局结束奖励推送过来 之后，会有个3秒倒计时，用来决定庄家是否下庄，
     * 如果3秒之内，未点击下庄，则APP调用 这个接口，用来开始下一局，
     * 这个由庄主来执行这个接口，其他用户不用执行
     * erbakaishi
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun erbakaishi(@Field("action")action: String):Observable<Result>


    /**
     * action=zhuangjiaqian&user_id=庄家ID&jine=加的金额
     * zhuangjiaqian
     */
    @FormUrlEncoded
    @POST("tools/guitu_api.ashx")
    fun zhuangjiaqian(@Field("action")action: String,
                     @Field("user_id") user_id: Int,
                      @Field("jine")jine:Int):Observable<Result>
}
