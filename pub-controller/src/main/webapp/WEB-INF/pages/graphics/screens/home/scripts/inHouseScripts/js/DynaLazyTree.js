var DynaLazyTree = function(){
    this.createTree = function(treeObj,urls){
            $(treeObj).dynatree({
                //children: data,
                initAjax: {
                    url:urls
                },
                onLazyRead: function(node){
                    node.appendAjax({
                        url: "/pub-controller/testdrive/mocks/tree/AssetChildTree.json"
                    });
                }
            });
    }

};

