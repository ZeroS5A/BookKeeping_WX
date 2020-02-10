//logs.js
const util = require('../../utils/util.js')
const CHARTS = require('../../utils/wxcharts.js');
var  app  =  getApp();

Page({
  data: {
    logs: [],
    appData:app,
    bkdateStr:'',
    colors: ['#7cb5ec', '#f7a35c', '#434348', '#90ed7d', '#f15c80', '#8085e9'],
    systemData:{},
    monthsData:[],
    ringData:[],
    momthsSum:0
  },
  onLoad: function () {
    var that=this
    // this.setData({
    //   logs: (wx.getStorageSync('logs') || []).map(log => {
    //     return util.formatTime(new Date(log))
    //   })
    // })
    //获取屏幕和手机型号信息
    wx.getSystemInfo({
      success (res) {
        that.setData({
          systemData:{
            model:res.model,
            pixelRatio:res.pixelRatio,
            windowWidth:res.windowWidth,
            windowHeight:res.windowHeight
          },
        })
      }
    })
    this.getDate()
    setTimeout(() => {
      app.getRequest("bookkeeping/listMonthsIncomeExpend",null)
      .then((res)=>{
        that.setData({
          monthsData:res.data.data
        })
        app.getRequest("bookkeeping/MonthsExpendTypeStatisticData",this.data.bkDateStr)
        .then((res)=>{
          this.setData({
            ringData:res.data.data
          })
          this.columnShow()
        })
      })      
    }, 100);

  },
  //画图函数
  columnShow: function() {
    //名称转换
    for(var i in this.data.ringData){
      var item = this.data.ringData[i];
      item.name=app.globalData.iconList[item.name];
      this.setData({
        momthsSum:this.data.momthsSum+=item.data
      })
    }


    var column = {
      canvasId: 'lineCanvas',
      type: 'ring',
      series: this.data.ringData,
      width: this.data.systemData.windowWidth,
      height: this.data.systemData.windowWidth/1.2,
      dataLabel: true
    }
    new CHARTS(column);

  },
  //条形图选择
  selectMomth(e){
    var year =e.currentTarget.dataset.year
    var month = e.currentTarget.dataset.month
    if (month < 10) {
      month = "0" + month;
    }
    this.setData({
      bkDateStr:year+'-'+month+'%',
      momthsSum:0,
      year:year,
      month:month
    })
    app.getRequest("bookkeeping/MonthsExpendTypeStatisticData",this.data.bkDateStr)
    .then((res)=>{
      this.setData({
        ringData:res.data.data
      })
      this.columnShow()
    })
  },
  //时间获取
  getDate(){
    
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    if (month < 10) {
        month = "0" + month;
    }
    var nowDate = year + "-" + month +'%';
    this.setData({
      bkDateStr:nowDate,
      year:year,
      month:date.getMonth() + 1
    })
    
  },
})

