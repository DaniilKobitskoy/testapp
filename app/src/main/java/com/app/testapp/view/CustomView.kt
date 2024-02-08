package com.app.testapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.app.testapp.model.Game
import com.app.testapp.R
import com.app.testapp.model.Card

class CustomView : View {

    private val limits = FloatArray(5)
    private var allowed = false
    private var won = false
    private var touched = 0
    private var downCnt = 0

    constructor(c: Context?) : super(c) {
        init()
    }

    constructor(c: Context?, `as`: AttributeSet?) : super(c, `as`) {
        init()
    }

    constructor(c: Context?, `as`: AttributeSet?, defaultStyle: Int) : super(c, `as`, defaultStyle) {
        init()
    }

    private fun init() {
        won = false
        allowed = true
        touched = 0
        Log.d("DEBUG", "Initializes the game")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size: Int
        val width = measuredWidth
        val height = measuredHeight

        size = if (width > height) {
            height
        } else {
            width
        }
        setMeasuredDimension(size, size)

        for (i in 0..4) {
            limits[i] = (i * size / 4).toFloat()
        }
    }

    private fun getCardDrawable(card: Card): Drawable? {
        return when (card.state) {
            Card.State.EMPTY -> ColorDrawable(Color.TRANSPARENT)
            Card.State.DEFAULT -> {
                val d = resources.getDrawable(R.drawable.def)
                d.setBounds(0, 0, (limits[4] / 7).toInt(), (limits[4] / 5.5f).toInt())
                d
            }
            Card.State.FLIPPED -> {
                val d = when (card.type) {
                    Card.Type.ONE -> resources.getDrawable(R.drawable.one)
                    Card.Type.TWO -> resources.getDrawable(R.drawable.two)
                    Card.Type.THREE -> resources.getDrawable(R.drawable.three)
                    Card.Type.FOUR -> resources.getDrawable(R.drawable.four)
                    Card.Type.FIVE -> resources.getDrawable(R.drawable.five)
                    Card.Type.SIX -> resources.getDrawable(R.drawable.six)
                    Card.Type.SEVEN -> resources.getDrawable(R.drawable.seven)
                    Card.Type.EIGHT -> resources.getDrawable(R.drawable.eight)
                }
                d.setBounds(0, 0, 150, 200)
                d
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var cnt = 0
        for (y in 0..3) {
            for (x in 0..3) {
                canvas.save()
                canvas.translate(limits[x] + limits[4] / 21.6f, limits[y] + limits[4] / 36f)
                getCardDrawable(Elements.game.getCard(cnt)!!)?.draw(canvas)
                cnt++
                canvas.restore()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!allowed) return true
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                val yPos = event.y
                val xPos = event.x
                var x = 0
                var y = 0
                while (xPos > limits[x]) x++
                while (yPos > limits[y]) y++
                downCnt = (y - 1) * 4 + (x - 1)
                return true
            }
            MotionEvent.ACTION_UP -> {
                val yPos = event.y
                val xPos = event.x
                var x = 0
                var y = 0
                while (xPos > limits[x]) x++
                while (yPos > limits[y]) y++
                val cnt = (y - 1) * 4 + (x - 1)
                if (cnt != downCnt)
                    return true

                if (Elements.game.getCard(cnt)?.state == Card.State.DEFAULT) {
                    touched++
                    Elements.game.setCardState(cnt, Card.State.FLIPPED)
                }
                if (touched == 1) {
                    Elements.game.firstCardId = cnt
                } else if (touched == 2) {
                    allowed = false
                    touched = 0
                    if (Elements.game.turn == Game.Turn.PLAYER_ONE) {
                        Elements.game.turn = Game.Turn.PLAYER_TWO
                        Elements.infoText!!.setText(resources.getString(R.string.turnInfo, 2))
                        if (Elements.game.getCard(Elements.game.firstCardId)?.type == Elements.game.getCard(cnt)?.type) {
                            won = true
                            Elements.game.score1++
                            Elements.player1!!.setText(resources.getString(R.string.player1, Elements.game.score1))
                        }
                    } else {
                        Elements.game.turn = Game.Turn.PLAYER_ONE
                        Elements.infoText!!.setText(resources.getString(R.string.turnInfo, 1))
                        if (Elements.game.getCard(Elements.game.firstCardId)?.type == Elements.game.getCard(cnt)?.type) {
                            won = true
                            Elements.game.score2++
                            Elements.player2!!.setText(resources.getString(R.string.player2, Elements.game.score2))
                        }
                    }

                    val handler = Handler()
                    handler.postDelayed({
                        if (won) {
                            Elements.game.setCardState(Elements.game.firstCardId, Card.State.EMPTY)
                            Elements.game.setCardState(cnt, Card.State.EMPTY)
                        } else {
                            Elements.game.setCardState(Elements.game.firstCardId, Card.State.DEFAULT)
                            Elements.game.setCardState(cnt, Card.State.DEFAULT)
                        }
                        invalidate()
                        won = false
                        allowed = true
                        if (Elements.game.isOver) {
                            Elements.infoText!!.setText(
                                when {
                                    Elements.game.score1 > Elements.game.score2 -> resources.getString(
                                        R.string.winInfo,
                                        1
                                    )
                                    Elements.game.score2 > Elements.game.score1 -> resources.getString(
                                        R.string.winInfo,
                                        2
                                    )
                                    else -> resources.getString(R.string.drawInfo)
                                }
                            )
                        }
                    }, 800)
                }
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}