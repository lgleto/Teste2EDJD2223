package ipca.spaceinvaders.teste2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {

    var playing = false
    lateinit var gameThread : Thread
    var canvas : Canvas? = null
    lateinit var paint: Paint

    lateinit var paintCol: Paint
    var screenWidth : Int = 0
    var screenHeight : Int = 0

    var circleSprites = arrayListOf<CircleSprite>()

    constructor(context: Context?,  screenWidth : Int, screenHeight:Int) : super(context) {
        init( context, screenWidth, screenHeight )
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun init (context : Context?, screenWidth : Int, screenHeight:Int ) {
        paint = Paint()
        paintCol = Paint()
        paintCol.style = Paint.Style.STROKE
        paintCol.strokeWidth = 4.0f
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight

    }

    override fun run() {
        while (playing){
            update()
            draw()
            control()
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread.start()
    }

    fun pause() {
        playing = false
        gameThread.join()
    }

    fun update() {

        var circlesToRemove = arrayListOf<CircleSprite>()

        for (c in circleSprites){
            c.update()
            for ( c1 in circleSprites ){
                if (c != c1 &&
                    c1.detectCollision.intersect(c.detectCollision))
                {
                    if (!circlesToRemove.contains(c1))
                        circlesToRemove.add(c1)
                    if (!circlesToRemove.contains(c))
                        circlesToRemove.add(c)
                }
            }
        }


        circleSprites.removeAll(circlesToRemove)
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas?.drawColor(Color.BLACK)

            //paint.style = Paint.Style.FILL
            paint.color = Color.GREEN
            for (c in circleSprites){
                canvas?.drawCircle(c.x.toFloat(), c.y.toFloat(),c.radius,paint)


            }

            paintCol.color = Color.RED
            for (c in circleSprites){
                canvas?.drawRect(c.detectCollision,paintCol)
            }


            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun control() {
        Thread.sleep(17)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when(it.action.and(MotionEvent.ACTION_MASK)){
                MotionEvent.ACTION_UP ->{

                }
                MotionEvent.ACTION_DOWN ->{
                    val circleSprite = CircleSprite(screenWidth, screenHeight,  event.x.toInt(), event.y.toInt())
                    circleSprites.add(circleSprite)
                }
                else ->{

                }
            }
        }
        return true
    }

}