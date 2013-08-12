package com.s3prototype.panacea;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	
	Context context;
	DrawThread drawThread;
	SurfaceHolder sHolder;
	int sWidth, sHeight;
	Activity sActivity;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public GameView(Activity activity) {
		super(activity.getApplicationContext());
		sActivity = activity;
		this.context = sActivity.getApplicationContext();
		sHolder = getHolder();
		sHolder.addCallback(this);
		drawThread = new DrawThread(context, this, sHolder, sActivity);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		sWidth = width;
		sHeight = height;
		sHolder = holder;
		sHolder.addCallback(this);
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if(drawThread != null){
			if (drawThread.getState() == Thread.State.NEW) {
				drawThread.start();
			}
		} else  {
			drawThread = new DrawThread(context, this, sHolder, sActivity);
			drawThread.start();
		}//else
		drawThread.setOkToRun(true);
	}//surfaceCreated

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.setOkToRun(false);
		try{
			drawThread.join();
			drawThread = null;
		} catch (InterruptedException e){
			
		}
	}

}//class GameView
