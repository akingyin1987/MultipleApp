package com.akingyin;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import java.io.IOException;

/**
 * <!-- 手电筒 -->
 * Call requires API level 5
 * <uses-permission android:name="android.permission.FLASHLIGHT"/>
 * <uses-permission android:name="android.permission.CAMERA"/>
 *
 * @author MaTianyu
 * @date 2014-11-04
 */
public class FlashLight {

    private Camera camera;
    private Handler handler = new Handler();

    /**
     * 超过3分钟自动关闭，防止损伤硬件
     */
    private static final int OFF_TIME = 3 * 60 * 1000;

    public static Camera open(int cameraId) {

        //手机上camera的数量 =0 则当前手机无摄像头
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            return null;
        }
        boolean explicitRequest = cameraId >= 0;
        if (!explicitRequest) {
            // Select a camera if no explicit camera requested
            int index = 0;
            //遍历当前所有摄像头信息
            while (index < numCameras) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(index, cameraInfo);
                //判断是否是后置摄像头
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    System.out.println("后置");
                    break;
                }
                index++;
            }
            System.out.println("index=="+index);
            cameraId = index;
        }
        Camera camera;
        if (cameraId < numCameras) {
            camera = Camera.open(cameraId);
        } else {
            if (explicitRequest) {
                camera = null;
            } else {
                camera = Camera.open(0);
            }
        }
         System.out.println("camera=="+(null == camera));
        return camera;
    }

    public   void  openCamera(){
        if(null == camera){
            System.out.println("无相机");
            camera = open(-1);
            System.out.println("null==camenra"+(null == camera));
        }
    }

    public boolean turnOnFlashLight() {
        openCamera();
        if (camera != null) {
            try {
                camera.setPreviewTexture(new SurfaceTexture(10));
                //处理android 7.0无法打开
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
            Camera.Parameters parameter = camera.getParameters();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            } else {
                parameter.set("flash-mode", "torch");
            }
            camera.setParameters(parameter);
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    turnOffFlashLight();
                }
            }, OFF_TIME);
        }
        return true;
    }

    public boolean turnOffFlashLight() {
        openCamera();
        if (camera != null) {
            handler.removeCallbacksAndMessages(null);
            Camera.Parameters parameter = camera.getParameters();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            } else {
                parameter.set("flash-mode", "off");
            }
            camera.setParameters(parameter);
            camera.stopPreview();

        }
        return true;
    }

    public  void  onDestory(){
        if(null != camera){
            camera.stopPreview();
            camera.release();
            camera = null;
        }

    }
}
