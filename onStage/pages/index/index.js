//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    modalName:'',
    textareaAInput:'',
    postStuData:{
      stuId:'',
      stuPw:'',
      startDate:'',
      endDate:'',
      rand:''
    },
    feedbackData:{
      model:"",
      feedbackData:""
    },
    loginData:{
        encryptedData: '',
        code: '',
        iv: '',
    },
    bkDateStr:{
      bkDateStr:'',
    },
    bkData:{
      monthIncomeMoney: "--",
      monthExpendMoney: "--",
      allIncomeMoney: "--",
      todayExpendMoney: "--",
      allExpendMoney: "--",
      todayIncomeMoney: "--"
    },
    codeTempPath:1,
    delateModel:0
  },
  //页面准备好的时候自动调用
  onLoad: function () {
    var that=this;
    //延迟，否则还未从后端获取到数据就已经写入
    setTimeout(function () {
      console.log(app.globalData.userInfo)
      if(app.globalData.hasUserInfo){
        that.setData({
          userInfo:app.globalData.userInfo,
          hasUserInfo:app.globalData.hasUserInfo,
          token:app.globalData.token
        })
        that.getBkDate()
        wx.hideLoading()
      }else{
        console.log("用户未登录！")
      }

    }, 100) 
  },

  //页面显示时执行
  onShow: function () {
    this.getBkDate()
  },


  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  //获取用户数据
  getUserInfo() {
    var that=this;
    return new Promise(function(resolve, reject){
      wx.getUserInfo({
        success: function(e) {
          app.globalData.userInfo = e.userInfo
          that.setData({
            loginData:{
              iv:e.iv,
              encryptedData:e.encryptedData,
              code:app.globalData.loginData.code
            } 
          })
          resolve();
        },
        fail: function() {
          wx.hideLoading()
          reject()
        }
      })
        
    }) 
  },

  //用户登录操作
  userLogin(e){
    var that=this;
    wx.showLoading({
      title: '登录中',
    })
    this.getUserInfo()
    .then(()=>{
      return app.getRequest("api/getUserData",this.data.loginData)      
    })
    .then((res)=>{
      that.setData({
        userInfo:res.data.data,
        hasUserInfo:true
      })
      app.globalData.userInfo=res.data.data,
      app.globalData.hasUserInfo=true
      this.getBkDate()
      wx.hideLoading()
    })  
  },

  //session测试拿数据
  getdata(){
    app.getRequest("api/getOpenId","test")
    .then((res)=>{
      if(res.code!=4003){
        console.log(res)
        this.data.motto=res.data.data
      }
    })
  },

  //弹窗
  feedbackDialog(){
    this.setData({
      modalName:'DialogModal1'
    })
  },
  bindCardDialog(){
    this.setData({
      modalName:'DialogModal2'
    })
  },
  crawCardData(){
    this.setData({
      modalName:'DialogModal3',
    })
    this.data.postStuData.rand=''
    this.getCode()
  },

  //隐藏模态窗
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },

  //显示图片
  showQrcode() {
    wx.previewImage({
      urls: ["https://lczeros.cn/images/QRcode.png"]
    })
    // wx.previewImage({
    //   urls: ['/static/images/test.png'],
    //   current: '/static/images/test.png' // 当前显示图片的http链接      
    // })
  },

  //获取当前时间
  getDate(){
    
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var nowDate = year + "-" + month + "-" + day;
    this.setData({
      bkDateStr:{
        bkDateStr:nowDate,
      },
      postStuData:{
        endDate:year+month+day,
        stuId:'',
        stuPw:'',
        startDate:'',
        rand:''
      }
    })
  },

  //获取数据
  getBkDate(){
    if(this.data.hasUserInfo){
      this.getDate()
      app.getRequest("/bookkeeping/allIncomeExpendMoney",this.data.bkDateStr)
      .then((res)=>{
        if(res.data.code!=200){
          console.log("无法获取统计数据")
        }
        else{
          this.setData({
            bkData:{
              monthIncomeMoney: res.data.data.monthIncomeMoney,
              monthExpendMoney: res.data.data.monthExpendMoney,
              allIncomeMoney: res.data.data.allIncomeMoney,
              todayExpendMoney: res.data.data.todayExpendMoney,
              allExpendMoney: res.data.data.allExpendMoney,
              todayIncomeMoney: res.data.data.todayIncomeMoney
            }
          })
        }
      })
    }else{
      console.log("用户未登录！")
    }
  },

  //获取验证码
  getCode(){
    var that=this
    //下载验证码到本地
    app.getRequest2('getImageCode/','')
    .then((res)=>{
      that.setData({
        codeTempPath:res.data
      })
    })
    
  },
  //复制链接
  CopyLink(e) {
    wx.setClipboardData({
      data: e.currentTarget.dataset.link,
      success: res => {
        wx.showToast({
          title: '已复制',
          duration: 1000,
        })
      }
    })
    var times
    if(this.data.delateModel<=6){
      times = this.data.delateModel+1
    }else{
      times = 0
      wx.showModal({
        title:'是否清除所有同步的数据',
        content:'操作不可逆！仅在数据有问题时使用',
        confirmText:'删除',
        success(res){
          if(res.confirm){
            wx.showLoading({
              title: '删除中',
            })
            app.getRequest('card/deleteCardData','')
            .then((res)=>{
              wx.hideLoading()
              wx.showModal({
                title:'本次删除了'+res.data.data+'条记录',
              })
            })
          }
        }
      })
    }
    this.setData({
      delateModel:times
    })
  },

  //反馈提交
  postFeedback(){
    var that=this
    wx.getSystemInfo({
      success (res) {
        that.setData({
          feedbackData:{
            model:res.model,
            feedbackData:that.data.textareaAInput
          },
        })
      }
    })

    console.log(this.data.feedbackData)

    app.getRequest("api/feedback",this.data.feedbackData)
    .then((res)=>{
      if(res.data.data==1){
        wx.showToast({
          title: '反馈成功',
          icon: 'success',
          duration: 1500
        })
      }
    })
    this.setData({
      modalName: null
    })
  },

  //绑定学生卡
  bindCard(){
    app.getRequest2('setStuData/',this.data.postStuData)
    .then((res)=>{
      if(res.data='success'){
        this.hideModal()
        wx.showToast({
          title: '已填写',
          duration: 1000,
        })
        this.setData({
          hasStuAccount:1
        })
      }
      else{
        wx.showToast({
          title: 'ERROR',
          duration: 1000,
        })
      }
    })
  },

  //爬取数据
  upCardData(){
    if(this.data.postStuData.rand == ''){
      wx.showModal({
        title: '请输入验证码！',
      })
    }else{
      wx.showLoading({
        title: '爬取中',
      })
      //先验证登录
      app.getRequest2('stuLogin/',this.data.postStuData)
      .then((res)=>{
        //后获取数据
        if (res.data == "success"){
          app.getRequest2('drawData/',this.data.postStuData)
          .then((res)=>{
            wx.hideLoading()
            var message
            var success = false
            var that = this
            if (res.data == "nodata"){
              message="请检查区间，或者此区间没有数据"
            }
            else if (res.statusCode == 500){
              message="请求超时，请稍后重试"
            }
            else{
              success =true
              message="爬取到了"+res.data
            }
            wx.showModal({
              title:'是否同步',
              content:message,
              confirmText:'同步',
              success (res) {
                if (res.confirm && success) {
                  wx.showLoading({
                    title: '同步中',
                  })
                  app.getRequest('card/InsertCardData','')
                  .then((res)=>{
                    wx.hideLoading()
                    wx.showModal({
                      title:'本次同步了'+res.data.data+'条',
                    })
                  })
                }
                that.setData({
                  modalName:''
                })
              }
            })
          })
        }else{
          wx.hideLoading()
          this.getCode()
          wx.showModal({
            title:'错误！',
            content:'请检查验证码或者学号密码'
          })
        }

      })
    }
  },
  //用于双向绑定
  handleInputChange: function(e){
   // 取出定义的变量名
  var currentValue = e.detail.value; 
  var name = e.target.dataset.name;
   // 将 input 值赋值给 定义的变量名
  if(name == 'textareaAInput'){
    this.data.textareaAInput=currentValue
  }
    
  else if(name == 'stuId'){
    this.data.postStuData.stuId=currentValue
  }

  else if(name == 'stuPw'){
    this.data.postStuData.stuPw=currentValue
  }
        
  else if(name == 'startDate'){
    this.data.postStuData.startDate=currentValue
  }

  else if(name == 'endDate'){
    this.data.postStuData.endDate=currentValue
  }

  else if(name == 'rand'){
    this.data.postStuData.rand=currentValue
  }
  },
})
