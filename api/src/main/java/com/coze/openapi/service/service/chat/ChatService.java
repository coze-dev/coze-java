package com.coze.openapi.service.service.chat;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.coze.openapi.api.ChatAPI;
import com.coze.openapi.api.ChatMessageAPI;
import com.coze.openapi.client.chat.CancelChatReq;
import com.coze.openapi.client.chat.CancelChatResp;
import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.CreateChatResp;
import com.coze.openapi.client.chat.RetrieveChatReq;
import com.coze.openapi.client.chat.RetrieveChatResp;
import com.coze.openapi.client.chat.SubmitToolOutputsReq;
import com.coze.openapi.client.chat.SubmitToolOutputsResp;
import com.coze.openapi.client.chat.message.ListMessageReq;
import com.coze.openapi.client.chat.message.ListMessageResp;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatPoll;
import com.coze.openapi.client.chat.model.ChatStatus;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.service.service.common.ChatStream;
import com.coze.openapi.service.service.common.CozeLoggerFactory;
import com.coze.openapi.service.utils.Utils;

import io.reactivex.Flowable;

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
  public CreateChatResp create(CreateChatReq req) {
    req.disableStream();
    String conversationID = req.getConversationID();
    req.clearBeforeReq();
    BaseResponse<Chat> resp = Utils.execute(chatAPI.chat(conversationID, req, req));
    return CreateChatResp.builder().chat(resp.getData()).logID(resp.getLogID()).build();
  }

  /*
   * Call the Chat API with non-streaming to send messages to a published Coze bot and
   * fetch chat status & message.
   * docs en: https://www.coze.com/docs/developer_guides/chat_v3
   * docs zh: https://www.coze.cn/docs/developer_guides/chat_v3
   * */
  public ChatPoll createAndPoll(CreateChatReq req) throws Exception {
    return _createAndPoll(req, null);
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
  public ChatPoll createAndPoll(CreateChatReq req, Long timeout) throws Exception {
    Objects.requireNonNull(timeout, "timeout is required");
    return _createAndPoll(req, timeout);
  }

  private ChatPoll _createAndPoll(CreateChatReq req, Long timeout) throws Exception {
    req.disableStream();
    String conversationID = req.getConversationID();
    req.clearBeforeReq();
    Chat chat = Utils.execute(chatAPI.chat(conversationID, req, req)).getData();
    // 处理一开始没有传入 ConversationID 的情况
    conversationID = chat.getConversationID();
    String chatID = chat.getID();
    long start = System.currentTimeMillis() / 1000;
    while (ChatStatus.IN_PROGRESS.equals(chat.getStatus())) {
      TimeUnit.SECONDS.sleep(1);
      if (timeout != null && timeout > 0) {
        if ((System.currentTimeMillis() / 1000) - start > timeout) {
          logger.warn("Chat timeout: " + timeout + " seconds, cancel Chat");
          // The chat can be cancelled before its completed.
          cancel(CancelChatReq.of(conversationID, chatID));
          break;
        }
      }

      chat = retrieve(RetrieveChatReq.of(conversationID, chatID)).getChat();
      if (ChatStatus.COMPLETED.equals(chat.getStatus())) {
        logger.info(
            "Chat completed, spend " + (System.currentTimeMillis() / 1000 - start) + " seconds");
        break;
      }
    }
    ListMessageResp resp = message().list(ListMessageReq.of(conversationID, chatID));
    return new ChatPoll(chat, resp.getMessages());
  }

  /*
   * Get the detailed information of the chat.
   * docs en: https://www.coze.com/docs/developer_guides/retrieve_chat
   * docs zh: https://www.coze.cn/docs/developer_guides/retrieve_chat
   * */
  public RetrieveChatResp retrieve(RetrieveChatReq req) {
    BaseResponse<Chat> resp =
        Utils.execute(chatAPI.retrieve(req.getConversationID(), req.getChatID(), req));
    return RetrieveChatResp.builder().chat(resp.getData()).logID(resp.getLogID()).build();
  }

  /*
   * Call this API to cancel an ongoing chat.
   * docs en: https://www.coze.com/docs/developer_guides/chat_cancel
   * docs zh: https://www.coze.cn/docs/developer_guides/chat_cancel
   * */
  public CancelChatResp cancel(CancelChatReq req) {
    BaseResponse<Chat> resp = Utils.execute(chatAPI.cancel(req, req));
    return CancelChatResp.builder().chat(resp.getData()).logID(resp.getLogID()).build();
  }

  /*
   * Call this API to submit the results of tool execution.
   * docs en: https://www.coze.com/docs/developer_guides/chat_submit_tool_outputs
   * docs zh: https://www.coze.cn/docs/developer_guides/chat_submit_tool_outputs
   */
  public SubmitToolOutputsResp submitToolOutputs(SubmitToolOutputsReq req) {
    req.disableStream();
    String conversationID = req.getConversationID();
    String chatID = req.getChatID();
    req.clearBeforeReq();
    BaseResponse<Chat> resp =
        Utils.execute(chatAPI.submitToolOutputs(conversationID, chatID, req, req));
    return SubmitToolOutputsResp.builder().chat(resp.getData()).logID(resp.getLogID()).build();
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
    return ChatStream.stream(chatAPI.streamSubmitToolOutputs(conversationID, chatID, req, req));
  }

  /*
   * Call the Chat API with streaming to send messages to a published Coze bot.
   * docs en: https://www.coze.com/docs/developer_guides/chat_v3
   * docs zh: https://www.coze.cn/docs/developer_guides/chat_v3
   * */
  public Flowable<ChatEvent> stream(CreateChatReq req) {
    req.enableStream();
    String conversationID = req.getConversationID();
    req.clearBeforeReq();
    return ChatStream.stream(chatAPI.stream(conversationID, req, req));
  }
}
