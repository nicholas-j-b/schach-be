package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.*
import net.dummyvariables.games.schach.model.message.GenericMessage
import net.dummyvariables.games.schach.model.message.InitialMessage
import net.dummyvariables.games.schach.model.message.game.GameInfoDto
import net.dummyvariables.games.schach.model.message.legalMoves.LegalMovesMessageBuilder
import net.dummyvariables.games.schach.model.message.pieces.PieceMessageBuilder
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class GameService(
        private val messageService: MessageService
) {
    private val pieceMessageBuilder = PieceMessageBuilder()
    private val legalMovesMessageBuilder = LegalMovesMessageBuilder()

    fun getAllGamesAsDtos(): List<GameInfoDto> {
        return GameCollection.games.map {
            it.value.toDto(it.key)
        }
    }

    fun createGameAndAllocatePlayerToIt(gameId: String): InitialMessage {
        val game = GameCollection.addGame(gameId)
        val colour = if (Random.nextBoolean()) Colour.black else Colour.white
//        val colour = Colour.white
        return allocatePlayerToGame(gameId, game, colour)
    }

    fun allocateToExistingGame(gameId: String): InitialMessage {
        val game = GameCollection.games[gameId] ?: throw error("no game found")
        val colour = game.getNonAssignedColour()
        return allocatePlayerToGame(gameId, game, colour)
    }

    private fun allocatePlayerToGame(gameId: String, game: Game, colour: Colour): InitialMessage {
        val connectionId = getConnectionIdFromGameId(gameId, colour)
        game.addPlayer(connectionId, colour)
        return InitialMessage(gameId, colour, connectionId)
    }

    fun registerPlayer(game: Game, connectionId: String) {
        game.registerPlayerReady(connectionId)
    }

    fun initialisePlayer(connectionId: String) {
        val game = getGameFromConnectionId(connectionId)
        registerPlayer(game, connectionId)
        if (game.areBothPlayersReady()) {
            val whitePlayer = game.players.first {
                it.colour == Colour.white
            }
            whitePlayer.isTurn = true
        }
    }

    fun isMoveFromExpectedPlayer(connectionId: String): Boolean {
        val colourJustMoved = Colour.stringToEnum(getColourFromConnectionId(connectionId))
        val game = getGameFromConnectionId(connectionId)
        return game.getActivePlayer().colour == colourJustMoved
    }

    fun getPlayerMessages(connectionId: String): List<GenericMessage> {
        val messages = mutableListOf<GenericMessage>()
        val game = getGameFromConnectionId(connectionId)
        game.players.forEach { player ->
            val pieceString = genPieceMessage(player.connectionId)
            messages.add(GenericMessage(player.connectionId, "pieces", pieceString))
            val legalMoveString = if (player.isTurn) {
                genLegalMovesMessage(player)
            } else {
                genEmptyLegalMovesMessage()
            }
            messages.add(GenericMessage(player.connectionId, "legalMoves", legalMoveString))
        }
        return messages
    }

    fun genPieceMessage(connectionId: String): String {
        val pieces = getEntityManagementService(connectionId).pieces
        val pieceMessage = pieceMessageBuilder.fromPieceList(pieces)
        return messageService.asJson(pieceMessage)
    }


    fun genLegalMovesMessage(player: Player): String {
        val legalMoves = getEntityManagementService(player.connectionId).getLegalMoves(player.colour)
        val legalMovesMessage = legalMovesMessageBuilder.fromLegalMoves(legalMoves)
        return messageService.asJson(legalMovesMessage)
    }

    fun genEmptyLegalMovesMessage(): String {
        val legalMovesMessage = legalMovesMessageBuilder.fromLegalMoves(emptyList())
        return messageService.asJson(legalMovesMessage)
    }

    fun getBoard(connectionId: String): StandardBoard {
        val gameId = getGameIdFromConnectionId(connectionId)
        return GameCollection.games[gameId]?.board ?: throw error("no game found")
    }

    fun getEntityManagementService(connectionId: String): EntityManagementService {
        val gameId = getGameIdFromConnectionId(connectionId)
        return GameCollection.games[gameId]?.entityManagementService ?: throw error("no game found")
    }

    fun waitingOnColour(board: StandardBoard): Colour {
        return board.currentColour
    }

    fun setNextColourTurn(board: StandardBoard): Colour {
        return board.newTurn()
    }

    fun getWhiteConnectionId(connectionId: String) = getOppositeConnectionId(Colour.black, connectionId)

    fun getBlackConnectionId(connectionId: String) = getOppositeConnectionId(Colour.white, connectionId)

    fun getOppositeConnectionId(colour: Colour, connectionId: String): String {
        val gameId = getGameIdFromConnectionId(connectionId)
        val otherColour = Colour.getOtherColour(colour)
        return getConnectionIdFromGameId(gameId, otherColour)
    }

    fun getGameFromConnectionId(connectionId: String) =
            GameCollection.games[getGameIdFromConnectionId(connectionId)] ?: throw error("no game found")

    fun getColourFromConnectionId(connectionId: String) = connectionId.split("-")[1]

    fun getGameIdFromConnectionId(connectionId: String) = connectionId.split("-").first()

    fun getConnectionIdFromGameId(gameId: String, colour: Colour) = "$gameId-$colour"
}