# CoreCode_Fragments

**本仓库用于记录平时一些常用代码片段**<br>
##*uc浏览器加载前的弹跳效果核心源码 (java/com/example/elvis/sogoloading)* 
- 使用Animator
- canvas绘图

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
- AsyncTask使用
- 在AsyncTask的doBackInground方法使用Handler进行UI操作（doBackInground自身只能做耗时而不可做ui更新）

##*带有进度条的AsynTask下载例子（AsynTaskTest.java）*
- 将AsynTask四个子函数全部用上的demo很简单

##*常用的颜色配置文件（colors.xml）*
- 反编译网易GaCha的资源获得
- 有很多种颜色可供选择非常方便

##*自定义圆形头像控件(CircleImageViewNew.java)*
- 需要在values文件夹下设置对应的资源属性即可

##*surceView 编写模板(surceViewDemo.java)*
- surceView编写模板 helloworld

##*带删除功能的EditText控件核心代码( EditDel.java)*
- 带删除功能的自定义EditText控件

##*Activity操作的一些常用工具方法(ActivityUtil.java)*
-  随时关闭所有Activity
-  杀死整个App,连后台都要杀死
-  双击返回键退出程序

##*Activity切换动画的常用配置文件（AnimatorXmlActivity）*
- 主要有
- 对应效果：
- 淡入淡出效果
- 放大淡出效果
- 转动淡出效果1
- 转动淡出效果2
- 左上角展开淡出效果
- 压缩变小淡出效果
- 右往左推出效果
- 下往上推出效果
- 左右交错效果
- 放大淡出效果
- 缩小效果
- 上下交错效果

##*Volley框架练习Demo（Volley学习核心代码）*
- volley常用的post/get请求方式
- Volley3中图片加载方式

##*viewPager+fragment实现框架（viewPager+fragment）*
- 类似微信滑动框架


##*HorizontalScrollViewWithListener.java*
- 带水平左右滑动监听的HorizonScrollView 
- 项目需求，使用在分享部分

##*Async*
- 线程池处理提供UI DB BG三种场景

##*citypickerDemo*
- 自定义 选择侧栏（类似通信录 ）
- 复杂Adapter的学习使用

##*RecyclerView的2种监听方式.java*
