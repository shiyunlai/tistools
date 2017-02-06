<div class="crumbs">
    <ul id="breadcrumbs" class="breadcrumb">
        <li>
            <i class="icon-home">
            </i>
            <a href="index.html">
                 ${modelName}
            </a>
        </li>
        <li class="current">
            <a href="pages_calendar.html" title="">
                ${page.name}
            </a>
        </li>

    </ul>

</div>

<div class="row"   style="padding:3px 0px 0px 0px">
    <div class="col-md-12">
        <div class="widget box">
            <div class="widget-header">
                <h4>
                    <i class="icon-reorder">
                    </i>
                   基本信息
                </h4>

            </div>
            <div class="widget-content">
                <form class="form-horizontal row-border" action="#">
                    <div class="form-group">
                        <#list page.table as t>
							 <div class="fl ml-10">
									<label class="lab" style="width:80px">${t.name}:</label>
									<#if t.select??>
								  <select ng-model="${page.id}.item.${t.id}" ng-init="${page.id}.getchangliang('${t.select}')">
								 <option value="">请选择..</option>
								  <option ng-selected="${page.id}.item.${t.id}==op.value" ng-repeat="op in ${page.id}.chang.${t.select}" value="{{op.value}}">{{op.label}}</option>
								</select>
							<#elseif t.tag??&&t.tag=='addson'>
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
                </form>
                <div class="btn-toolbar align-right">
                    <button class="btn btn-primary" ng-click="submit()">提交</button>
                    <button class="btn btn-warning" ng-click="cancel()">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>



<!--弹出编辑窗口  结束-->
