package com.app.testapp.model


class Game {

    enum class Turn {
        PLAYER_ONE, PLAYER_TWO
    }

    var turn: Turn = Turn.PLAYER_ONE
    var score1 = 0
    var score2 = 0
    var firstCardId = 0
    var cards: ArrayList<Card>

    init {
        cards = ArrayList()
        cards = fillCards(cards)

        cards.shuffle()
    }

    private fun fillCards(cards: ArrayList<Card>): ArrayList<Card> {
        cards.apply {
            add(Card(Card.State.DEFAULT, Card.Type.ONE))
            add(Card(Card.State.DEFAULT, Card.Type.ONE))
            add(Card(Card.State.DEFAULT, Card.Type.TWO))
            add(Card(Card.State.DEFAULT, Card.Type.TWO))
            add(Card(Card.State.DEFAULT, Card.Type.THREE))
            add(Card(Card.State.DEFAULT, Card.Type.THREE))
            add(Card(Card.State.DEFAULT, Card.Type.FOUR))
            add(Card(Card.State.DEFAULT, Card.Type.FOUR))
            add(Card(Card.State.DEFAULT, Card.Type.FIVE))
            add(Card(Card.State.DEFAULT, Card.Type.FIVE))
            add(Card(Card.State.DEFAULT, Card.Type.SIX))
            add(Card(Card.State.DEFAULT, Card.Type.SIX))
            add(Card(Card.State.DEFAULT, Card.Type.SEVEN))
            add(Card(Card.State.DEFAULT, Card.Type.SEVEN))
            add(Card(Card.State.DEFAULT, Card.Type.EIGHT))
            add(Card(Card.State.DEFAULT, Card.Type.EIGHT))
        }
        return cards
    }

    fun getCard(i: Int): Card {
        return cards[i]
    }

    fun setCardState(id: Int, nState: Card.State) {
        cards[id].state = nState
    }

    val isOver: Boolean
        get() {
            for (i in cards.indices) {
                if (cards[i].state != Card.State.EMPTY) return false
            }
            return true
        }
}