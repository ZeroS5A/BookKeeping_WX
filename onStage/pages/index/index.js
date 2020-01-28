//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    //canIUse: wx.canIUse('button.open-type.getUserInfo'),
    loginData:{
        encryptedData: '',
        code: '',
        iv: '',
    }
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  /*onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },*/
  getUserInfo() {
    var that=this;
    return new Promise(function(resolve, reject){
      wx.getUserInfo({
        success: function(e) {
          console.log(e)
          app.globalData.userInfo = e.userInfo
          // this.setData({
          //   userInfo: e.userInfo,
          //   //hasUserInfo: true
          // })
          that.data.loginData.iv=e.iv;
          that.data.loginData.encryptedData=e.encryptedData;
          resolve();
        }
      })  
    })
    
    
  },
  getCode(){
    var that=this;
    return new Promise(function(resolve, reject){
      wx.login({
          success(res) {
              console.log('code:'+res.code);
              that.data.loginData.code = res.code;
              resolve();
          }
      })
    })
  },
  userLogin(e){
    console.log(e);
    var that=this;
    this.getCode()
    .then(()=>{
      return this.getUserInfo()
    })
    .then(()=>{
      console.log(this.data.loginData);
      wx.request({
          url: 'http://localhost/BookKeeping/api/login',
          method: 'POST',
          data: this.data.loginData,
          success:function (res){
            console.log(res.data)
            that.data.userInfo=res.data.data;
            that.data.hasUserInfo=true;
          }
      })  
    })  
  },
})
