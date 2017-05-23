[更多详细Git教程,请看这!](http://www.bronsp.com:8060/index.php?s=/5&page_id=14)
----------

### 开发人员基本git步骤

&emsp;&emsp;[全新加入](#全新加入开发 "")
&emsp;&emsp;[每日同步](#每日同步 "")
&emsp;&emsp;[新功能开发](#新功能开发 "")


#### 全新加入

	新人参与项目开发。

1. 下载代码到本地，形成本地git仓库

		git clone url

1. 创建开发分支，将开发活动与主干进行隔离

		git checkout -b develop_name
		git checkout develop_name

#### 每日同步

	保持本地开发环境与远程主干分支同步！

1. 同步本地主干

		git checkout master
		git pull

1. 同步本地开发分支

		git checkout develop_name
		git merge --no-ff master
		git commit -m "与主干同步"

1. 同步某个正在开发中的分支（ feature_xxxx, bugfix_xxxx）

		git checkout feature_xxxx
		git merge --no-ff master
		git commit -m "与主干同步"

#### 新功能开发

	开始一个新功能开发，完成并测试通过后，提交远程Pull requests。

1. 与主干同步

		git checkout master
		git pull

		git checkout develop_name
		git merge --no-ff master
		git commit -m "与主干同步"

1. 新建开发分支 feature_xxx，并切换到该分支

		git checkout develop_name
		git checkout -b feature_xxx
		git checkout feature_xxx

1. 开发过程 - coding、file、...

	查看工程状态
		git status

	添加新增
		git -A "新增,注释内容"

	提交并写注释
		git commit -m "提交,注释内容"

	查看最新3次提交日志
		git log -3

	回滚到e377f60e28c8b84158
		git reset --hard e377f60e28c8b84158

1. 合并到本地开分支，进行测试，准备交付(交付到github)

		git checkout develop_shiyl

		git merge --no-ff feature_xxx

		git commit -m "注释内容"

	在develop_name分支上测试。。。。。

	<strong>注意：</strong>
	如果测试有问题，必须切换回 feature_xxx 分支进行修改，然后再合并到develop_name分支测试，直到测试通过！</strong>

1. 提交到远程github，等待管理员处理Pull requests

		git checkout master
		git merge --no-ff develop_name
		git commit -m "提交feature_xxx"
		git push

1. 如果已经合并到本地master的分支，可以直接删除掉

		git checkout -d feature_xxx
