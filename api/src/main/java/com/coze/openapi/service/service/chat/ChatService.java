package com.coze.openapi.service.service.chat;

import com.coze.openapi.api.ChatAPI;
import com.coze.openapi.api.ChatMessageAPI;
import com.coze.openapi.client.chat.CancelChatReq;
import com.coze.openapi.client.chat.ChatReq;
import com.coze.openapi.client.chat.RetrieveChatReq;
import com.coze.openapi.client.chat.SubmitToolOutputsReq;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.service.utils.Utils;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ChatService {

    private final ChatAPI chatAPI;
    private final ChatMessageService chatMessageAPI;

    public ChatService(ChatAPI chatAPI, ChatMessageAPI chatMessageAPI) {
        this.chatAPI = chatAPI;
        this.chatMessageAPI = new ChatMessageService(chatMessageAPI);
    }

    public ChatMessageService message() {
        return this.chatMessageAPI;
    }

    public Chat create(ChatReq req) {
        req.disableStream();
        String conversationID = req.getConversationID();
        req.clearBeforeReq();
        return Utils.execute(chatAPI.chat(conversationID, req)).getData();
    }

    public Chat retrieve(RetrieveChatReq req) {
        return Utils.execute(chatAPI.retrieveChat(req.getConversationID(), req.getChatID())).getData();
    }

    public Chat cancel(CancelChatReq req) {
        return Utils.execute(chatAPI.cancelChat(req)).getData();
    }

    public Chat submitToolOutputs(SubmitToolOutputsReq req) {
        req.disableStream();
        String conversationID = req.getConversationID();
        String chatID = req.getChatID();
        req.clearBeforeReq();
        return Utils.execute(chatAPI.submitToolOutputs(conversationID, chatID, req)).getData();
    }

    public Flowable<ChatEvent> streamSubmitToolOutputs(SubmitToolOutputsReq req) {
        req.enableStream();
        String conversationID = req.getConversationID();
        String chatID = req.getChatID();
        req.clearBeforeReq();
        return stream(chatAPI.streamSubmitToolOutputs(conversationID, chatID, req));
    }

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
