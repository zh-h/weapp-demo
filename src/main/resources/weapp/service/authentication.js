const CONSTANT = require('../constant.js');

const login = (callback, fail) => {
  wx.login({
    success: function (res) {
      console.log(res);
      const code = res.code;
      wx.getUserInfo({
        success: function (res) {
          console.log(res);
          const userInfo = {
            encryptedData: res.encryptedData,
            iv: res.iv,
            signature: res.signature,
            userInfo: res.userInfo,
            rawData: res.rawData
          }
          var loginRequest = {
            code: code,
            userInfo: userInfo
          }
          console.log('login request');
          console.log(JSON.stringify(loginRequest));
          requestLogin(loginRequest, function (res) {
            callback(res);
          }, function (e) {
            fail(e)
          })
        },
        fail: function (e) {
          fail(e)
        },
        complete: function (res) {
        }
      })

    },
    fail: function (res) { },
    complete: function (res) { },
  })
}

function requestLogin(loginRequest, callback, fail) {
  wx.request({
    url: CONSTANT.APIS.LOGIN,
    data: loginRequest,
    header: {},
    method: 'POST',
    dataType: 'json',
    success: function (res) {
      console.log(res);
      if(res.data.code !== 0){
        fail(res.data);
      }
      const userInfo = res.data.data;
      wx.setStorage({
        key: 'token',
        data: userInfo.token,
      })
      wx.setStorage({
        key: 'userInfo',
        data: userInfo,
      })
      callback(res.data.data);
    },
    fail: function (res) {
      fail(res)
    },
    complete: function (res) { },
  })
}

const logout = (callback, fail) => {
  const token = wx.getStorageSync("token");
  wx.request({
    url: CONSTANT.APIS.LOGOUT,
    data: token,
    header: {},
    method: 'DELETE',
    dataType: 'json',
    success: function(res) {
      callback(res)
    },
    fail: function(res) {
      fail(res)
    },
    complete: function(res) {},
  })
}

module.exports = {
  login: login,
  logout: logout
}