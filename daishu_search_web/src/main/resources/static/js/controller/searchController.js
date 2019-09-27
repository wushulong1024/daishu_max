app.controller('searchController', function ($scope, searchService) {
    //定义搜索对象的结构
    $scope.searchMap = {
        'keywords': '',
        'category': '',
        'brand': '',
        'spec': {},
        'price': '',
        'pageNo': 1,
        'pageSize': 40,'sort':'','sortFieId':''
    };

    //搜索
    $scope.search = function () {
        $scope.searchMap.pageNo=parseInt( $scope.searchMap.pageNo);
        searchService.search($scope.searchMap).success(
            function (response) {
                $scope.resultMap = response;
              /*  $scope.searchMap.pageNo=1;*///查询后显示第一页
                buildPageLabel();//构建分页栏
            }
        );
    }

    //构建分页栏
    buildPageLabel = function () {
        //构建分页标栏
        $scope.pageLabel = [];
        var fristPage = 1;//开始页码
        var lastPage = $scope.resultMap.totalPages;//截止页码
        $scope.fristDot=true;//开始有点
        $scope.lastDot=true;//后边有点

        if ($scope.resultMap.totalPages > 5) {//如果页码数量大于5
            if ($scope.searchMap.pageNo <= 3) {//如果当前页码小于等于3，显示5页
                lastPage = 5;
                $scope.fristDot=false;//前面没点
            } else if ($scope.searchMap.pageNo >= $scope.resultMap.totalPages - 2) {
                fristPage=$scope.resultMap.totalPages-4;
                $scope.lastDot=false;//后边没点
            }else{//已当前页为中心的五页
                fristPage=$scope.searchMap.pageNo-2
                lastPage=$scope.searchMap.pageNo+2;
            }
        }else{
            $scope.fristDot=false;//开始有点
            $scope.lastDot=false;//后边有点
        }

        //构建页码
        for (var i = fristPage; i <= lastPage; i++) {
            $scope.pageLabel.push(i);
        }
    }

    //添加搜索项
    $scope.addSearchItem = function (key, value) {
        if (key == 'category' || key == 'brand' || key == 'price') {  //如果用户点击的是分类或品牌
            $scope.searchMap[key] = value;
        } else {
            $scope.searchMap.spec[key] = value;
        }

        $scope.search();
    }


    //撤销搜索项
    $scope.removeSearchItem = function (key) {
        if (key == 'category' || key == 'brand' || key == 'price') {  //如果用户点击的是分类或品牌
            $scope.searchMap[key] = "";
        } else {
            delete $scope.searchMap.spec[key];
        }
        $scope.search();
    }

    $scope.queryByPage=function (pageNo) {
        if(pageNo<1||pageNo>$scope.resultMap.totalPages){
            return ;
        }
        $scope.searchMap.pageNo=pageNo;
        $scope.search();//查询

    }

    //判断当前页是否为第一页
    $scope.isTopPage=function () {
            if($scope.searchMap.pageNo==1){
                return  true;
            }else{
                return  false;
            }
    }

    //判断当前页是否为最后一页
    $scope.isEndPage=function () {
        if($scope.searchMap.pageNo==$scope.resultMap.totalPages){
            return true;
        }else{
             return  false;
        }
    }

    //排序查询
    $scope.sortSearch=function (sortField,sort) {
        $scope.searchMap.sortFieId=sortField;
        $scope.searchMap.sort=sort;

        $scope.search();//查询
    }


});