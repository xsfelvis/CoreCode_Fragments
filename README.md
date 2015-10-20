# CoreCode_Fragments

**本仓库用于记录平时一些常用代码片段**<br>
##*uc浏览器加载前的弹跳效果核心源码 (java/com/example/elvis/sogoloading)* <br>
使用Animator
## *仿QQ聊天ListView   (chatlistdome)*<br>
- 使用俩个布局（发送和接受布局）重写Adapter的getView方法<br>
- 主要涉及到getItemViewType用来返回第position个Item的类型；getViewTypeCount（）返回不同布局的总数<br>

##*App管理核心代码 (AppManager.java)*
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

##*Androd常用辅助方法（Util.java）*
- 获取屏幕高度、宽度
- 获取屏幕密度、dip2px
- 获取状态栏高度
- 获取当前屏幕截图（包含状态看/不包含状态栏）

##*AsyncTask和Handler结合使用（MainActivity.java）*
-AsyncTask使用
-在AsyncTask的doBackInground方法使用Handler进行UI操作（doBackInground自身只能做耗时而不可做ui更新）

