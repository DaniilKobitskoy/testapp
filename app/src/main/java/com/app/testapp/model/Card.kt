package com.app.testapp.model

class Card(var state: State, val type: Type) {

    enum class State {
        EMPTY, DEFAULT, FLIPPED
    }

    enum class Type {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
    }
}