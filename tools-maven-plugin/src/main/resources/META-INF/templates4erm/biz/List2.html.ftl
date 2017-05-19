<!-- BEGIN PAGE HEADER-->

<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="#">${modelName} </a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="#">${table.name}</a>
        </li>

    </ul>

</div>

<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <!-- Begin: life time stats -->
        <div class="portlet">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-list"></i>${table.name}
                </div>
				 <div class="actions">
                    <div class="btn-group btn-group-sm btn-group-solid">
                        <button type="button" class="btn blue" ng-click="add()" >新增</button>
                        <button type="button" class="btn red"  ng-click="${table.id}.delete()">删除</button>
                    </div>

                </div>
            </div>
            <div class="portlet-body">
                <div class="table-container">
                    <div id="datatable_ajax_wrapper" class="dataTables_wrapper dataTables_extended_wrapper no-footer">
                    <!--开始table表头分页以及工具栏-->
                        <div class="row">
                            <div class="col-md-8 col-sm-12">
                                <div class="dataTables_paginate paging_bootstrap_extended">
                                    <div class="pagination-panel"> 第 <a ng-click="${table.id}.prePage()"
                                                                           class="btn btn-sm default prev"
                                                                           title="Prev"><i class="fa fa-angle-left"></i></a><input
                                            type="text"  ng-model="${table.id}.searchForm.page.currentPage"
                                            class="pagination-panel-input form-control input-mini input-inline input-sm"
                                            maxlenght="5" style="text-align:center; margin: 0 5px;"><a   ng-click="${table.id}.nextPage()"
                                                                                                       class="btn btn-sm default next"
                                                                                                       title="Next"><i
                                            class="fa fa-angle-right"></i></a> 页,&nbsp;&nbsp;&nbsp;总<span class="pagination-panel-total">{{${table.id}.searchForm.page.totalPages}}</span>页
                                    </div>
                                </div>
                                <div class="dataTables_length" ><label><span
                                        class="seperator">|</span>每页 <select ng-model="${table.id}.searchForm.page.itemsperpage" class="form-control input-xsmall input-sm input-inline">
                                    <option value="10">10</option>
                                    <option value="20">20</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                    <option value="150">150</option>
                                </select> 条</label></div>
                                <div class="dataTables_info"  role="status" aria-live="polite">
                                    <span class="seperator">|</span>总计{{${table.id}.searchForm.page.totalItems}} 条
                                </div>
                            </div>
                            <div class="col-md-4 col-sm-12">

                            </div>
                        </div>
                    <!--结束table表头分页以及工具栏-->
                    <!--开始table-->
                        <div class="table-scrollable" style="overflow-y: auto">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr role="row" class="heading">
                                    <th width="1%">
                                        <input type="checkbox" class="group-checkable">
                                    </th>
                                    <th width="1%">
                                        序号
                                    </th>
									<#list table.fields as field>
									<#if field.form??&&field.form=='true'>
									 <th width="10%">
										${field.name}
									</th>
									</#if>
									</#list>
                                   
                                    <th width="10%">
                                       <div style="width:120px">操作</div>
                                    </th>
                                </tr>
                                <tr role="row" class="filter">
                                    <td></td>
                                    <td></td>
									<#list table.fields as field>
									<#if field.form??&&field.form=='true'>
									 <td>
                                        <input type="text" class="form-control form-filter input-sm" ng-model="${table.id}.searchForm.searchItems.${field.id}_lk">
                                    </td>
									</#if>
									</#list>
                                    <td>
                                        <button class="btn btn-xs  filter-submit margin-bottom" ng-click="${table.id}.searchN()"><i
                                                class="fa fa-search"></i> 查找
                                        </button>
                                        <button class="btn btn-xs  filter-cancel" ng-click="${table.id}.reset()"><i class="fa fa-times"></i> 重置
                                        </button>
                                    </td>

                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-repeat="d in ${table.id}.dataList" ng-class-odd="'odd'" ng-class-even="'even'">
                                    <td><span> <input type="checkbox" ng-model="d.checked" class="group-checkable"/></span></td>
                                    <td>{{$index+1}}</td>
									<#list table.fields as field>
									<#if field.form??&&field.form=='true'>
									 <td>
									 {{d.${field.id}}}
                                    </td>
									</#if>
									</#list>
                                    <td>
                                        <div class="btn-group btn-group-xs">
                                            <button type="button" class="btn " ng-click="edit(d)">编辑</button>
                                            <button type="button" class="btn " ng-click="${table.id}.deleteData(d)">删除</button>
                                        </div>
                                    </td>
                                </tr>

                                </tbody>
                            </table>
                        </div>
                    <!--结束table-->
                    <!--开始table尾部分页以及工具栏-->
                    <div class="row">
                        <div class="col-md-8 col-sm-12">
                            <div class="dataTables_paginate paging_bootstrap_extended">
                                <div class="pagination-panel"> 第 <a ng-click="${table.id}.prePage()"
                                                                    class="btn btn-sm default prev"
                                                                    title="Prev"><i class="fa fa-angle-left"></i></a><input
                                        type="text"  ng-model="${table.id}.searchForm.page.currentPage"
                                        class="pagination-panel-input form-control input-mini input-inline input-sm"
                                        maxlenght="5" style="text-align:center; margin: 0 5px;"><a   ng-click="${table.id}.nextPage()"
                                                                                                     class="btn btn-sm default next"
                                                                                                     title="Next"><i
                                        class="fa fa-angle-right"></i></a> 页,&nbsp;&nbsp;&nbsp;总<span class="pagination-panel-total">{{${table.id}.searchForm.page.totalPages}}</span>页
                                </div>
                            </div>
                            <div class="dataTables_length" ><label><span
                                    class="seperator">|</span>每页 <select ng-model="${table.id}.searchForm.page.itemsperpage" class="form-control input-xsmall input-sm input-inline">
                                <option value="10">10</option>
                                <option value="20">20</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                                <option value="150">150</option>
                            </select> 条</label></div>
                            <div class="dataTables_info"  role="status" aria-live="polite">
                                <span class="seperator">|</span>总计{{${table.id}.searchForm.page.totalItems}} 条
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-12">

                        </div>
                    </div>
                    <!--结束table尾部分页以及工具栏-->
                    </div>
                </div>
            </div>
        </div>
        <!-- End: life time stats -->
    </div>
</div>
<!-- END PAGE CONTENT-->
