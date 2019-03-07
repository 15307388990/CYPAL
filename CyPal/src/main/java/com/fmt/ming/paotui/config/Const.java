package com.fmt.ming.paotui.config;

/**
 * @author Const 请求地址 常量
 */
public class Const {
    /**
     * 微信模块参数
     */
    public static final String APP_ID = "wx89dce52534040293";
    public static final String APP_SECRET = "0bbfdeaa90461078d380c91deede2d15";
    public static final String GET_WECHAT_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String GET_WECHAT_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 测试地址1
     */
    // public static final String BASE_URL =
    // "http://memberapitest.efengshe.com/";
    /**
     * 测试地址2
     */
    // public static final String BASE_URL =
    // "http://192.168.1.168:8080/efengshe-api-member/";
    // public static final String SHOP_URL = "http://shoptest.efengshe.com/";

    /**
     * 主地址
     */
    public static final String BASE_URL = "http://fmt.pengkeda.com";
    //public static final String BASE_URL = "http://api.pos.efengshe.com";
    /* ... */
    public static final String SHOP_URL = "http://shop.efengshe.com/";
    /* 七牛图片请求地址 */
    public static final String PHOTO_URL = "http://7tsxz7.com1.z0.glb.clouddn.com/";
    /* 默认图片地址 */
    public static final String USER_DEFAULT_ICON = "http://7tsxz7.com1.z0.glb.clouddn.com/headicon_default.png";


    /* 登录 */
    public static final String venderLogin = "/rider/mlogin";
    /* 获取订单列表*/
    public static final String orderlist = "/order/index";
    /* 修改密码 */
    public static final String repassword = "/rider/repassword";
    /* 退出登录 */
    public static final String mlogout = "/rider/mlogout";
    /* 订单详情 */
    public static final String ordershow = "/order/show";
    /* 更新订单*/
    public static final String update = "/order/update";
    /* 更新头像 */
    public static final String avatar = "/rider/avatar";
    /* 上传图片 */
    public static final String upload = "/image/upload";
    /* 上班接单 */
    public static final String work = "/rider/work";
    /* 下班 休息 */
    public static final String unwork = "/rider/unwork";
    /* 用户中心 */
    public static final String center = "/rider/center";
    /* 消息中心 */
    public static final String message = "/message/index";


    /* 售货机列表 */
    public static final String getApiVenderDeviceList = "/api/vender/getApiVenderDeviceList.do";
    /* 今日售货明细列表 */
    public static final String todaySellList = "/api/vender/todaySellList.do";
    /* 售货统计 */
    public static final String getDeviceSellStatistics = "/api/vender/getDeviceSellStatistics.do";
    /* 检测新版本 */
    public static final String jiebianAppVersion = "/api/shop/version";
    /* 更新定位 */
    public static final String gis = "/rider/gis";
    /* 获取配置信息 */
    public static final String config = "/config/index";
    /* 获取轨迹 */
    public static final String route = "/lbs/route";
    /* 消息详情 */
    public static final String messageshow = "/message/show";


    /**
     * 获取图片验证码流
     **/
    public static final String imageValidStream = "/api/shop/admin/imageValidStream";
    /**
     * 获取图片验证码流
     **/
    public static final String safeImageValidStream = "/api/shop/agencypayinfo/safeImageValidStream";
    /**
     * 添加提现账号
     **/
    public static final String addAccount = "/api/shop/agencyaccount/add";
    /**
     * 发送验证码
     **/
    public static final String sendPhoneMsg = "/api/pos/sendPhoneMsg.json";
    /**
     * 根据卡号前6位 获取所属银行
     **/
    public static final String getBankName = "/api/shop/agencyaccount/findBank";
    /**
     * 获取所有城市
     **/
    public static final String getAllBankCity = "/api/shop/agencyaccount/cityList";
    /**
     * 根据城市编号和银行名称获取支行列表
     **/
    public static final String getBankBranchsByCityCode = "/api/shop/agencyaccount/findBranchBanck";
    /**
     * 售货机首页获取冻结金额
     **/
    public static final String getNoSettlePrice = "/api/vender/settle/getNoSettlePrice.json";
    /**
     * 获取账号列表
     **/
    public static final String accountList = "/api/shop/agencyaccount/list";
    /**
     * 打款列表
     **/
    public static final String playMoneyRecord = "/api/vender/settle/playMoneyRecord.json";
    /**
     * 我的钱包
     **/
    public static final String FinanceIndex = "/api/shop/finance/index";
    /**
     * 申请提现
     */
    public static final String applyWithdraw = "/api/shop/finance/withraw";
    /**
     * 删除提现账户
     */
    public static final String delAccount = "/api/shop/agencyaccount//del";
    /**
     * 验证支付密码（修改支付密码时使用）
     */
    public static final String verificationPayPass = "/api/shop/agencypayinfo/validPayPass";
    /**
     * 店铺详情
     * POST 开发完成 /api/shop/admin/info
     */
    public static final String mallSetInfo = "/api/shop/admin/info";
    //验证安全手机验证码
    public static final String sendSafeMsg = "/api/shop/agencypayinfo/sendSafeMsg";
    //安全手机验证
    public static final String validSafePhone = "/api/shop/agencypayinfo/validSafePhone";
    /**
     * 修改支付密码
     */
    public static final String updatePayPass = "/api/shop/agencypayinfo/updatePayPass";
    /**
     * 街边go首页数据显示
     */
    public static final String vender_index = "/api/shop/mall/order/vender_index";

    /**
     * 20、已经完成提现的列表
     */
    public static final String completeWithdraw = "/api/shop/finance/withdrawList";
    /**
     * 订单列表
     */
    public static final String getVenderOrderList = "/api/shop/mall/order/getVenderOrderList";
    /**
     * 16、提现记录列表
     */
    public static final String withdrawList = "/api/shop/finance/index";
    /**
     * 15、冻结金额  财务首页
     */
    public static final String freezeList = "/api/shop/finance/index";
}
