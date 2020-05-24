package net.dummyvariables.games.schach.server.game

import net.dummyvariables.games.schach.model.message.LegalMovesMessage
import net.dummyvariables.games.schach.model.message.PieceMessage
import net.dummyvariables.games.schach.service.GameService
import net.dummyvariables.games.schach.service.MessageService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class WebSocketController(
        private val simpMessagingTemplate: SimpMessagingTemplate,
        private val messageService: MessageService,
        private val gameService: GameService
) {

    @MessageMapping("/{connectionId}/movement")
    fun receiveMovement(@DestinationVariable connectionId: String, @Payload msg: String) {
        val pieceString = genPieceMessage(connectionId)
        val legalString = genLegalMovesMessage(connectionId)
        simpMessagingTemplate.convertAndSend("/down/$connectionId/pieces", pieceString)
        simpMessagingTemplate.convertAndSend("/down/$connectionId/legalMoves", legalString)
    }

    fun genPieceMessage(connectionId: String): String {
        println(connectionId)
        Thread.sleep(500)
        val pieces = gameService.getBoard(connectionId).pieces
        return messageService.asJson(PieceMessage(pieces))
    }

    fun genLegalMovesMessage(connectionId: String): String {
        println(connectionId)
        Thread.sleep(1000)
        val legalMoves = gameService.getBoard(connectionId).getLegalMoves()
        println(legalMoves)
        return messageService.asJson(LegalMovesMessage(legalMoves))
    }

}