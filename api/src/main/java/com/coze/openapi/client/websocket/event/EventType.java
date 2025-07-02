package com.coze.openapi.client.websocket.event;

public class EventType {
  // common
  public static final String CLIENT_ERROR = "client_error"; // sdk error
  public static final String CLOSED = "closed"; // connection closed

  // error
  public static final String ERROR = "error"; // received error event

  // v1/audio/speech
  // req
  public static final String INPUT_TEXT_BUFFER_APPEND =
      "input_text_buffer.append"; // send text to server
  public static final String INPUT_TEXT_BUFFER_COMPLETE =
      "input_text_buffer.complete"; // no text to send, after audio all received, can close
  // connection
  public static final String SPEECH_UPDATE = "speech.update"; // send speech config to server

  // resp
  // v1/audio/speech
  public static final String SPEECH_UPDATED = "speech.updated"; // send speech config to server
  public static final String SPEECH_CREATED = "speech.created"; // after speech created
  public static final String INPUT_TEXT_BUFFER_COMPLETED =
      "input_text_buffer.completed"; // received `input_text_buffer.complete` event
  public static final String SPEECH_AUDIO_UPDATE =
      "speech.audio.update"; // received `speech.update` event
  public static final String SPEECH_AUDIO_COMPLETED =
      "speech.audio.completed"; // all audio received, can close connection

  // v1/audio/transcriptions
  // req
  public static final String INPUT_AUDIO_BUFFER_APPEND =
      "input_audio_buffer.append"; // send audio to server
  public static final String INPUT_AUDIO_BUFFER_COMPLETE =
      "input_audio_buffer.complete"; // no audio to send, after text all received, can close
  // connection
  public static final String TRANSCRIPTIONS_UPDATE =
      "transcriptions.update"; // send transcriptions config to server

  // resp
  public static final String TRANSCRIPTIONS_CREATED =
      "transcriptions.created"; // after transcriptions created
  public static final String TRANSCRIPTIONS_UPDATED =
      "transcriptions.updated"; // after transcriptions config updated
  public static final String INPUT_AUDIO_BUFFER_COMPLETED =
      "input_audio_buffer.completed"; // received `input_audio_buffer.complete` event
  public static final String TRANSCRIPTIONS_MESSAGE_UPDATE =
      "transcriptions.message.update"; // received `transcriptions.update` event
  public static final String TRANSCRIPTIONS_MESSAGE_COMPLETED =
      "transcriptions.message.completed"; // all audio received, can close connection

  // v1/chat
  // req
  public static final String CHAT_UPDATE = "chat.update"; // send chat config to server
  public static final String CONVERSATION_CHAT_SUBMIT_TOOL_OUTPUTS =
      "conversation.chat.submit_tool_outputs"; // send tool outputs to server
  public static final String INPUT_AUDIO_BUFFER_CLEAR = "input_audio_buffer.clear";
  public static final String CONVERSATION_MESSAGE_CREATE = "conversation.message.create";
  public static final String CONVERSATION_CLEAR = "conversation.clear";
  public static final String CONVERSATION_CHAT_CANCEL = "conversation.chat.cancel";

  // resp
  public static final String CHAT_CREATED = "chat.created";
  public static final String CHAT_UPDATED = "chat.updated";
  public static final String CONVERSATION_CHAT_CREATED =
      "conversation.chat.created"; // audio ast completed, chat started
  public static final String CONVERSATION_CHAT_IN_PROGRESS = "conversation.chat.in_progress";
  public static final String CONVERSATION_MESSAGE_DELTA =
      "conversation.message.delta"; // get agent text message update
  public static final String CONVERSATION_AUDIO_DELTA =
      "conversation.audio.delta"; // get agent audio message update
  public static final String CONVERSATION_MESSAGE_COMPLETED = "conversation.message.completed";
  public static final String CONVERSATION_AUDIO_COMPLETED = "conversation.audio.completed";
  public static final String CONVERSATION_CHAT_COMPLETED =
      "conversation.chat.completed"; // all message received, can close connection
  public static final String CONVERSATION_CHAT_FAILED = "conversation.chat.failed"; // chat failed
  public static final String INPUT_AUDIO_BUFFER_CLEARED = "input_audio_buffer.cleared";
  public static final String CONVERSATION_CLEARED = "conversation.cleared";
  public static final String CONVERSATION_CHAT_CANCELED = "conversation.chat.canceled";
  public static final String CONVERSATION_AUDIO_TRANSCRIPT_UPDATE =
      "conversation.audio_transcript.update"; // get agent audio transcript update
  public static final String CONVERSATION_AUDIO_TRANSCRIPT_COMPLETED =
      "conversation.audio_transcript.completed"; // all audio transcript received, can close
  // connection
  public static final String CONVERSATION_CHAT_REQUIRES_ACTION =
      "conversation.chat.requires_action"; // need plugin submit
  public static final String INPUT_AUDIO_BUFFER_SPEECH_STARTED =
      "input_audio_buffer.speech_started";
  public static final String INPUT_AUDIO_BUFFER_SPEECH_STOPPED =
      "input_audio_buffer.speech_stopped";
  public static final String CONVERSATION_AUDIO_SENTENCE_START =
      "conversation.audio.sentence_start";
}
