package com.example.myglide


import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import kotlinx.android.synthetic.main.activity_main.*
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget

import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth









class MainActivity : Activity() , View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onClick(p0: View?) {
//        loadImage()
//        loadImageThumbnail()
//        loadImageAnimation()
//        loadImageCut()
//          loadCache()
//        loadImagePrority()
//        loadImageTarget()
//        loadImageTransform()
        loadSelfDefineAnimate()
    }

    //普通加载网络图片-占位图
    fun loadImage(){

        Log.d("feifei","loadImage ----"+Thread.currentThread().name)
        val url= "http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg"
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(iv_image)
    }

    //首先显示缩略图，再展示原图
    fun loadImageThumbnail(){
        Log.d("feifei","loadImageThumbnail ----"+Thread.currentThread().name)
        val url= "http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg"
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .thumbnail(0.2f)
                .into(iv_image)
    }

    //动画开关 - 渐进动画
    fun loadImageAnimation(){
        Log.d("feifei","loadImageThumbnail ----"+Thread.currentThread().name)
        val url= "http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg"
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
//                .crossFade(1000*5) //crossFade()  强制开启默认的淡入淡出操作;
                .dontAnimate()//dontAnimate()关闭动画
                .into(iv_image)
    }

    //图片大小裁剪(指定图片宽和高)
    fun loadImageCut(){
        Log.d("feifei","loadImageThumbnail ----"+Thread.currentThread().name)
        val url= "http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg"
        Glide.with(this)
                .load(url)
                .override(100,100)
                .placeholder(R.mipmap.ic_launcher)
                .into(iv_image)
    }

    /**
     * 为了更快的加载图片，我们肯定希望可以直接拿到图片，而不是进行网络请求，所以我们需要缓存。
     * Glide 通过使用默认开启 "内存和磁盘缓存",来避免不必要的网络请求
     * skipMemoryCache(true)告诉 Glide 关闭内存缓存，这样Glide 就不会把这张图片放到内存缓存中。
     * diskCacheStrategy( DiskCacheStrategy.NONE ) 修改磁盘缓存策略，禁止磁盘缓存。
     * Glide 不仅缓存了全尺寸的图，还会根据 ImageView 大小所生成的图也会缓存起来。
     * DiskCacheStrategy 的枚举意义：
     *  DiskCacheStrategy.NONE 什么都不缓存
     *  DiskCacheStrategy.SOURCE 只缓存全尺寸图
     *  DiskCacheStrategy.RESULT 只缓存最终的加载图
     *  DiskCacheStrategy.ALL 缓存所有版本图（默认行为）
     */
    fun loadCache(){
        val url= "http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg"
        Glide.with(this)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .into(iv_image);
    }

    /**
     * 同一时间加载多个图片的场景，我们可以通过指定优先级，来优先加载对于用户更重要的图片
     * 优先级可选值:
     * Priority.LOW
     * Priority.NORMAL
     * Priority.HIGH
     * Priority.IMMEDIAT
     */
    fun loadImagePrority(){
        val url= "http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg"
        val url1 = "http://h.hiphotos.baidu.com/lvpics/w=600/sign=5dc3621e19d5ad6eaaf967eab1c939a3/0b55b319ebc4b745cc71eecccdfc1e178b821506.jpg"

        Glide.with(this)
                .load(url)
                .priority(Priority.HIGH)
                .into(iv_image)

        Glide.with(this)
                .load(url1)
                .priority(Priority.LOW)
                .into(iv_image1);

    }

    /**
     * 如果需要获取到 Bitmap 本身,就需要用到 Target，Target 其实就是整个图片的加载的生命周期,通过它在图片加载完成之后获取到 Bitmap。
     * Target 可以简单的理解为回调，本身就是一个 interface，Glide本身也为我们提供了很多 Target
     */

    fun loadImageTarget(){
        val url= "http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg"
        Glide.with(this)
                .load(url)
                .asBitmap()
                .into(mSimpleTarget);
    }

      //SimpleTarget 可以指定加载的图片的尺寸
      val mSimpleTarget = object : SimpleTarget<Bitmap>(200,200) {
          override fun onResourceReady(resource: Bitmap, animation: GlideAnimation<in Bitmap>) {
              iv_image.setImageBitmap(resource)
              Log.d("feifei", "onResourceReady:" + resource.toString())
          }
      }


    /**
     * ransformations 圆角处理、灰阶处理
     * 一些现成的transform 不用重复造轮子
     * https://github.com/wasabeef/glide-transformations
     */

    fun loadImageTransform(){
        val url= "http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg"
        Glide.with(this)
                .load(url)
                .centerCrop()
//                .transform(RoundTransformation(this,180)) //圆角tranform
                .transform(RoundTransformation(this,180),RotateTransformation(this,180f))
                .into(iv_image);
    }


    //自定义加载动画
  fun loadSelfDefineAnimate(){
      val url= "http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg"
      Glide.with(this)
              .load(url)
              .centerCrop()
              .animate(R.anim.zoomin)
              .transform(RoundTransformation(this,180),RotateTransformation(this,180f))
              .into(iv_image)
  }



}

public class RoundTransformation(context:Context) : BitmapTransformation(context) {

    private var radius:Float = 0.0f;
     constructor( context:Context,px:Int):this(context){
             this.radius = px.toFloat()
     }

    override fun getId(): String {
        return this.javaClass.getName() + Math.round(radius);
    }

    override fun transform(pool: BitmapPool?, toTransform: Bitmap?, outWidth: Int, outHeight: Int): Bitmap {
      return roundCrop(pool!!,toTransform!!)!!
    }

    fun roundCrop(pool:BitmapPool,source:Bitmap):Bitmap?{
        if(source == null){
            return null
        }

        var result = pool.get(source.width,source.height,Bitmap.Config.ARGB_8888)
        if(result == null){
            result = Bitmap.createBitmap(source.width,source.height,Bitmap.Config.ARGB_8888)
        }

        var canvas = Canvas(result)

        val paint = Paint()
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        paint.setAntiAlias(true);
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF,radius,radius,paint)
        return result


    }


}


class RotateTransformation(context: Context, rotateRotationAngle: Float) : BitmapTransformation(context) {

    private var rotateRotationAngle = 0f

    init {
        this.rotateRotationAngle = rotateRotationAngle
    }

    protected override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val matrix = Matrix()

        matrix.postRotate(rotateRotationAngle)

        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.width, toTransform.height, matrix, true)
    }

    override fun getId(): String {
        return javaClass.name + Math.round(rotateRotationAngle)
    }
}