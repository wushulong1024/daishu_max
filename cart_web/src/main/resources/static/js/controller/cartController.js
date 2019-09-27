//购物车控制层
app.controller('cartController',function($scope,cartService){
	$scope.cartList=[];
	//查询购物车列表
	$scope.findCartList=function(){
		cartService.findCartList().success(
			function(response){
				$scope.cartList=response;
				$scope.totalValue= cartService.sum($scope.cartList);
			}
		);
	}
	
	//数量加减
	$scope.addGoodsToCartList=function(itemId,num){
		cartService.addGoodsToCartList(itemId,num).success(
			function(response){
				if(response.success){//如果成功
					$scope.findCartList();//刷新列表
				}else{
					alert(response.message);
				}				
			}		
		);		
	}

	$scope.selectIds=[];//用户勾选的集合id
	//单个复选框
	$scope.updateSelection=function($event,id){
		if($event.target.checked){//如果是被选中,则增加到数组
			$scope.selectIds.push(id);
			console.log($scope.selectIds);
			if($scope.selectIds.length==$scope.list.length){
				$scope.isAllSelect=true;
				$scope.isSelect=true;
			}
		}else{
			var idx = $scope.selectIds.indexOf(id);
			$scope.selectIds.splice(idx, 1);//删除
			$scope.isAllSelect=false;
			if($scope.selectIds.length==0){
				$scope.isSelect=false;
			}
		}

	}

	$scope.selectAll=function($event){

		if($event.target.checked){
			console.log($scope.selectIds=[])
			//如果是被选中,则增加全部id到数组
			$scope.selectIds=[];
			$scope.isAllSelect=true;
			$scope.isSelect=true;
			angular.forEach($scope.list, function (value,key) {
				$scope.selectIds.push(value.id);
			});
		}else{
			$scope.isAllSelect=false;
			$scope.isSelect=false;
			$scope.selectIds=[];
		}

	}
	//查询实体
	$scope.findOne=function() {
		var href = location.href;

		$scope.id= href.substr(href.indexOf("=") + 1);
		cartService.findCartById($scope.id).success(
			function (response) {
				$scope.orderList=response;
			}
		)
	}

	//获取当前用户的地址列表
	$scope.findAddressList=function(){
		cartService.findAddressList().success(
			function(response){
				$scope.addressList=response;
				for(var i=0;i<$scope.addressList.length;i++){
					if($scope.addressList[i].isDefault=='1'){
						$scope.address=$scope.addressList[i];
						break;
					}
				}

			}
		);
	}
	//选择地址
	$scope.selectAddress=function(address){
		$scope.address=address;
	}

	//判断某地址对象是不是当前选择的地址
	$scope.isSeletedAddress=function(address){
		if(address==$scope.address){
			return true;
		}else{
			return false;
		}
	}

	$scope.order={paymentType:'1'};//订单对象

	//选择支付类型
	$scope.selectPayType=function(type){
		$scope.order.paymentType=type;
	}

	//保存订单
	$scope.submitOrder=function(){
		$scope.order.receiverAreaName=$scope.address.address;//地址
		$scope.order.receiverMobile=$scope.address.mobile;//手机
		$scope.order.receiver=$scope.address.contact;//联系人

		cartService.submitOrder( $scope.order ).success(
			function(response){
				//alert(response.message);
				if(response.success){
					//页面跳转
					if($scope.order.paymentType=='1'){//如果是微信支付，跳转到支付页面
						location.href="pay.html";
					}else{//如果货到付款，跳转到提示页面
						location.href="paysuccess.html";
					}

				}else{
					alert(response.message);	//也可以跳转到提示页面
				}

			}
		);
	}

});