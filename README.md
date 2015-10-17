# CoreCode_Fragments

**本仓库用于记录平时一些常用代码片段**<br>
##*uc浏览器加载前的弹跳效果核心源码 (java/com/example/elvis/sogoloading)* <br>
使用Animator
## *仿QQ聊天ListView   (chatlistdome)*<br>
- 使用俩个布局（发送和接受布局）重写Adapter的getView方法<br>
- 主要涉及到getItemViewType用来返回第position个Item的类型；getViewTypeCount（）返回不同布局的总数<br>

##*App管理核心代码*
- 主要使用一个stack，每次onCreate的时候将活动对象进栈，finish的时候出栈，退出的时候遍历stack，全部出栈
- <code> /* 销毁*/
	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}<br>
	/***添加入栈*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
	}</code>





