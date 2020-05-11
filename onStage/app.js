//app.js
App({

  globalData: {
    userInfo: {},
    token:'',
    loginData:{
        encryptedData: '',
        code: '',
        iv: '',
    },
    hasUserInfo: false,
    //图标库对应
    iconList:{
      emoji:'餐饮',
      shop:'消费',
      cart:'购物',
      clothes:'服饰',
      pay:'缴费',
      weixin:'微信转账',
      deliver:'交通',
      discover:'娱乐',
      taxi:'交通',
      mobile:'通信',
      ticket:'票据',
      camera:'旅游',
      evaluate:'医疗',
      edit:'学习',
      more:'其他',

      selection:'奖金',
      vipcard:'工资',
      redpacket:'红包',
      recharge:'收款',
    }
  },

  //开启时执行
  onLaunch: function () {
    // 展示本地存储能力
    // var logs = wx.getStorageSync('logs') || []
    // logs.unshift(Date.now())
    // wx.setStorageSync('logs', logs)

    console.log("程序打开");

    this.refreshToken()
    //登录
    this.getCode()
    .then((res)=>{
      //拿token
      return this.getRequest("api/login",res) 
    })
    .then((res)=>{
      this.globalData.token=res.data.data.token;
      this.globalData.loginData.code=res.data.data.session;

      //判断是否授权;
      wx.getSetting({
        success: res => {
          if (res.authSetting['scope.userInfo']) {
            //已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
            this.getUserInfo()
            .then(()=>{
              return this.getRequest("api/getUserData",this.globalData.loginData)
            })
            .then((res)=>{
              this.globalData.userInfo=res.data.data
              if(this.globalData.userInfo!=null){
                this.globalData.hasUserInfo=true
                console.log("获取用户数据成功")
              }
            })
          }else{
            console.log("没授权")
          }
        }
      })
    })
    
    
    
    // 获取用户信息
    wx.getSystemInfo({
      success: e => {
        this.globalData.StatusBar = e.statusBarHeight;
        let custom = wx.getMenuButtonBoundingClientRect();
        this.globalData.Custom = custom;
        this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
      }
    })   
  },
  //后台回切时执行(防止Token失效)
  onShow (options) {
    this.getCode()
      .then((res)=>{
        //拿token
        return this.getRequest("api/login",res) 
      })
      .then((res)=>{
        console.log("已经刷新token")
        this.globalData.token=res.data.data.token;
      })
  },
  //微信请求封装
  getRequest(url,data){
    var that=this;
    return new Promise(function(resolve,reject){
      wx.request({
        url: 'https://lczeros.cn/Bookkeeping/'+url,
        data: data,
        method: 'POST',
        header: {
          Authorization: that.globalData.token
        }, 
        success: function(res){
          resolve(res)
        },
        fail: function() {
          reject()
        },
        complete: function() {
          //complete
        }
      })
    })
  },
  //微信请求封装Django版本
  getRequest2(url,data){
    var that=this;
    return new Promise(function(resolve,reject){
      wx.request({
        url: 'https://lczeros.cn/crawExpendData/'+url,
        data: data,
        method: 'GET',
        header: {
          Authorization: that.globalData.token,
          'content-type': 'application/x-www-form-urlencoded'
        }, 
        success: function(res){
          resolve(res)
        },
        fail: function() {
          reject()
        },
        complete: function() {
          //complete
        }
      })
    })
  }, 

  //获取code换session
  getCode(){
    var that=this;
    //回调函数
    return new Promise(function(resolve, reject){
      wx.login({
          success(res) {
              resolve(res.code);
          }
      })
    })
  },

  //获取用户数据
  getUserInfo() {
    var that=this;
    return new Promise(function(resolve, reject){
      wx.getUserInfo({
        success: function(e) {
          that.globalData.loginData.iv=e.iv;
          that.globalData.loginData.encryptedData=e.encryptedData;
          resolve();
        }
      })  
    }) 
  },

  //刷新登录(30分钟)
  refreshToken(){
    var that=this
    setTimeout(function(){
      that.getCode()
      .then((res)=>{
        //拿token
        return that.getRequest("api/login",res) 
      })
      .then((res)=>{
        console.log("已经刷新token")
        that.globalData.token=res.data.data.token;
      })

      that.refreshToken()
    },1000*60*29)
  }
})