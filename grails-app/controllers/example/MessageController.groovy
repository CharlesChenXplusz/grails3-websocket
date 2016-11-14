package example

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SubscribeMapping

import javax.servlet.http.HttpServletResponse

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
    protected String replyToToTopic(String content) {
        return "Someone says : [${content}]!"
    }

    @SubscribeMapping("/reply")
    protected String replyToYourSelf(String content) {
        return "You says : [$content]!"
    }

    def broadcast(String content) {
        brokerMessagingTemplate.convertAndSend('/topic/hello', "Broadcast : [$content]".toString())
        render(status: HttpServletResponse.SC_OK)
    }

}
