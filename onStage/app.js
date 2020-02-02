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
  },

  //开启时执行
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    console.log("页面打开");

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
              this.globalData.userInfo=res.data.data,
              this.globalData.hasUserInfo=true
            })
            console.log("获取用户数据成功")
            console.log(this.globalData)
          }else{
            console.log("没授权")
            console.log(this.globalData)
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

  //微信请求封装
  getRequest(url,data){
    var that=this;
    return new Promise(function(resolve,reject){
      wx.request({
        url: 'http://localhost/BookKeeping/'+url,
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


})