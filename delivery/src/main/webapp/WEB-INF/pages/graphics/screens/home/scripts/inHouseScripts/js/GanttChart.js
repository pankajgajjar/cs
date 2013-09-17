var GanttChart = function(){

    var currentPath;
    var parentNode;
    var newNode
    var currentRow;
    var dropTargetType

    var input;

    this.createGanttChart = function(id){
        TreeGrid({Layout:{Url:"../../../graphics/screens/home/scripts/inHouseScripts/js/Def.js"},
                  Data:{Script:"myData"}},id);
    }

    Grids.OnGetMenu = function(G,row,col){
        console.log(row)
        var possibleDim=[];
        possibleDim  = GraphicDataStore.getPossibleChild(row.type);
        var menuItems = [];
        var menu = {Items:menuItems};

        if(possibleDim != ""){
            for(var i=0; i< possibleDim.length; i++){
                //This is to create possible dimensions on click of currentNode
                var item = {};
                var keyName = "Name";
                item[keyName] = possibleDim[i];
                var keyName = "Text";
                item[keyName] = "Create "+possibleDim[i];
                menuItems.push(item);
            }

        }

        if(row.type != "root"){
            //This is to delete the currentNode
            var deleteItem = {};
            var keyName = "Name";
            deleteItem[keyName] = "Delete";
            var keyName = "Text";
            deleteItem[keyName] = "Delete";
            menuItems.push(deleteItem);
        }
        return menu;
    }


    Grids.OnRowDelete = function(grid,row,type){
        DeleteDimension.deleteDim(function(){
            alert("Deleted successfully")
        });
    }

    Grids.OnContextMenu = function(G,row,col,name){
        currentRow = row;
        if(name == "Delete"){
            G.DeleteRow(row);
        }else{
             showPopUp(G,row,col,name);

        }
    }

    Grids.OnStartDrag = function(grid,row,col){
        //To suppress the dragging as per the dimension type
        if(row.type == "root") {
            return true;
        }
        return false;
    }

    Grids.OnCanDrop = function(grid,row,togrid,torow,type,copy){
        dropTargetType = GraphicDataStore.getPossibleDropParent(row.type);
        var dropTargetFound = $.inArray(torow.type, dropTargetType)
        if(dropTargetFound != -1){
            return 2;
        }
        return 0;

    }

    Grids.OnEndDrag = function(grid,row,togrid,torow,type,X,Y,copy){
       if(type === 2){
           var oldPathForChild = row.path;
           var newChildNode = row;
           var parentNode = torow;
           newChildNode.path = parentNode.path +","+parentNode.title;
           var newPathForChild = newChildNode.path;
           var flag=isFolder(newChildNode.type);
           var prefix;
           prefix =getUrlPrefix(row.type,"move");
           prefix = prefix+row.type;
           GanttChartPresenter.dragAndDropDimensions(prefix,row.title,oldPathForChild,flag,newPathForChild,onDropSuccess);

       }
    }

    function onDropSuccess(){
       /* if(draggedNode.data.type == "Assortment"){
            var cb = draggedNode.toDict(true, function(dict){
                //dict.title = "Copy of " + dict.title;
                delete dict.key; // Remove key, so a new one will be created
            });
            droppedSrcNode.addChild(cb);
        }
        else{
            draggedNode.move(droppedSrcNode, dragHitMode);
        }*/
    }

    function addNode(data){
        Grids[0].AddRow(currentRow,null,1);
        Grids[0].SetValue(currentRow.lastChild,"name",newNode.title,1);
        Grids[0].SetValue(currentRow.lastChild,"title",newNode.title,1);
        Grids[0].SetValue(currentRow.lastChild,"path",newNode.path,1);
        Grids[0].SetValue(currentRow.lastChild,"id",newNode.title,1);
        Grids[0].SetValue(currentRow.lastChild,"type",newNode.type,1);
        Grids[0].SetValue(currentRow.lastChild,"Items",newNode.Items,1);
    }

    function showPopUp(G,row,col,name){
        $( "#dialog-form" ).dialog({
            height: 490,
            width: 500,
            modal: true,
            buttons: {
            "Create ": function() {
                var bValid = true;
                var dimensionName = $( "#name" ),
                    manager = $( "#manager" ),
                    startdate = $( "#startdate" ),
                    enddate = $( "#enddate" ),
                    budgetowner = $( "#budgetowner" ),
                    budgetamount = $( "#budget"),
                    currency = $( "#currency" );

                input = new Object();
                input.dimensionName=dimensionName.val();
                input.managerName=manager.val();
                input.startDate=startdate.val();
                input.endDate=enddate.val();
                input.budgetOwner = budgetowner.val();
                input.budget = budgetamount.val();
                input.currency = currency.val();
                console.log(input);
                if(input.dimensionName != null && input.dimensionName !=""){
                    parentNode = row;
                    if(parentNode.type == "root"){
                        currentPath = "-1";
                    }
                    else{
                        currentPath = parentNode.path+","+ parentNode.title;
                        if(currentPath.indexOf("-1")==0)
                            currentPath = currentPath.match(/([^,]*),(.*)/)[2];   //To remove -1 root folder
                    }

                    var flag = isFolder(name);
                    var prefix=getUrlPrefix(name,"create");
                    newNode = createNewRow(input.dimensionName,name,currentPath);
                    if(name == "Assortment"){
                        GanttChartPresenter.createAssortment(prefix,name,input.dimensionName,currentPath,flag,addNode);
                    }else{
                        GanttChartPresenter.createDimension(prefix,name,input.dimensionName,currentPath,flag,addNode);
                    }
                }


                $( this ).dialog( "close" );

            },
            Cancel: function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            //allFields.val( "" ).removeClass( "ui-state-error" );
        },
        autoOpen :true,
        changeTitle: document.getElementById("ui-id-1").innerHTML="Create New " + name,
        changeLabel: document.getElementById("dimensionName").innerHTML=name + " Name",
        datePicker: $(function() {
                        $( '.datePicker' ).datepicker({
                            showOn: 'both',
                            buttonImage: 'calendar-icon.png',
                            buttonImageOnly: true,
                            //changeMonth: true,
                            changeYear: true,
                            showAnim: 'blind',
                            showButtonPanel: true
                        });
                    })
        });
    }

    function  createNewRow(name,type,path){
        var newRowNode = {
            "id": "",
            "title": name,
            "type": type,
            "path": path,
            "Items": []
        }
        return newRowNode;
    }

    function isFolder(dim){
        var flag =true;
        if(dim == "Page" || dim == "Assortment"){
            flag = false;
        }
        return flag;
    }

    var getUrlPrefix=function(type,action){
        switch(type){
            case "Chapter":
                return  "/delivery/chapter/"+action+"/";
            case "Page":
                return  "/delivery/page/"+action+"/";
            case "Assortment":
                return  "/delivery/assortment/"+action+"/";
        }
        return "/delivery/dimension/"+action+"/";
    }


}
