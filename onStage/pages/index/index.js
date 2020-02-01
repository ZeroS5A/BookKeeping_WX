//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {
      avatarUrl:"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI7tNjftuIGgDgWqvA8NZW70Bu1p66Gicr4wMBak3DImFA2q0as7goZ2iaXiafEwneFib1cLjALp8l57g/132",
    },
    hasUserInfo: false,
    token:'',
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

  //获取用户数据
  getUserInfo() {
    var that=this;
    return new Promise(function(resolve, reject){
      wx.getUserInfo({
        success: function(e) {
          console.log(e)
          app.globalData.userInfo = e.userInfo
          // this.setData({
          //   userInfo: e.userInfo,
          //   hasUserInfo: true
          // })
          that.data.loginData.iv=e.iv;
          that.data.loginData.encryptedData=e.encryptedData;
          resolve();
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
              console.log('code:'+res.code);
              that.data.loginData.code = res.code;
              resolve();
          }
      })
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
          Authorization: that.data.token
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

  //用户登录操作
  userLogin(e){
    console.log(e);
    var that=this;
    this.getCode()
    .then(()=>{
      return this.getUserInfo()
    })
    .then(()=>{
      console.log(this.data.loginData);
      return this.getRequest("api/login",this.data.loginData.code)           
    })
    .then((res)=>{
      console.log(res)
      that.data.token=res.data.data.token;
      that.data.loginData.code=res.data.data.session;
      return this.getRequest("api/getUserData",this.data.loginData)
      
    }).then((res)=>{
      that.setData({
        userInfo:res.data.data,
        hasUserInfo:true
      })
    })  
  },

  //session测试拿数据
  getdata(){
    this.getRequest("api/getOpenId",this.data.loginData.code)
    .then((res)=>{
      if(res.code!=4003){
        console.log(res)
        this.data.motto=res.data.data
      }
    })
  },

  //测试状态转换
  switchTest(){
    this.setData({
      hasUserInfo:true
    })
  }
})
