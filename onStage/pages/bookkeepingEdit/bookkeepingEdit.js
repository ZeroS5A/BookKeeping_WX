// pages/bookkeepingEdit/bookkeepingEdit.js
var dateTimePicker = require('../../utils/dateTimePicker.js');
//分页需要这样才能获取导app数据
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    appData:app,
    dateTimeArray: null,
    dateTime: null,
    dateTimeArray1: null,
    dateTime1: null,
    startYear: 2000,
    endYear: 2050,
    id:null,
    bktype:'baby',
    isIncome:false,
    type:null,
    byDate:'',
    remarkText:"",
    bkMoney:'',
    //支出类型
    bkType0:[
      {
        bktype:"emoji",
      },
      {
        bktype:"cart",
      },
      {
        bktype:"shop",
      },
      {
        bktype:"more",
      },
      {
        bktype:"taxi",
      },
      {
        bktype:"pay",
      },
      {
        bktype:'mobile',
      },
      {
        bktype:'camera',
      },
      {
        bktype:"clothes",
      },
      {
        bktype:"discover",
      },
      {
        bktype:"ticket",
      },
      {
        bktype:"evaluate",
      },
      {
        bktype:"edit"
      }
    ],
    //收入类型
    bkType1:[
      {
        bktype:"vipcard",
      },
      {
        bktype:"selection",
      },
      {
        bktype:"redpacket",
      },
      {
        bktype:"more",
      },
      {
        bktype:"recharge"
      }
    ],
  },
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    // 获取完整的年月日 时分秒，以及默认显示的数组
    var obj = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
    var obj1 = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
    // 精确到分的处理，将数组的秒去掉
    var lastArray = obj1.dateTimeArray.pop();
    var lastTime = obj1.dateTime.pop();

    this.setData({
      dateTime: obj.dateTime,
      dateTimeArray: obj.dateTimeArray,
      dateTimeArray1: obj1.dateTimeArray,
      dateTime1: obj1.dateTime
    });
    this.transDate()

    var that=this
    //处理跳转的数据
    setTimeout(function () {
      const eventChannel = that.getOpenerEventChannel()
      eventChannel.on('acceptDataFromOpenerPage', function(data) {
        var isIncome
        var type
        if(data.data.incomeOrExpend=="income"){
          isIncome=true
          type='income'
        }
        else{
          isIncome=false
          type='expend'
        }
        that.setData({
          id:data.data.id,
          bktype:data.data.bkType,
          bkDate:data.data.bkDate,
          isIncome:isIncome,
          type:type,
          remarkText:data.data.remarkText,
          bkMoney:data.data.bkMoney,
        })
        
      })

    },50)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  TimeChange(e) {
    this.setData({
      time: e.detail.value
    })
  },

  onDateChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      date: e.detail.value
    })
  },

  DateChange(e) {
    this.setData({
      date: e.detail.value
    })
  },

  changeDate(e) {
    this.setData({ date: e.detail.value });
  },
  changeTime(e) {
    this.setData({ time: e.detail.value });
  },
  changeDateTime(e) {
    this.setData({ dateTime: e.detail.value });
  },
  changeDateTime1(e) {
    this.setData({ dateTime1: e.detail.value });
    this.transDate()
  },
  changeDateTimeColumn(e) {
    var arr = this.data.dateTime, dateArr = this.data.dateTimeArray;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);

    this.setData({
      dateTimeArray: dateArr,
      dateTime: arr
    });
  },
  changeDateTimeColumn1(e) {
    var arr = this.data.dateTime1, dateArr = this.data.dateTimeArray1;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);

    this.setData({
      dateTimeArray1: dateArr,
      dateTime1: arr
    });
  },

  //类型切换
  typeSelect0(){
    this.setData({
      isIncome:false,
      bktype:'baby'
    })
  },
  typeSelect1(){
    this.setData({
      isIncome:true,
      bktype:'more'
    })
  },

  //激活选择
  typeActive(e){
    //选择的type的id值
    this.setData({
      bktype:e.currentTarget.dataset.modal
    })
  },

  //用于双向绑定
  handleInputChange: function(e){
    // 取出定义的变量名
   var targetData = e.currentTarget.dataset.modal; 
   // 取出定义的变量名
   var currentValue = e.detail.value; 
    
   // 将 input 值赋值给 定义的变量名
   if(targetData==1){
     this.data.remarkText=currentValue
   }else if(targetData==2){
     this.data.bkMoney=currentValue
   }
  },

  //提交按钮
  postData(){
    var type
    var data
    
    if(this.data.isIncome){
      type="income"
    }else{
      type="expend"
    }

    if(this.data.bkMoney==''){
      console.log("请输入金额")
      wx.showModal({
        content: '请输入金额!',
        showCancel: false,
        success (res) {
          if (res.confirm) {
            console.log('用户点击确定')
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    }else{
      data={
        id: this.data.id,
        bkType: this.data.bktype,
        bkDate: this.data.bkDate,
        bkMoney: this.data.bkMoney,
        remarkText: this.data.remarkText,
        incomeOrExpend: type
      }
      app.getRequest("bookkeeping/editBookkeeping",data)
      .then((res)=>{
        if(res.data.code==200){
          console.log("添加成功！")
          
          
          wx.navigateBack({
            delta: 2
          })
          wx.showToast({
            title: '添加成功！',
            icon: 'success',
            duration: 2000
          })
        }
      })
    }

    console.log(data)
  },

  //时间转换
  transDate(){
    var momth=this.data.dateTime1[1]+1
    var day=this.data.dateTime1[2]+1
    var hour=this.data.dateTime1[3]
    var min=this.data.dateTime1[4]

    if (momth < 10) {
      momth = "0" + momth;
    }
    if (day < 10) {
      day = "0" + day;
    }
    if (hour < 10) {
      hour = "0" + hour;
    }
    if (min < 10) {
      min = "0" + min;
    }
    this.setData({
      bkDate: "20"+this.data.dateTime1[0]+"-"+momth+"-"+day
                  +" "+hour+":"+min,
    })
  },

  //快捷选择
  quickChoose(e){
    //console.log(e.currentTarget.dataset.type)
    this.setData({
      remarkText:e.currentTarget.dataset.type
    })
  }
})