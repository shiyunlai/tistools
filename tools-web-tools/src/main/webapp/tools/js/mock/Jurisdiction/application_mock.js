/**
 * Created by hasee on 2017/6/4.
 */
/*
Mock.mock('http://g.cn', {
    'name': '@name()',
    'age|1-100': 100,
    'color': 'white',
    'appname':'应用数据模型'
});*/



Mock.mock('http://g.cn', {
    'dataList|8': [{'id|+1': 0 , 'appname': '@name','appcode':'ABFRAME','dict_type|1':['本地','远程'],'dict_open|1':['是','否'],'dict_data':'2017-06-04','address|1'
        :['北京','上海','南京','无锡','深圳','杭州','芜湖','泰州'],'ip':'192.168.1.101',
        'port':'8080','dict_des|+1':'这里是测试数据1'}]
})