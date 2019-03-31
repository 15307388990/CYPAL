package com.cypal.ming.cypal.config;

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
    public static final String BASE_URL = "http://111.230.242.115:8888";
    //public static final String BASE_URL = "http://api.pos.efengshe.com";
    /* ... */
    public static final String SHOP_URL = "http://shop.efengshe.com/";
    /* 七牛图片请求地址 */
    public static final String PHOTO_URL = "http://7tsxz7.com1.z0.glb.clouddn.com/";
    /* 默认图片地址 */
    public static final String USER_DEFAULT_ICON = "http://7tsxz7.com1.z0.glb.clouddn.com/headicon_default.png";


    /* 登录 */
    public static final String venderLogin = "/public/login";
    /* 修改密码 */
    public static final String repassword = "/public/forgetPassword";
    /* 注册 */
    public static final String register = "/public/register";
    /* 退出登录 */
    public static final String mlogout = "/public/loginOut";
    /**
     * 发送注册验证码
     **/
    public static final String sendPhoneMsg = "/msm/register?";
    /**
     * 发送忘记密码验证码
     **/
    public static final String forgetPassword = "/msm/forgetPassword?";
    /**
     * 首页
     */
    public static final String mallSetInfo = "/private/index/";
    /**
     * 充值中心
     */
    public static final String rwlist = "/private/rw/list?";
    /**
     * 发起充值
     */
    public static final String recharge = "/private/rw/recharge";
    /**
     * 我的
     */
    public static final String setIn = "/private/user/setIn?";
    /**
     * 签到
     */
    public static final String signIn = "/private/user/signIn";
    /**
     * 图片上传
     */
    public static final String image = "/private/upload/image";
    /**
     * 保存认证资料
     */
    public static final String save = "/private/certification/save";
    /**
     * 会员管理
     */
    public static final String certification = "/private/certification/list?";
    /**
     * 缴纳保证金
     */
    public static final String submitBailMoney = "/private/certification/submitBailMoney?";
    /**
     * 申请退还保证金页面
     */
    public static final String refoundBailMoney = "/private/certification/refoundBailMoney?";
    /**
     * 申请退还保证金
     */
    public static final String sureRefoundBailMoney = "/private/certification/sureRefoundBailMoney?";
    /**
     * 修改头像
     */
    public static final String modifyAvatar = "/private/user/modifyAvatar";
    /**
     * 修改昵称
     */
    public static final String modifyNickName = "/private/user/modifyNickName";
    /**
     * 收款账户类别
     */
    public static final String category = "/private/payAccount/category?";
    /**
     * 我的收款账户列表
     */
    public static final String payAccount = "/private/payAccount/list?";
    /**
     * 添加收款账户
     */
    public static final String payAccountSave= "/private/payAccount/save";
    /**
     * 使用账户
     */
    public static final String use= "/private/payAccount/use";
    /**
     * 删除账户
     */
    public static final String del= "/private/payAccount/del";
    /**
     * 我的佣金
     */
    public static final String commision= "/private/commision/list?";
    /**
     * 提取佣金
     */
    public static final String withdraw= "/private/commision/withdraw?";
    /**
     * 退出登录
     */
    public static final String loginOut= "/public/loginOut?";
    /**
     * 开始接单
     */
    public static final String start= "/private/index/start";
    /**
     *  取消接单
     */
    public static final String stop= "/private/index/stop";
    /**
     *  抢单
     */
    public static final String take= "/private/index/take";
    /**
     *  确认收款
     */
    public static final String confirm= "/private/otcOrder/confirm";
    /**
     *  申诉订单
     */
    public static final String service= "/private/rw/service";
    /**
     *  充值详情
     */
    public static final String order= "/private/rw/order?";
    /**
     *  充值记录
     */
    public static final String orderlist= "/private/rw/my?";
    /**
     *  接单记录
     */
    public static final String otcOrderlist= "/private/otcOrder/list?";
    /**
     *  取消订单
     */
    public static final String cancel= "/private/rw/cancel";
    /**
     *  确认付款
     */
    public static final String rwconfirm= "/private/rw/confirm";
    /**
     * 版本 */
    public static final String check = "/public/version/check";


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
