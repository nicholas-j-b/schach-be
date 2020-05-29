package net.dummyvariables.games.schach.server.game

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
        val pieceString = genPieceMessage(connectionId)
        val legalString = genLegalMovesMessage(connectionId)
        simpMessagingTemplate.convertAndSend("/down/$connectionId/pieces", pieceString)
        simpMessagingTemplate.convertAndSend("/down/$connectionId/legalMoves", legalString)
    }

    @MessageMapping("/{connectionId}/movement")
    fun receiveMovement(@DestinationVariable connectionId: String, @Payload msg: String) {
        val board = gameService.getBoard(connectionId)
        val moveDto = messageService.moveDtoFromJson(msg)
        movementService.move(moveDto, board)
        val pieceString = genPieceMessage(connectionId)
        val legalString = genLegalMovesMessage(connectionId)
        simpMessagingTemplate.convertAndSend("/down/$connectionId/pieces", pieceString)
        simpMessagingTemplate.convertAndSend("/down/$connectionId/legalMoves", legalString)
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