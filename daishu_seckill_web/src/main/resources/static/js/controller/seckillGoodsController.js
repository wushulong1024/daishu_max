app.controller('seckillGoodsController', function ($scope, $location, seckillGoodsService, $interval) {
    //读取列表数据绑定到表单中
    $scope.findList = function () {
        seckillGoodsService.findList().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    $scope.findOne = function () {
        var id = $location.search()['id'];
        seckillGoodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                //倒计时开始
                allsecond=Math.floor((new Date( $scope.entity.endTime).getTime()-new Date().getTime())/1000);

                time = $interval(function () {
                    allsecond=allsecond-1;
                  $scope.timeString=convertTimeString(allsecond);
                    if ( allsecond<= 0) {
                        $interval.cancel(time);
                    }

                }, 1000);

            }
        );
    }

    //转换秒为天小时秒格式
    convertTimeString=function (allsecond) {
        var days=Math.floor(allsecond/(60*60*24));
        var hours=Math.floor((allsecond-days*60*60*24)/(60*60));
        var miuntes=Math.floor((allsecond-days*60*60*24-hours*60*60)/60);
        var seconds=allsecond-days*60*60*24-hours*60*60-miuntes*60;
        var timeString="";
        if(days>0){
            timeString=days+"天";
        }
        return timeString+hours+":"+miuntes+":"+seconds;
    }


    $scope.submitOrder=function () {
        seckillGoodsService.submitOrder($scope.entity.id).success(
            function (response) {
                if(response.success){
                    alert("抢购成功，请在五分钟之内完成支付")
                    location.href="pay.html";//跳到支付页面
                }else {
                    alert("shi");
                }

        });
    }

});
