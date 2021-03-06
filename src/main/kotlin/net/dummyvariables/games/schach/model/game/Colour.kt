package net.dummyvariables.games.schach.model.game

enum class Colour {
    white, black;
    companion object {
        fun stringToEnum(s: String): Colour {
            return Colour.values().first {
                it.toString() == s
            }
        }

        fun getOtherColour(colour: Colour): Colour = if (colour == white) black else white
    }

}
