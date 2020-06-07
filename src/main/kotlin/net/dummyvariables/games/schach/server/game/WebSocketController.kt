package net.dummyvariables.games.schach.server.game

import net.dummyvariables.games.schach.model.game.Board
import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.message.legalMoves.LegalMovesMessageBuilder
import net.dummyvariables.games.schach.model.message.pieces.PieceMessageBuilder
import net.dummyvariables.games.schach.service.GameService
import net.dummyvariables.games.schach.service.MessageService
import net.dummyvariables.games.schach.service.MovementService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class WebSocketController(
        private val simpMessagingTemplate: SimpMessagingTemplate,
        private val messageService: MessageService,
        private val gameService: GameService,
        private val movementService: MovementService
) {

    private val pieceMessageBuilder = PieceMessageBuilder()
    private val legalMovesMessageBuilder = LegalMovesMessageBuilder()

    @MessageMapping("/{connectionId}/init")
    fun initConnection(@DestinationVariable connectionId: String, @Payload msg: String) {
        val game = gameService.getGameFromConnectionId(connectionId)
        game.registerPlayerReady(connectionId)
        if (game.areBothPlayersReady()) {
            val whiteConnectionId = gameService.getWhiteConnectionId(connectionId)
            val blackConnectionId = gameService.getBlackConnectionId(connectionId)
            val pieceString = genPieceMessage(whiteConnectionId)
            val legalString = genLegalMovesMessage(whiteConnectionId)
            simpMessagingTemplate.convertAndSend("/down/$whiteConnectionId/pieces", pieceString)
            simpMessagingTemplate.convertAndSend("/down/$blackConnectionId/pieces", pieceString)
            simpMessagingTemplate.convertAndSend("/down/$whiteConnectionId/legalMoves", legalString)
        }
    }

    @MessageMapping("/{connectionId}/movement")
    fun receiveMovement(@DestinationVariable connectionId: String, @Payload msg: String) {
        val board = gameService.getBoard(connectionId)
        val colourJustMoved = Colour.stringToEnum(gameService.getColourFromConnectionId(connectionId))
        if (colourJustMoved == gameService.waitingOnColour(board)) {
            move(msg, board)
            val (pieceString, legalString, altConnectionId) = generateMessageInfo(connectionId, board)
            notifyClients(connectionId, pieceString, altConnectionId, legalString)
        }
    }

    private fun move(msg: String, board: Board) {
        val moveDto = messageService.moveDtoFromJson(msg)
        movementService.move(moveDto, board)
    }

    private fun notifyClients(connectionId: String, pieceString: String, altConnectionId: String, legalString: String) {
        simpMessagingTemplate.convertAndSend("/down/$connectionId/pieces", pieceString)
        simpMessagingTemplate.convertAndSend("/down/$altConnectionId/pieces", pieceString)
        simpMessagingTemplate.convertAndSend("/down/$altConnectionId/legalMoves", legalString)
    }

    private fun generateMessageInfo(connectionId: String, board: Board): Triple<String, String, String> {
        val pieceString = genPieceMessage(connectionId)
        val legalString = genLegalMovesMessage(connectionId)
        val gameId = gameService.getGameIdFromConnectionId(connectionId)
        val nextColour = gameService.setNextColourTurn(board)
        val altConnectionId = gameService.getConnectionIdFromGameId(gameId, nextColour)
        return Triple(pieceString, legalString, altConnectionId)
    }

    fun genPieceMessage(connectionId: String): String {
        val pieces = gameService.getBoard(connectionId).pieces
        val pieceMessage = pieceMessageBuilder.fromPieceList(pieces)
        println(connectionId)
        println(pieceMessage)
        return messageService.asJson(pieceMessage)
    }

    fun genLegalMovesMessage(connectionId: String): String {
        val legalMoves = gameService.getBoard(connectionId).getLegalMoves()
        val legalMovesMessage = legalMovesMessageBuilder.fromLegalMoves(legalMoves)
        println(connectionId)
        println(legalMoves)
        return messageService.asJson(legalMovesMessage)
    }

}