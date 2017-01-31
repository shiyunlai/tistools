<!--右侧内容部分导航-->
<div class="row lm_top">
    <span class="lm_left_corner"></span>
    <span class="lm_center">当前位置： ${modelName}   &gt; ${table.name} &gt; ${table.name}详情</span>
    <span class="lm_right_corner"></span>
</div>

<div class="row" style="border:2px solid #e1e1e1;padding-top: 15px;">
    <form class="form-horizontal" name="form">
        <div class="col-md-12">
		<#list table.fields as field>
	<#if field.form?? && field.form=='true'>
	  <div class="col-md-3">
                <div class="form-group" style="display: block">
                    <label class="col-md-4 control-label lm_lbl">${field.name}</label>
                    <div class="col-md-8">
                        <input ng-model="${table.id}.item.${field.id}" type="text"  class="form-control lm_form" />
                    </div>
                </div>
            </div>
		</#if>
	</#list>
          
        </div>

        <div class="col-md-12">
            <div class="form-group">
                <div class="col-md-offset-11 col-md-2">
                    <button type="button" class="btn btn-default btn-xs" ng-click="${table.id}.save()">
                        <span class="glyphicon glyphicon-ok"></span> 保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>