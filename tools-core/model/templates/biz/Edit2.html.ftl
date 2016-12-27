<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="index.html">${modelName} </a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="#">新增/编辑${table.name}</a>
        </li>
    </ul>

</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row" ng-form="bigform">
<div class="col-md-12">

<div class="portlet light bordered ">
<div class="portlet-title">
    <div class="caption">
        <i class="fa fa-pencil"></i>{{${table.id}.item.suoshukehu||'开始新的'}}
    </div>
    <div class="actions btn-set">
        <button type="button" name="back" class="btn default" ng-click="$root.settings.utils.back()"><i class="fa fa-angle-left"></i> 返回</button>
        <button ng-disabled="bigform.$invalid" class="btn green" ng-click="${table.id}.save(1)"popover-trigger="mouseenter"><i class="fa fa-check"></i> 保存并返回</button>
        <button ng-disabled="bigform.$invalid"  class="btn green" ng-click="${table.id}.save(2)"  popover-trigger="mouseenter"><i class="fa fa-check-circle"></i>保存并新增</button>

    </div>
</div>
<div class="portlet-body">
    <div class="row">
        <div class="col-md-12">
                <div class="form">
                    <!-- BEGIN FORM-->
                    <form action="#" name="myform" class="form-horizontal">
                        <div class="form-body">
                           
                            <!--/row-->
					<#list table.fields as field>
						<#if field.form?? && field.form=='true'>
							 <div class="col-md-6" >
								<div class="form-group">
									<label class="control-label col-md-3">${field.name}</label>
									<div class="col-md-9">
										<input type="text" class="form-control" ng-model="${table.id}.item.${field.id}"  name="${field.id}"/>
									</div>
								</div>
							</div>
						</#if>
					</#list>
							

                        </div>

                     </form>
                    <!-- END FORM-->

                </div>
        </div>
    </div>
</div>
</div>
</form>
</div>
</div>
