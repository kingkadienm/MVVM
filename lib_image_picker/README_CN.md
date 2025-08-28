
```sh
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_MEDIA_STORAGE" />
<uses-permission android:name="android.permission.WRITE_SETTINGS" />
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />

Android 13版本适配，细化存储权限
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
<uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
```

Android 11 使用相机，需要再AndroidManifest.xm 添加如下代码：

```sh
<queries package="${applicationId}">
    <intent>
        <action android:name="android.media.action.IMAGE_CAPTURE">

        </action>
    </intent>
    <intent>
        <action android:name="android.media.action.ACTION_VIDEO_CAPTURE">

        </action>
    </intent>
</queries>
 


 
 

1、获取图片

```sh
PictureSelector.create(this)
   .openGallery(SelectMimeType.ofImage())
   .setImageEngine(GlideEngine.createGlideEngine())
   .forResult(new OnResultCallbackListener<LocalMedia>() {
      @Override
      public void onResult(ArrayList<LocalMedia> result) {

      }

      @Override
      public void onCancel() {

      }
});
```

使用系统相册

```sh
PictureSelector.create(this)
     .openSystemGallery(SelectMimeType.ofImage())
     .forResult(new OnResultCallbackListener<LocalMedia>() {
        @Override
        public void onResult(ArrayList<LocalMedia> result) {

        }

        @Override
        public void onCancel() {

        }
});
```

2、单独拍照

```sh
PictureSelector.create(this)
     .openCamera(SelectMimeType.ofImage())
     .forResult(new OnResultCallbackListener<LocalMedia>() {
        @Override
        public void onResult(ArrayList<LocalMedia> result) {

        }

        @Override
        public void onCancel() {

        }
});
```

Navigation Fragment场景下，单独拍照请使用如下方式:

```sh
PictureSelector.create(this)
     .openCamera(SelectMimeType.ofImage())
     .forResultActivity(new OnResultCallbackListener<LocalMedia>() {
        @Override
        public void onResult(ArrayList<LocalMedia> result) {

        }

        @Override
        public void onCancel() {

        }
});
```


3、您还可以按如下示例使用：

(1)、注入到任意视图层

```sh

PictureSelector.create(this)
   .openGallery(SelectMimeType.ofAll())
   .setImageEngine(GlideEngine.createGlideEngine())
   .buildLaunch(R.id.fragment_container, new OnResultCallbackListener<LocalMedia>() {
      @Override
      public void onResult(ArrayList<LocalMedia> result) {
      
      }

      @Override
      public void onCancel() {
      
      }
});
			
```

(2)、自行注入到任意视图层

```sh

PictureSelectorFragment selectorFragment = PictureSelector.create(this)
     .openGallery(SelectMimeType.ofAll())
     .setImageEngine(GlideEngine.createGlideEngine())
     .build();
     
getSupportFragmentManager().beginTransaction()
     .add(R.id.fragment_container, selectorFragment, selectorFragment.getFragmentTag())
     .addToBackStack(selectorFragment.getFragmentTag())
     .commitAllowingStateLoss();
			
```

4、单独获取数据源

(1)、专辑列表
```sh

PictureSelector.create(this)
    .dataSource(SelectMimeType.ofAll())
    .obtainAlbumData(new OnQueryDataSourceListener<LocalMediaFolder>() {
        @Override
        public void onComplete(List<LocalMediaFolder> result) {

        }
   );

```

(2)、相册列表
```sh

PictureSelector.create(this)
    .dataSource(SelectMimeType.ofAll())
    .obtainMediaData(new OnQueryDataSourceListener<LocalMedia>() {
        @Override
        public void onComplete(List<LocalMedia> result) {

        }
   );

```

(3)、根据IBridgeMediaLoader获取指定数据
```sh

IBridgeMediaLoader loader = PictureSelector.create(this)
    .dataSource(SelectMimeType.ofImage()).buildMediaLoader();
    loader.loadAllAlbum(new OnQueryAllAlbumListener<LocalMediaFolder>() {
        @Override
        public void onComplete(List<LocalMediaFolder> result) {

        }
  });

```


5、预览图片、视频、音频

如果预览网络视频AndroidManifest.xml添加如下代码

```sh
android:usesCleartextTraffic="true"
```

```sh

PictureSelector.create(this)
    .openPreview()
    .setImageEngine(GlideEngine.createGlideEngine())
    .setExternalPreviewEventListener(new OnExternalPreviewEventListener() {
       @Override
       public void onPreviewDelete(int position) {

       }

        @Override
       public boolean onLongPressDownload(LocalMedia media) {
           return false;
       }
    }).startActivityPreview(position, true, list);

```


设置图片选择器主题，更多请参阅 [文档](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-3.0-%E4%B8%BB%E9%A2%98api%E8%AF%B4%E6%98%8E)

```sh
.setSelectorUIStyle();
```
或者您可以重载布局，更多请参阅[文档](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-3.0-%E5%A6%82%E4%BD%95%E9%87%8D%E8%BD%BD%E5%B8%83%E5%B1%80%EF%BC%9F)

```sh
.setInjectLayoutResourceListener(new OnInjectLayoutResourceListener() {
   @Override
   public int getLayoutResourceId(Context context, int resourceSource) {
	return 0;
   }
```

高级用例如下所示：

1、使用自定义相机功能，详情请参阅 [文档](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-3.0-%E5%A6%82%E4%BD%95%E8%87%AA%E5%AE%9A%E4%B9%89%E7%9B%B8%E6%9C%BA%EF%BC%9F)

```sh
.setCameraInterceptListener(new OnCameraInterceptListener() {
    @Override
    public void openCamera(Fragment fragment, int cameraMode, int requestCode){
	                                    
    }
});
```

2、使用图片压缩功能，详情请参阅 [文档](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-3.0-%E5%A6%82%E4%BD%95%E5%8E%8B%E7%BC%A9%EF%BC%9F)

```sh
.setCompressEngine(new CompressFileEngine() {
   @Override
   public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call){
                                    
   }
});
```

3、使用图片裁剪功能，详情请参阅 [文档](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-3.0-%E5%A6%82%E4%BD%95%E8%A3%81%E5%89%AA%EF%BC%9F)

```sh

.setCropEngine(new CropFileEngine() {
   @Override
   public void onStartCrop(Fragment fragment, Uri srcUri, Uri destinationUri, ArrayList<String> dataSource, int requestCode) {
                                    
   }
});
```

4、使用图片编辑功能，详情请参阅 [文档](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-3.0-%E5%A6%82%E4%BD%95%E7%BC%96%E8%BE%91%E5%9B%BE%E7%89%87%EF%BC%9F)

```sh
.setEditMediaInterceptListener(new OnMediaEditInterceptListener() {
    @Override
    public void onStartMediaEdit(Fragment fragment, LocalMedia currentLocalMedia, int requestCode) {
                                    
    }
});

```

5、加载自定义数据源，详情请参阅 [文档](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-3.0-%E5%A6%82%E4%BD%95%E5%8A%A0%E8%BD%BD%E8%87%AA%E5%AE%9A%E4%B9%89%E6%95%B0%E6%8D%AE%E6%BA%90%EF%BC%9F)

```sh
.setExtendLoaderEngine(new ExtendLoaderEngine() {
    @Override
    public void loadAllAlbumData(Context context, OnQueryAllAlbumListener<LocalMediaFolder> query) {
                                    
    }

    @Override
    public void loadOnlyInAppDirAllMediaData(Context context, OnQueryAlbumListener<LocalMediaFolder> query) {

    }

    @Override
    public void loadFirstPageMediaData(Context context, long bucketId, int page, int pageSize, OnQueryDataResultListener<LocalMedia> query) {

    }

    @Override
    public void loadMoreMediaData(Context context, long bucketId, int page, int limit, int pageSize, OnQueryDataResultListener<LocalMedia> query) {

    }
 });


```

6、自定义权限申请，详情请参阅 [文档](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-3.0-%E5%A6%82%E4%BD%95%E8%87%AA%E5%AE%9A%E4%B9%89%E6%9D%83%E9%99%90%E7%94%B3%E8%AF%B7-%EF%BC%9F)

```sh
.setPermissionsInterceptListener(new OnPermissionsInterceptListener() {
      @Override
      public void requestPermission(Fragment fragment, String[] permissionArray, OnRequestPermissionListener call) {

      }

      @Override
      public boolean hasPermissions(Fragment fragment, String[] permissionArray) {
      	return false;
      }
});

```

7、Android 10 及以上版本，沙盒机制文件处理，详情请参阅 [文档](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-3.0-%E5%A6%82%E4%BD%95%E8%AE%BF%E9%97%AE%E6%B2%99%E7%9B%92%E5%A4%96%E8%B5%84%E6%BA%90%EF%BC%9F)

```sh
.setSandboxFileEngine(new UriToFileTransformEngine() {
    @Override
    public void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call) {
                                        
    }
});
```


## 混淆配置 
```sh

`` 
