public class MySurfaceView extends SurfaceView implements Callback, Runnable {  
    // 用于控制surfacView  
    private SurfaceHolder mHolder;  
    // 声明一个画笔  
    private Paint paint;  
    // 文本的坐标  
    private int textX = 10, textY = 10;  
    // 声明一个线程  
    private Thread th;  
    // 线程消亡的标志位  
    private boolean flag;  
    // 声明一个画布  
    private Canvas canvas;  
    // 声明屏幕的宽高  
    private int screenW, screenH;  
  
    public MySurfaceView(Context context) {  
        super(context);  
        // 实例化mHolder  
        mHolder = this.getHolder();  
        // 为surfaceView添加监听器  
        mHolder.addCallback(this);  
        // 实例化画笔  
        paint = new Paint();  
        // 实例化画笔颜色为白色  
        paint.setColor(Color.WHITE);  
        // 设置焦点  
        setFocusable(true);  
    }  
  
    @Override  
    public void surfaceCreated(SurfaceHolder holder) {  
        screenW = this.getWidth();  
        screenH = this.getHeight();  
        flag = true;  
        // 实例化线程  
        th = new Thread(this);  
        // 启动线程  
        th.start();  
    }  
  
    @Override  
    public void surfaceChanged(SurfaceHolder holder, int format, int width,  
            int height) {  
        // TODO Auto-generated method stub  
  
    }  
  
    @Override  
    public void surfaceDestroyed(SurfaceHolder holder) {  
        flag = true;  
  
    }  
  
    @Override  
    public void run() {  
        while (flag) {  
            long start = System.currentTimeMillis();  
            myDraw();  
            logic();  
            long end = System.currentTimeMillis();  
            try {  
                if (end - start < 50) {  
                    Thread.sleep(50 - (end - start));  
                }  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
  
    }  
 <pre name="code" class="java">       /* 游戏处理逻辑 */  
    private void logic() {  
        // 处理游戏逻辑部分  
  
    }  
  
    private void myDraw() {  
        try {  
            canvas = mHolder.lockCanvas();  
            if (canvas != null) {  
                // 这里采用绘制矩形方式刷屏  
                // 绘制矩形  
                canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);  
                canvas.drawText("Hello World", textX, textY, paint);  
            }  
        } catch (Exception e) {  
  
        } finally {  
            if (canvas != null)  
                mHolder.unlockCanvasAndPost(canvas);  
        }  
  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        textX = (int) event.getX();  
        textY = (int) event.getY();  
        return true;  
    }  
  
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        return super.onKeyDown(keyCode, event);  
    }  
      
  
}  