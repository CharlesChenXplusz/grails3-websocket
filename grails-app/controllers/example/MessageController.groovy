package example

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SubscribeMapping

import javax.servlet.http.HttpServletResponse
import java.security.Principal

/**
 * Created by charles.chen on 5/9/16.
 */
class MessageController extends BaseController {

    SimpMessagingTemplate brokerMessagingTemplate

    def index() {
        render(view: 'message')
    }

    @MessageMapping("/hello")
    @SendTo("/topic/hello")
    protected String replyToToTopic(String content, Principal principal) {
        return "${principal.principal.username} says : [${content}]!"
    }

    @SubscribeMapping("/reply")
    protected String replyToYourSelf() {
        return "Welcome you!"
    }

    def broadcast(String content) {
        brokerMessagingTemplate.convertAndSend('/topic/hello', "Broadcast : [$content]".toString())
        render(status: HttpServletResponse.SC_OK)
    }

    def sendToUser(String user, String content) {
        brokerMessagingTemplate.convertAndSendToUser(user, '/queue/replyToYourself', content)
        render(status: HttpServletResponse.SC_OK)
    }

    @MessageMapping("/sendToUserPrivate-{username}")
    protected void userPrivate(@Payload String message,
                               @DestinationVariable('username') String username, Principal principal) {
        brokerMessagingTemplate.convertAndSendToUser(username, '/queue/exchangePrivate', message)
    }
}
