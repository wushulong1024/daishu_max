        app.service('seckillGoodsService',function ($http) {
                this.findList=function () {
                        return $http.get('seckillGoods/findList');
                }

                this.findOne=function (id) {
                    return $http.get('seckillGoods/findOneFromRedis?id='+id);
                }


             this.submitOrder=function (seckillId) {
                return $http.get('seckillOrder/submitOrder?seckillId='+seckillId);
            }
        });