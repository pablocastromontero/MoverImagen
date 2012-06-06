package com.pernix.moverimagen;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

public class MoverImagenActivity extends Activity implements OnGestureListener
{
    private int screen_w = 320;
    private int screen_h = 480;
    private int scrollX = 0;
    private int scrollY = 0;
    
    private int imageWidth = 0;
    private int imageHeight = 0;
     
    MoveImageView main;  
    Bitmap sourceBmp;
    Bitmap showBmp;
    Resources res;
    Paint paint;
    GestureDetector gestureScanner;
     
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); 
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        res = getResources();
        
        DisplayMetrics dm = res.getDisplayMetrics();
        screen_w = dm.widthPixels;
        screen_h = dm.heightPixels-20;
        
        gestureScanner = new GestureDetector(this);
        paint = new Paint();
       
        sourceBmp = BitmapFactory.decodeResource(res, R.drawable.image2);
        imageWidth = sourceBmp.getWidth();
        imageHeight = sourceBmp.getHeight();
        showBmp = Bitmap.createBitmap(sourceBmp);

        main = new MoveImageView(this);       
        setContentView(main, new ViewGroup.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    }
   
    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
        return gestureScanner.onTouchEvent(me);
    }
   
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        main.handleScroll(distanceX,distanceY);
        return true;
    }
   
    @Override
    public boolean onDown(MotionEvent e)
    {
        return true;
    }
   
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        return true;
    }
   
    @Override
    public void onLongPress(MotionEvent e){    }
   
    @Override
    public void onShowPress(MotionEvent e) {   }    
   
    @Override
    public boolean onSingleTapUp(MotionEvent e)    
    {
        return true; 
    }

    class MoveImageView extends View
    {
         public MoveImageView(Context context)
         {
              super(context);
         }
         
         @Override
         protected void onDraw(Canvas canvas)
         {
              canvas.drawBitmap(showBmp, 0, 0, paint);
         }
         
         public void handleScroll(float distX, float distY)
         {         
               if ( (scrollX <= imageWidth-screen_w) && (scrollX >= 0) )
               {
                    scrollX += distX;
               }
            
               if ( (scrollY <= imageHeight-screen_h) && (scrollY >= 0) )
               {
                    scrollY += distY;
               }
               
               if (scrollX<0) scrollX = 0;
               if (scrollY<0) scrollY = 0;
               if (scrollX>imageWidth-screen_w) scrollX = imageWidth-screen_w;
               if (scrollY>imageHeight-screen_h) scrollY = imageHeight-screen_h;
 
               if((scrollX <= imageWidth-screen_w) && (scrollY <= imageHeight-screen_h))
               {
                   showBmp = Bitmap.createBitmap(sourceBmp, scrollX, scrollY, screen_w, screen_h);              
                   invalidate();
               }
         }
    }
}