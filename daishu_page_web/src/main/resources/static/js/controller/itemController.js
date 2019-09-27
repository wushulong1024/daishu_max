app.controller('itemController', function ($scope,$http) {

    $scope.specificationItems={};//存储用户选择的规格
    //对数量加减
    $scope.addNum=function(x){
        $scope.num+=x;
        if($scope.num<1){
            $scope.num=1;
        }
    }

//用户选择规格
$scope.selectspecification=function(key,value){
    $scope.specificationItems[key]=value;
    searchsku();//查询sku
}


$scope.isSelected=function(key,value){
    if( $scope.specificationItems[key]==value){
        return true;
    }else{
        return false;
    }
}

$scope.sku={};//当前选择sku
//加载默认的sku
$scope.loadSku=function(){
    $scope.sku=skuList[0];
    $scope.specificationItems= JSON.parse(JSON.stringify($scope.sku.spec));

}


//匹陪两个对象是否相等
matchObject=function(map1,map2){
    for(var k in map1 ){
        if(map1[k]!=map2[k]){
            return false;
        }
    }


     for(var k in map2 ){
        if(map2[k]!=map1[k]){
            return false;
        }
    }

    return true;
}
searchsku=function(){

    for(var i=0;i<skuList.length;i++){
        if(matchObject(skuList[i].spec,$scope.specificationItems)){
            $scope.sku=skuList[i];
            return;
        }

    }

    $scope.sku={id:0,title:'----',price:0};
}

    //添加商品到购物车
$scope.addToCart=function(){
   /* alert('SKUID:'+$scope.sku.id);*/

    $http.get('http://192.168.90.136:9050/cart/addGoodsToCartList?itemId='
        +$scope.sku.id+'&num='+$scope.num).success(
            function (response) {
                if(response.success){
                    location.href='http://192.168.90.136:9050/cart.html';
                }else{
                    alert(response.message);
                }
            }      
    );
}


});