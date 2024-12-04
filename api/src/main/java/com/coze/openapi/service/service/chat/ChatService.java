package com.coze.openapi.service.service.chat;

import com.coze.openapi.api.ChatAPI;
import com.coze.openapi.api.ChatMessageAPI;
import com.coze.openapi.client.chat.CancelChatReq;
import com.coze.openapi.client.chat.ChatReq;
import com.coze.openapi.client.chat.RetrieveChatReq;
import com.coze.openapi.client.chat.SubmitToolOutputsReq;
import com.coze.openapi.client.chat.message.ListMessageReq;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatPoll;
import com.coze.openapi.client.chat.model.ChatStatus;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.service.common.CozeLoggerFactory;
import com.coze.openapi.service.utils.Utils;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

public class ChatService {

    private final ChatAPI chatAPI;
    private final ChatMessageService chatMessageAPI;
    private static final Logger logger = CozeLoggerFactory.getLogger();

    public ChatService(ChatAPI chatAPI, ChatMessageAPI chatMessageAPI) {
        this.chatAPI = chatAPI;
        this.chatMessageAPI = new ChatMessageService(chatMessageAPI);
    }

    public ChatMessageService message() {
        return this.chatMessageAPI;
    }

    /*
    * Call the Chat API with non-streaming to send messages to a published Coze bot.
    * docs en: https://www.coze.com/docs/developer_guides/chat_v3
    * docs zh: https://www.coze.cn/docs/developer_guides/chat_v3
    * */
    public Chat create(ChatReq req) {
        req.disableStream();
        String conversationID = req.getConversationID();
        req.clearBeforeReq();
        return Utils.execute(chatAPI.chat(conversationID, req)).getData();
    }
    /*
     * Call the Chat API with non-streaming to send messages to a published Coze bot and
     * fetch chat status & message.
     * docs en: https://www.coze.com/docs/developer_guides/chat_v3
     * docs zh: https://www.coze.cn/docs/developer_guides/chat_v3
     * */
    public ChatPoll createAndPoll(ChatReq req) throws Exception{
        return _createAndPoll(req, null, false);
    }

    /*
     * Call the Chat API with non-streaming to send messages to a published Coze bot and
     * fetch chat status & message.
     * docs en: https://www.coze.com/docs/developer_guides/chat_v3
     * docs zh: https://www.coze.cn/docs/developer_guides/chat_v3
     *
     * timeout: The maximum time to wait for the chat to complete. The chat will be cancelled after the progress of it
     * exceed timeout. The unit is second.
     * */
    public ChatPoll createAndPoll(ChatReq req, Long timeout) throws Exception{
        Objects.requireNonNull(timeout, "timeout is required");
        return _createAndPoll(req, timeout, true);
    }

    private ChatPoll _createAndPoll(ChatReq req, Long timeout, boolean needTimeout) throws Exception{
        req.disableStream();
        String conversationID = req.getConversationID();
        req.clearBeforeReq();
        Chat chat = Utils.execute(chatAPI.chat(conversationID, req)).getData();
        String chatID = chat.getID();
        long start = System.currentTimeMillis() / 1000;
        while (ChatStatus.IN_PROGRESS.equals(chat.getStatus())) {
            TimeUnit.SECONDS.sleep(1);
            if (needTimeout) {
                if ((System.currentTimeMillis() / 1000) - start > timeout) {
                    logger.warn("Chat timeout: " + timeout + " seconds, cancel Chat");
                    // The chat can be cancelled before its completed.
                    cancel(CancelChatReq.of(conversationID, chatID));
                    break;
                }
            }

            chat = retrieve(RetrieveChatReq.of(conversationID, chatID));
            if (ChatStatus.COMPLETED.equals(chat.getStatus())) {
                logger.info("Chat completed, spend " + (System.currentTimeMillis() / 1000 - start) + " seconds");
                break;
            }
        }
        List<Message> messages = message().list(ListMessageReq.of(conversationID, chatID));
        return new ChatPoll(chat, messages);
    }

    /*
    * Get the detailed information of the chat.
    * docs en: https://www.coze.com/docs/developer_guides/retrieve_chat
    * docs zh: https://www.coze.cn/docs/developer_guides/retrieve_chat
    * */
    public Chat retrieve(RetrieveChatReq req) {
        return Utils.execute(chatAPI.retrieve(req.getConversationID(), req.getChatID())).getData();
    }

    /*
    * Call this API to cancel an ongoing chat.
    * docs en: https://www.coze.com/docs/developer_guides/chat_cancel
    * docs zh: https://www.coze.cn/docs/developer_guides/chat_cancel
    * */
    public Chat cancel(CancelChatReq req) {
        return Utils.execute(chatAPI.cancel(req)).getData();
    }

    /*
     * Call this API to submit the results of tool execution.
     * docs en: https://www.coze.com/docs/developer_guides/chat_submit_tool_outputs
     * docs zh: https://www.coze.cn/docs/developer_guides/chat_submit_tool_outputs
     */
    public Chat submitToolOutputs(SubmitToolOutputsReq req) {
        req.disableStream();
        String conversationID = req.getConversationID();
        String chatID = req.getChatID();
        req.clearBeforeReq();
        return Utils.execute(chatAPI.submitToolOutputs(conversationID, chatID, req)).getData();
    }

    /*
     * Call this API to submit the results of tool execution. This API will return streaming response
     * docs en: https://www.coze.com/docs/developer_guides/chat_submit_tool_outputs
     * docs zh: https://www.coze.cn/docs/developer_guides/chat_submit_tool_outputs
     */
    public Flowable<ChatEvent> streamSubmitToolOutputs(SubmitToolOutputsReq req) {
        req.enableStream();
        String conversationID = req.getConversationID();
        String chatID = req.getChatID();
        req.clearBeforeReq();
        return stream(chatAPI.streamSubmitToolOutputs(conversationID, chatID, req));
    }

    /*
    * Call the Chat API with streaming to send messages to a published Coze bot.
    * docs en: https://www.coze.com/docs/developer_guides/chat_v3
    * docs zh: https://www.coze.cn/docs/developer_guides/chat_v3
    * */
    public Flowable<ChatEvent> stream(ChatReq req) {
        req.enableStream();
        String conversationID = req.getConversationID();
        req.clearBeforeReq();
        return stream(chatAPI.stream(conversationID, req));
    }
    

    public static Flowable<ChatEvent> stream(Call<ResponseBody> apiCall) {
        return Flowable.create(emitter -> apiCall.enqueue(new EventCallback(emitter)), BackpressureStrategy.BUFFER);
    }
}
