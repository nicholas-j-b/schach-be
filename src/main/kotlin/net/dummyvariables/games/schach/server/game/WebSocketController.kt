package net.dummyvariables.games.schach.server.game

import net.dummyvariables.games.schach.model.message.GenericMessage
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

    @MessageMapping("/{connectionId}/init")
    fun initConnection(@DestinationVariable connectionId: String, @Payload msg: String) {
        gameService.initialisePlayer(connectionId)
        notifyPlayers(connectionId)
    }

    @MessageMapping("/{connectionId}/movement")
    fun receiveMovement(@DestinationVariable connectionId: String, @Payload msg: String) {
        if (gameService.isMoveFromExpectedPlayer(connectionId)) {
            val game = gameService.getGameFromConnectionId(connectionId)
            val moveDto = messageService.moveDtoFromJson(msg)
            movementService.move(moveDto, game)
            notifyPlayers(connectionId)
        }
    }

    private fun notifyPlayers(connectionId: String) {
        val messages = gameService.getPlayerMessages(connectionId)
        messages.forEach { message ->
            sendGenericMessage(message)
        }
    }

    fun sendGenericMessage(genericMessage: GenericMessage) {
        simpMessagingTemplate.convertAndSend("/down/${genericMessage.connectionId}/${genericMessage.channel}", genericMessage.message)
    }


}