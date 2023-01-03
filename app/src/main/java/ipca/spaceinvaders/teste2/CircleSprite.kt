package ipca.spaceinvaders.teste2

import android.graphics.Rect
import android.graphics.RectF
import java.util.*

class CircleSprite {

    var x = 0
    var y = 0

    var vx = 0
    var vy = 0

    var detectCollision = RectF()

    var maxY : Float
    var minY : Float = 0f
    var maxX : Float
    var minX : Float = 0f

    var radius = 40.0f

    constructor( screenWidth : Int, screenHeight : Int, x : Int, y :Int) {
        this.x = x
        this.y = y

        maxY = screenHeight.toFloat()
        maxX = screenWidth.toFloat()

        var generator = Random()
        vx = generator.nextInt(10) - 20
        vy = generator.nextInt(10) - 20

        RectF()
        detectCollision = RectF(
            x.toFloat() - radius,
            y.toFloat() - radius,
            x.toFloat() + radius,
            y.toFloat() + radius)
    }


    fun update(){
        x += vx
        y += vy

        if (x > (maxX - radius) ) {
            vx *= -1
        }

        if (x < (minX + radius) ) {
            vx *= -1
        }

        if (y > (maxY - radius) ) {
            vy *= -1
        }

        if (y < (minY + radius) ) {
            vy *= -1
        }

        detectCollision.left = x.toFloat() - radius
        detectCollision.top = y.toFloat() - radius
        detectCollision.right = x.toFloat() + radius
        detectCollision.bottom = y.toFloat() + radius

    }


}