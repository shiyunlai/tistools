<div class="crumbs">
    <ul id="breadcrumbs" class="breadcrumb">
        <li>
            <i class="icon-home">
            </i>
            <a >
                ${modelName}
            </a>
        </li>
        <li class="current">
            <a>
                ${page.name}
            </a>
        </li>

    </ul>

</div>

<!--搜索  结束-->
<div class="row"  style="padding:3px 0px 0px 0px">
    <div class="col-md-12">
        <div class="widget box">
            <div class="widget-content">
                <form class="form-horizontal row-border" action="#">
                    <div class="form-group">
					<#list page.search as s>
					 <div class="fl ml-10">
                            <label class="lab" style="width:80px">${s.name}:</label>
							<#if s.select??>
							  <select ng-model="${page.id}.searchForm.${s.id}" ng-init="${page.id}.getchangliang('${s.select}')">
							 <option value="">请选择..</option>
							  <option  ng-repeat="op in ${page.id}.chang.${s.select}" value="{{op.value}}">{{op.label}}</option>
						 </select>
							<#else>
							<input ng-model="${page.id}.searchForm.${s.id}"   type="text">
							</#if>
                        </div>
					</#list>
                        <div class="fl ml-10">
                            <button class="btn btn-sm" type="button"  ng-click="${page.id}.search1()">查找</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!--按钮工具栏  开始-->
<div class="row" style="padding:3px 0px 28px 0px">
    <div class="toolbar">
        <span class="col-md-1"><button class="btn"   ng-click="${page.id}.add()"><i class="icon-plus"></i> 增加</button></span>
        <span class="col-md-1"><button class="btn"   ng-click="${page.id}.delete()"><i class=" icon-trash"></i> 删除</button></span>
    </div>
</div>
<!--按钮工具栏  结束-->

<!--grid列表  开始-->
<div class="row">
    <div class="col-md-12">
        <div class="widget box">
            <div class="widget-content">
                <table class="table table-hover table-striped">
                    <thead>
                    <tr>
                        <th>
                            <input type="checkbox" ng-click="${page.id}.checkAll(headcheck)" ng-model="headcheck"/>
                        </th>
						<#list page.table as t>
						 <th>
                            ${t.name}
                        </th>
						</#list>
						 <th>
                            操作
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="d in ${page.id}.dataList">
                        <td>
                            <input type="checkbox" ng-model="d.checked"/>
                        </td>
						<#list page.table as t>
                          	<#if t.select??> <td  ng-init="${page.id}.getchangliang('${t.select}')">
                            {{(${page.id}.chang.${t.select}| filter:{"value":d.${t.id}}:strict|limitTo:1)[0].label}}
                        </td><#elseif t.tag??&&t.tag=='addson'>
						 <td><a ng-repeat="f in d.addson" href="../ngres/download/{{f.id}}/0/0" target="_blank">{{f.yuanshimingcheng}}</a></td>
						<#else> <td>{{d.${t.id}}}</td></#if>
						</#list>
                        <td> <a href ng-click="${page.id}.editData(d)">修改</a>
						  <a href  ng-click="${page.id}.deleteData(d)">删除</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="col-md-12">
        <div class="page_box">
            <div class="fl">总页数：{{${page.id}.searchForm.page.totalPages}},共{{${page.id}.searchForm.page.totalItems}}项</div>
            <div class=" fr">
                <div class="page_con fl">
                    <pagination boundary-links="true" total-items="${page.id}.searchForm.page.totalItems"      items-per-page="${page.id}.searchForm.page.itemsperpage"  ng-model="${page.id}.searchForm.page.currentPage"  max-size="7" num-pages
                                class="pagination-sm" previous-text="上一页" next-text="下一页"
                                first-text="首页"
                                last-text="尾页"></pagination>
                </div>
                <div class="page_con fl" style="padding-left:20px; padding-top:2px;">
                    <ul>
                        <li><input type="text" ng-model="gogogoog" class="t1"/><input type="button" class="b1"
                                                                                      ng-click="${page.id}.searchCurrent(gogogoog)"/><em>页</em>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!--grid列表结束-->
<!--弹出编辑窗口 开始-->
<style type="text/css">
    .app-modal-window .modal-dialog {
        width: 900px;
    }</style>
<script type="text/ng-template" id="edit.html">
    <div class="row">
        <div class="col-md-12">
            <div class="widget box">
                <div class="widget-header">
                    <h4>
                        <i class="icon-reorder">
                        </i>
                        ${page.name}
                    </h4>
                    <div class="fr">
                        <a  class="fr" ng-click="cancel()">关闭</a>
                    </div>

                </div>
                <div class="widget-content">
                 
                        <div class="form-group">
                            <div class="fl ml-10">
							<#list page.table as t>
							 <div class="fl ml-10">
									<label class="lab" style="width:80px">${t.name}:</label>
									<#if t.select??>
								  <select ng-model="${page.id}.item.${t.id}" ng-init="${page.id}.getchangliang('${t.select}')">
								 <option value="">请选择..</option>
								  <option ng-selected="${page.id}.item.${t.id}==op.value" ng-repeat="op in ${page.id}.chang.${t.select}" value="{{op.value}}">{{op.label}}</option>
								</select>
							<#elseif t.tag??&&t.tag=='addson'>
							  <div ng-repeat="f in ${page.id}.item.addson"  >
                                     <a  href="../ngres/download/{{f.id}}/0/0" target="_blank">{{f.yuanshimingcheng}}</a>
                                     <a  href ng-click="${page.id}.item.addson.splice($index, 1)" target="_blank">删除</a>
                                 </div>
							    <div class="fl ml-10" flow-init  flow-name="${page.id}.fileflow"
                                 flow-files-submitted="${page.id}.fileflow.upload()"><span  flow-btn>添加附件</span>
                                <div ng-repeat="file in ${page.id}.fileflow.files" class="transfer-box">
                                    {{file.relativePath}} ({{file.size}}bytes)
                                    <div class="progress progress-striped" ng-class="{active: file.isUploading()}">
                                        <div class="progress-bar" role="progressbar"
                                             aria-valuenow="{{file.progress() * 100}}"
                                             aria-valuemin="0"
                                             aria-valuemax="100"
                                             ng-style="{width: (file.progress() * 100) + '%'}">
                                            <span class="sr-only">{{file.progress()}}% Complete</span>
                                        </div>
                                    </div>
                                    <div class="btn-group">
                                        <a class="btn btn-xs btn-danger" ng-click="file.cancel()">
                                            Cancel
                                        </a>
                                    </div>
                                </div>
                            </div>
							<#else>
							<input ng-model="${page.id}.item.${t.id}"   type="text">
							</#if>
								</div>
							</#list>
                            </div>
							<!--多-->
							<#if page.subTable??>
							   <div style="clear:both"></div>
                        <div class="row" style="padding:3px 0px 28px 0px">
                            <div class="toolbar">
                                <span class="col-md-2"><button class="btn"   ng-click="${page.id}.${page.subTable.id}xuanze()"><i class="icon-plus"></i> ${page.subTable.name}</button></span>
                                <span class="col-md-1"><button class="btn"   ng-click="delete()"><i class=" icon-trash"></i> 删除</button></span>
                            </div>
                        </div>

                        <div class="col-md-12">
                            <table  width="97%" class="table table-hover table-striped">
                                <thead>
                                <th><input type="checkbox" class="check" ng-model="headcheck" ng-click="checkAll(headcheck)"/>序号</th>
								<#list page.subTable.subFields as subField>  <th>${subField.name}</th></#list>
                                <th>操作</th>
                                </thead>
                                <tbody>
                                <tr ng-repeat="d in ${page.id}.${page.subTable.id}List">
                                    <td><input type="checkbox" class="check" ng-model="d.checked"/>{{d.index=$index+1}}</td>
									<#list page.subTable.subFields as subField>  <td><input ng-model="d.${subField.id}" type="text"  style="width:60px;"/></td></#list>
                                    <td><a href ng-click="deleteData(d)">删除</a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
						</#if>
							<!--多end-->
                        </div>
                   
                    <div class="btn-toolbar align-right">
					<#if page.subTable??>
					 <button class="btn btn-primary" ng-click="${page.id}.save()">提交</button>
					<#else>
					 <button class="btn btn-primary" ng-click="submit()">提交</button>
					</#if>
                        <button class="btn btn-warning" ng-click="cancel()">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</script>
