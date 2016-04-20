/**
*in case of click time less 0.5s
**/

public abstract class OnClickEvent implements View.OnClickListener {

    public static long lastTime;

    public abstract void singleClick(View v);

    @Override
    public void onClick(View v) {
        if (onDoubClick()) {
            return;
        }
        singleClick(v);
    }

    public boolean onDoubClick() {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastTime;

        if (time < 500) {
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }
}


how to use:
 mClickView.setOnClickListener(new OnClickEvent() {
        @Override
        public void singleClick(View v) {

        }
    });