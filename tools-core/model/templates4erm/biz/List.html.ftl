<div class="row lm_top">
    <span class="lm_left_corner"></span>
    <span class="lm_center">当前位置： ${modelName} &gt; ${table.name}</span>
    <span class="lm_right_corner"></span>
</div>

<!--右侧头部搜索部分-->
<div class="row" style="border:2px solid #e1e1e1;padding-top: 15px;">
    <form class="form-horizontal">
	<#list table.fields as field>
	<#if field.search?? && field.search=='true'>
	 <div class="col-md-3">
            <div class="form-group" style="display: block">
                <label  class="col-md-4 control-label lm_lbl">${field.name}</label>
                <div class="col-md-8">
                    <input type="text" class="form-control lm_form"  placeholder="${field.name}" ng-model="${table.id}.searchForm.${field.id}"/>
                </div>
            </div>
        </div>
		</#if>
	</#list>
	
        <div class="col-md-3" >
            <div class="form-group">
                <div class="col-md-3">
                    <button type="button" class="btn btn-default btn-xs" ng-click="${table.id}.search1()">
                        <span class="glyphicon  glyphicon-search"></span> 查找
                    </button>
                </div>

            </div>
        </div>
    </form>
</div>

<!--按钮工具栏  开始-->
<div class="row lm_toolbar ng-scope">
    <div class="col-md-1" ng-if="renyuan.mingcheng=='admin'">
        <span class="glyphicon glyphicon-plus"></span><a href="#${table.id}-edit/0">增加</a>
    </div>
    <div class="col-md-1" ng-if="renyuan.mingcheng=='admin'">
        <span class="glyphicon glyphicon-remove"></span><a href ng-click="${table.id}.delete()">删除</a>
    </div>
</div>
<!--按钮工具栏  结束-->

<!--右侧表格列表区-->
<div class="row  lm_table " >
    <table class="table table-condensed  table-striped table-hover">
        <thead>

        <tr>
            <th><span class="lm_tablespan"> <input type="checkbox" ng-click="${table.id}.checkAll(headcheck)" ng-model="headcheck"></span></th>
			<#list table.fields as field>
			<#if field.form??&&field.form=='true'>
			 <th>
                ${field.name}
            </th>
			</#if>
			</#list>
            <th>
                操作
            </th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="d in ${table.id}.dataList">
            <td>
                <span class="lm_tablespan"> <input type="checkbox" ng-model="d.checked"/></span>
            </td>
			   <#list table.fields as field>
			<#if field.form??&&field.form=='true'>
			 <td>{{d.${field.id}}}</td>
			</#if>
			</#list>         

            <td>
                <span ng-if="renyuan.mingcheng=='admin'"><span class="glyphicon   glyphicon-pencil"></span><a ng-href="#${table.id}-edit/{{d.id}}">修改</a></span>
                <span ng-if="renyuan.mingcheng=='admin'"><span class="glyphicon   glyphicon-remove"></span><a href ng-click="${table.id}.deleteData(d)">删除</a></span>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<!--表格下面的工具栏分页-->
<div class="row" style="border:2px solid  #e1e1e1;height: 30px;">

    <div style="float:right; ">
        <span style="padding-right: 20px;">共{{${table.id}.searchForm.page.totalItems}}条</span>
            <span style="padding-right: 20px;">每页<select ng-model="${table.id}.searchForm.page.itemsperpage">
                <option>10</option>
                <option>20</option>
                <option>30</option>
            </select>条</span>
        <span style="padding-right: 20px;">第{{${table.id}.searchForm.page.currentPage}}/{{${table.id}.searchForm.page.totalPages}}页</span>
        <span style="padding-right: 30px;">转到:<input type="number" style="width:40px;"  ng-model="${table.id}.searchForm.page.currentPage">页</span>
        <span style="padding-right: 30px;"><span class="lm_pointer" ng-click="${table.id}.firstPage()">首页</span>|<span  class="lm_pointer"  ng-click="${table.id}.prePage()">上一页</span>|<span  class="lm_pointer"   ng-click="${table.id}.nextPage()">下一页</span>|<span  class="lm_pointer"  ng-click="${table.id}.lastPage()">尾页</span></span>
    </div>
</div>