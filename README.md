# Coze Java API SDK

[![codecov](https://codecov.io/github/coze-dev/coze-java/graph/badge.svg?token=UXitaQ0wp7)](https://codecov.io/github/coze-dev/coze-java)

## Introduction

The Coze API SDK for Java is a powerful tool designed to seamlessly integrate Coze's open APIs into your projects.

Key Features:
- Full support for Coze open APIs and authentication APIs
- Both synchronous and asynchronous SDK calls
- Optimized streaming APIs with Stream object returns  
- Optimized list APIs with Iterator Page object returns
- Simple and intuitive API design for ease of use


## Usage

### Examples

| Example                       | File                                                                                                        |
|-------------------------------|-------------------------------------------------------------------------------------------------------------|
| pat auth                      | [TokenAuthExample.java](example/src/main/java/example/auth/TokenAuthExample.java)                           |
| oauth by web code             | [WebOAuthExample.java](example/src/main/java/example/auth/WebOAuthExample.java)                             |
| oauth by jwt flow             | [JWTsOauthExample.java](example/src/main/java/example/auth/JWTOAuthExample.java)                            |
| oauth by pkce flow            | [PKCEOauthExample.java](example/src/main/java/example/auth/PKCEOAuthExample.java)                           |
| oauth by device flow          | [DevicesOAuthExample.java](example/src/main/java/example/auth/DevicesOAuthExample.java)                     |
| handle auth exception         | [HandlerExceptionExample.java](example/src/main/java/example/auth/HandlerExceptionExample.java)             |
| bot create, publish and chat  | [PublishBotExample.java](example/src/main/java/example/bot/PublishBotExample.java)                          |
| get bot and bot list          | [GetBotExample.java](example/src/main/java/example/bot/GetBotExample.java)                                  |
| non-stream chat               | [ChatExample.java](example/src/main/java/example/chat/ChatExample.java)                                     |
| steam chat                    | [StreamChatExample.java](example/src/main/java/example/chat/StreamChatExample.java)                         |
| chat with local plugin        | [SubmitToolOutputExample.java](example/src/main/java/example/chat/SubmitToolOutputExample.java)             |
| chat with image               | [ChatWithImageExample.java](example/src/main/java/example/chat/ChatWithImageExample.java)                   |
| non-stream workflow chat      | [RunWorkflowExample.java](example/src/main/java/example/workflow/RunWorkflowExample.java)                   |
| stream workflow chat          | [StreamWorkflowExample.java](example/src/main/java/example/workflow/StreamWorkflowExample.java)             |
| async workflow run            | [AsyncRunWorkflowExample.java](example/src/main/java/example/workflow/AsyncRunWorkflowExample.java)         |
| conversation                  | [CreateConversationExample.java](example/src/main/java/example/conversation/CreateConversationExample.java) |
| list conversation             | [ListConversationsExample.java](example/src/main/java/example/conversation/ListConversationsExample.java)   |
| workspace                     | [ListWorkspaceExample.java](example/src/main/java/example/workspace/ListWorkspaceExample.java)              |
| create update delete message  | [ListWorkspaceExample.java](example/src/main/java/example/conversation/message/CrudMessageExample.java)     |
| list message                  | [ListWorkspaceExample.java](example/src/main/java/example/conversation/message/ListMessageExample.java)     |
| create update delete document | [ListWorkspaceExample.java](example/src/main/java/example/datasets/document/CrudDocumentExample.java)       |
| initial client                | [InitServiceExample.java](example/src/main/java/example/service/InitClientExample.java)                     |
| how to handle exception       | [HandlerExceptionExample.java](example/src/main/java/example/service/HandlerExceptionExample.java)          |
| get request log id            | [GetLogExample.java](example/src/main/java/example/service/GetLogExample.java)                              |
| set timeout                   | [SetRequestTimeoutExample.java](example/src/main/java/example/service/SetRequestTimeoutExample.java)        |

### Initialize the Coze Client 

To get started, visit https://www.coze.com/open/oauth/pats (or https://www.coze.cn/open/oauth/pats for the CN environment).

Create a new token by clicking "Add Token". Configure the token name, expiration time, and required permissions. Click OK to generate your personal access token.

Important: Store your personal access token securely to prevent unauthorized access.

```java
public void example() {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);
    /*
     * The default access is api.coze.com, but if you need to access api.coze.cn
     * please use baseUrl to configure the API endpoint to access
     */
    String baseUrl = System.getenv("COZE_API_BASE");
    CozeAPI coze = new CozeAPI.Builder()
            .baseURL(baseUrl)
            .auth(authCli)
            .client(new OkHttpClient.Builder().build()) 
            .build();
}
```

### Chat

First, create a bot instance in Coze. The bot ID is the last number in the web link URL.

#### Non-Stream Chat

The SDK provides a convenient wrapper function for non-streaming chat operations. It handles polling and message retrieval automatically. Use createAndPoll to simplify the process:

```java
public void example() throws Exception {
    String token = System.getenv("COZE_API_TOKEN");
    String botID = System.getenv("PUBLISHED_BOT_ID");
    String uid = System.getenv("USER_ID");
    CozeAPI coze = new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(new TokenAuth(token))
            .readTimeout(10000)
            .build();
    ChatReq req = ChatReq.builder()
                         .botID(botID)
                         .userID(uid)
                         .messages(Collections.singletonList(Message.buildUserQuestionText("What can you do?")))
                         .build();
    ChatPoll chat = coze.chat().createAndPoll(req);
    for (Message message : chat.messages) {
        System.out.println(message.content);
    }
    if (chat.chat.status == ChatStatus.COMPLETED) {
        System.out.println("token usage:" + chat.chat.usage.tokenCount);
    }
}

```


#### Stream Chat 

Use coze.chat.stream() to create a streaming chat session. This returns a Chat Iterator that you can use to process chat events as they arrive:

```java

public void example() {
    String token = System.getenv("COZE_API_TOKEN");
    String botID = System.getenv("PUBLISHED_BOT_ID");
    String userID = System.getenv("USER_ID");
    TokenAuth authCli = new TokenAuth(token);
    CozeAPI coze = new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .build();
    ChatReq req = ChatReq.builder()
            .botID(botID)
            .userID(userID)
            .messages(Collections.singletonList(Message.buildUserQuestionText("What can you do?")))
            .build();
    Flowable<ChatEvent> resp = coze.chat().stream(req);
    resp.blockingForEach(event -> {
        if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
            System.out.print(event.getMessage().getContent());
        }
        if (ChatEventType.CONVERSATION_CHAT_COMPLETED.equals(event.getEvent())) {
            System.out.println("Token usage:" + event.getChat().getUsage().getTokenCount());
        }
    });
}

```

### Bots

The SDK enables you to programmatically create, update, publish and list bots:

```java
public void example() {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);
    // Init the Coze client through the access_token.
    CozeAPI coze = new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .build();
    /*
    * step one, create a bot
    * */
    String workspaceID = System.getenv("WORKSPACE_ID");
    // set the prompt of your bot
    BotPromptInfo promptInfo = new BotPromptInfo("your prompt");
    // set the onboarding info of your bot
    BotOnboardingInfo onboardingInfo = BotOnboardingInfo.builder()
            .prologue("the prologue of your bot")
            .suggestedQuestions(Arrays.asList("question 1", "question 2"))
            .build();
    // Call the upload file interface to get the avatar id.
    String avatarPath = "/path/avatar.jpg";
    FileInfo avatarInfo = coze.files().upload(avatarPath);
    // build the request
    CreateBotReq createReq = CreateBotReq.builder()
            .spaceID(workspaceID)
            .description("the description of your bot")
            .name("the name of your bot")
            .promptInfo(promptInfo)
            .onboardingInfo(onboardingInfo)
            .iconFileID(avatarInfo.getID())
            .build();
    // Invoke the creation interface to create a bot in the draft status, and you can get the bot id.
    String botID = coze.bots().create(createReq).getBotID();
    /*
     * step two, update the bot, you can update the bot after being created
     * in this example, we will update the avatar of the bot
     */
    // Call the publishing interface to publish the bot on the api channel.
    PublishBotResult updateResp = coze.bots().publish(PublishBotReq.of(botID));
    /*
     * step three, you can also modify the bot configuration and republish it.
     * in this example, we will update the avatar of the bot
     */
    // set the onboarding info of your bot
    // Call the upload file interface to get the avatar id.
    String newAvatarPath = "/path/new_avatar.jpg";
    FileInfo newAvatarInfo = coze.files().upload(newAvatarPath);
    // build the request
    UpdateBotReq updateReq = UpdateBotReq.builder()
            .botID(botID)
            .iconFileID(newAvatarInfo.getID())
            .build();
    // Invoke the update interface to update a bot, It means success that no exception has been thrown.
    coze.bots().update(updateReq);
    updateResp = coze.bots().publish(PublishBotReq.of(botID));
}

```

### Conversations 

The SDK provides comprehensive conversation management capabilities, including:
- Creating new conversations
- Conducting chat sessions
- Retrieving message history
- Managing conversation state

```java

public void example() {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);
    // Init the Coze client through the access_token.
    CozeAPI coze = new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();
    // create conversation
    Conversation resp = coze.conversations().create(new CreateConversationReq());
    System.out.println("create conversations" + resp);
    // retrieve conversation
    String conversationID = resp.getId();
    Conversation getResp = coze.conversations().retrieve(RetrieveConversationReq.of(conversationID));
    System.out.println("retrieve conversations:" + getResp);
    // append message to conversation
    Message msgs = coze.conversations().messages().create(CreateMessageReq
            .builder()
            .conversationID(conversationID)
            // if you want to create object content, you can use followed method to simplify your code
            .objectContent(
                    Arrays.asList(MessageObjectString.buildText("hello"),
                            MessageObjectString.buildImageByURL(System.getenv("PICTURE_URL")),
                            MessageObjectString.buildFileByURL(System.getenv("FILE_URL"))))
            .build());
    System.out.println(msgs);
    /*
    * retrieve message
    * */
    Message retrievedMsg = coze.conversations().messages().retrieve(RetrieveMessageReq.builder()
            .conversationID(conversationID)
            .messageID(message.getId())
            .build());
    System.out.println(retrievedMsg);
    /*
    * update message
    * */
    UpdateMessageReq updateReq = UpdateMessageReq.builder()
            .conversationID(conversationID).messageID(message.getId())
            .content(String.format("modified message content:%s", message.getContent()))
            .contentType(MessageContentType.TEXT).build();
    Message resp = coze.conversations().messages().update(updateReq);
    System.out.println(resp);
    /*
    * delete message
    * */
    Message deletedMsg = coze.conversations().messages().delete(DeleteMessageReq.builder()
            .conversationID(conversationID)
            .messageID(message.getId())
            .build());
    System.out.println(deletedMsg);
    // list messages
    ListMessageReq req = ListMessageReq.builder()
            .conversationID(conversationID)
            .limit(limit)
            .build();
    PageResult<Message> messages = coze.conversations().messages().list(req);
    Iterator<Message> iter = messages.getIterator();
    iter.forEachRemaining(System.out::println);
    // clear conversation
    ClearConversationResult clearResp = coze.conversations().clear(ClearConversationReq.of(conversationID));
    System.out.println(clearResp);
}
```

### Files

The SDK supports file and image uploads. You can use uploaded files for:
- Bot avatars
- Multimodal conversations 

```java
public void example() {
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);
    CozeAPI coze = new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();
    String filePath = System.getenv("FILE_PATH");
    //*** upload file ***//
    FileInfo fileInfo = coze.files().upload(filePath);
    // you can also upload file in byte array
    FileInfo fileInfo = coze.files().upload(new byte[1024]);
    //*** retrive file ***//
    FileInfo retrievedInfo = coze.files().retrieve(fileInfo.getID());
    System.out.println(retrievedInfo);
}

```

### Workflows

Coze also enables users to directly invoke the workflow.

#### Non-stream workflow run

```java
public static void main(String[] args) {
// Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);
    // Init the Coze client through the access_token.
    CozeAPI coze = new CozeAPI.Builder()
        .baseURL(System.getenv("COZE_API_BASE"))
        .auth(authCli)
        .readTimeout(10000)
        .build();
    String workflowID = System.getenv("WORKSPACE_ID");
    // if your workflow need input params, you can send them by map
    Map<String, Object> data = new HashMap<>();
    data.put("param name", "param values");
    RunWorkflowReq.RunWorkflowReqBuilder builder = RunWorkflowReq.builder();
    builder.workflowID(workflowID).parameters(data);
    RunWorkflowResp resp = coze.workflows().runs().run(builder.build());
    System.out.println(resp);
}
```

#### Stream workflow run

The returned result of the streaming interface is an iterator and can be directly iterated.

When the workflow incorporates question-and-answer nodes, the streaming interface will
return the INTERRUPT event.

Users should call the resume interface to submit the results of the question-and-answer.

The return value of resume remains an iterator, so recursive processing might be necessary here.

```java
public static void main(String[] args) {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);
    // Init the Coze client through the access_token.
    CozeAPI coze = new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();
    String workflowID = System.getenv("WORKSPACE_ID");
    // if your workflow need input params, you can send them by map
    Map<String, Object> data = new HashMap<>();
    data.put("param name", "param values");
    RunWorkflowReq.RunWorkflowReqBuilder builder = RunWorkflowReq.builder();
    builder.workflowID(workflowID).parameters(data);
    Flowable<WorkflowEvent> flowable = coze.workflows().runs().stream(builder.build());
    handleEvent(flowable, coze, workflowID);
}
```

### Dataset

```java
public static void main(String[] args) {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);
    // Init the Coze client through the access_token.
    CozeAPI coze = new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();
    Long datasetID = Long.parseLong(System.getenv("DATASET_ID"));
    /*
     * create document in to specific dataset
     * */
    CreateDocumentReq createReq = CreateDocumentReq.builder()
            .datasetID(datasetID)
            .documentBases(Arrays.asList(
                    DocumentBase.buildWebPage("web doc example", "https://your-website.com"), //create datasets documents by web page
                    DocumentBase.buildLocalFile("file doc example", "your file content", "txt"))) //create datasets documents by local file
            .build();
    CreateDocumentResp creatResp = coze.datasets().documents().create(createReq);
    List<Long> documentIDs = new ArrayList<>();
    for (Document documentBase : creatResp.getDocumentInfos()) {
        documentIDs.add(Long.parseLong(documentBase.getDocumentID()));
    }
    /*
     * update document. It means success that no exception has been thrown
     * */
    UpdateDocumentReq updateReq = UpdateDocumentReq.builder()
            .documentID(documentIDs.get(0))
            .documentName("new name")
            .build();
    coze.datasets().documents().update(updateReq);
    /*
     * delete document. It means success that no exception has been thrown
     * */
    coze.datasets().documents().delete(DeleteDocumentReq.builder().documentIDs(Collections.singletonList(documentIDs.get(0))).build());

    /*
     * list documents
     * */
    ListDocumentReq req = ListDocumentReq.builder()
                .size(2)
                .datasetID(datasetID)
                .page(1)
                .build();

    // you can use iterator to automatically retrieve next page
    PageResult<Document> documents = coze.datasets().documents().list(req);
    Iterator<Document> iter = documents.getIterator();
    iter.forEachRemaining(System.out::println);
}
```

### OAuth App

#### Web OAuth App

To create a Web OAuth application:

1. Visit https://www.coze.com/open/oauth/apps (or https://www.coze.cn/open/oauth/apps for CN environment)
2. Create a new OAuth App of type "Web application" 
3. Follow the setup guide at https://www.coze.com/docs/developer_guides/oauth_code

Upon completion, you'll receive three critical parameters:
- Client ID
- Client secret (store securely)
- Redirect URI

```java
public void initOAuthClient() {
    String clientSecret = System.getenv("COZE_WEB_OAUTH_CLIENT_SECRET");
    String clientID = System.getenv("COZE_WEB_OAUTH_CLIENT_ID");
    
    String cozeAPIBase = System.getenv("COZE_API_BASE");
    if (cozeAPIBase == null || cozeAPIBase.isEmpty()) {
        cozeAPIBase = Consts.COZE_COM_BASE_URL;
    }
    
    WebOAuthClient oauth = new WebOAuthClient.WebOAuthBuilder()
            .clientID(clientID)
            .clientSecret(clientSecret)
            .baseURL(cozeAPIBase)
            .build();
}
```

The WebOAuth authorization process is to first generate a coze authorization link and
send it to the coze user requiring authorization.

Once the coze user opens the link, they can see the authorization consent button.

```java

public void genAuthLink() {
    String redirectURI = System.getenv("COZE_WEB_OAUTH_REDIRECT_URI");
    // Generate the authorization link and direct the user to open it.
    String oauthURL = oauth.getOAuthURL(redirectURI, null);
}
```

After the user clicks the authorization consent button, the coze web page will redirect
to the redirect address configured in the authorization link and carry the authorization
code and state parameters in the address via the query string.

```java
public void getAccessToken() {
    /*
    After the user clicks the authorization consent button, the Coze web page will redirect
    to the redirect address configured in the authorization link and carry the authorization
    code and state parameters in the address via the query string.
    Get from the query of the redirect interface: query.get('code')
    */
    String code = "mock code";

    /*
    After obtaining the code after redirection, the interface to exchange the code for a
    token can be invoked to generate the Coze access_token of the authorized user.
    */
    OAuthToken resp = oauth.getAccessToken(code, redirectURI);
    System.out.println(resp);

    // Use the access token to init Coze client
    CozeAPI coze = new CozeAPI.Builder().auth(new TokenAuth(resp.getAccessToken())).baseURL(cozeAPIBase).build();

    // When the token expires, you can also refresh and re-obtain the token
    resp = oauth.refreshToken(resp.getRefreshToken());
}
```

#### JWT OAuth App

Firstly, users need to access https://www.coze.com/open/oauth/apps. For the cn environment,
users need to access https://www.coze.cn/open/oauth/apps to create an OAuth App of the type
of Service application.

The specific creation process can be referred to in the document:
https://www.coze.com/docs/developer_guides/oauth_jwt. For the cn environment, it can be
accessed at https://www.coze.cn/docs/developer_guides/oauth_jwt.

After the creation is completed, three parameters, namely the client ID, private key,
and public key id, can be obtained. For the client secret and public key id, users need to
keep it securely to avoid leakage.

```java
public void initOAuthClient() {
    String cozeAPIBase = System.getenv("COZE_API_BASE");
    if (cozeAPIBase == null || cozeAPIBase.isEmpty()) {
        cozeAPIBase = "api.coze.cn";
    }
    String jwtOauthClientID = System.getenv("COZE_JWT_OAUTH_CLIENT_ID");
    String jwtOauthPrivateKey = System.getenv("COZE_JWT_OAUTH_PRIVATE_KEY");
    String jwtOauthPrivateKeyFilePath = System.getenv("COZE_JWT_OAUTH_PRIVATE_KEY_FILE_PATH");
    String jwtOauthPublicKeyID = System.getenv("COZE_JWT_OAUTH_PUBLIC_KEY_ID");
    JWTOAuthClient oauth = null;
    try {
        jwtOauthPrivateKey = new String(Files.readAllBytes(Paths.get(jwtOauthPrivateKeyFilePath)), StandardCharsets.UTF_8);
    } catch (IOException e) {
        e.printStackTrace();
    }
    /*
    The jwt oauth type requires using private to be able to issue a jwt token, and through
    the jwt token, apply for an access_token from the coze service. The sdk encapsulates
    this procedure, and only needs to use get_access_token to obtain the access_token under
    the jwt oauth process.
    Generate the authorization token
    The default ttl is 900s, and developers can customize the expiration time, which can be
    set up to 24 hours at most.
    */
    try {
        oauth = new JWTOAuthClient.JWTOAuthBuilder()
                .clientID(jwtOauthClientID)
                .privateKey(jwtOauthPrivateKey)
                .publicKey(jwtOauthPublicKeyID)
                .baseURL(cozeAPIBase)
                .build();
    } catch (Exception e) {
        e.printStackTrace();
        return;
    }
}
```

The jwt oauth type requires using private to be able to issue a jwt token, and through
the jwt token, apply for an access_token from the coze service.

The sdk encapsulates this procedure, and only needs to use get_access_token to obtain
the access_token under the jwt oauth process.

```java
# The jwt process does not require any other operations, you can directly apply for a token
oauth_token = jwt_oauth_app.get_access_token(ttl=3600)

public void getAccessToken() {
    // The jwt process does not require any other operations, you can directly apply for a token
    OAuthToken resp = oauth.getAccessToken(3600);

    // Use the access token to init Coze client
    CozeAPI coze = new CozeAPI.Builder().auth(new TokenAuth(resp.getAccessToken())).baseURL(cozeAPIBase).build();

}
```

#### PKCE OAuth App

PKCE stands for Proof Key for Code Exchange, and it's an extension to the OAuth 2.0 authorization
code flow designed to enhance security for public clients, such as mobile and single-page
applications.

Firstly, users need to access https://www.coze.com/open/oauth/apps. For the cn environment,
users need to access https://www.coze.cn/open/oauth/apps to create an OAuth App of the type
of Mobile/PC/Single-page application.

The specific creation process can be referred to in the document:
https://www.coze.com/docs/developer_guides/oauth_pkce. For the cn environment, it can be
accessed at https://www.coze.cn/docs/developer_guides/oauth_pkce.

After the creation is completed, three parameters, namely the client ID can be obtained.

```java
public void initOAuthClient() {
    String clientID = System.getenv("COZE_PKCE_OAUTH_CLIENT_ID");
    /*
     * The default access is api.coze.com, but if you need to access api.coze.cn,
     * please use base_url to configure the api endpoint to access
     */
    String cozeAPIBase = System.getenv("COZE_API_BASE");
    if (cozeAPIBase == null || cozeAPIBase.isEmpty()) {
        cozeAPIBase = Consts.COZE_COM_BASE_URL;
    }
    PKCEOAuthClient oauth = new PKCEOAuthClient.PKCEOAuthBuilder()
            .clientID(clientID)
            .baseURL(cozeAPIBase)
            .build();
}
```

In the pkce oauth process, first, need to select a suitable code_challenge_method.
Coze supports two types: plain and s256.

Then, based on the selected code_challenge_method type, hash the code_verifier into
the code_challenge. Finally, based on the callback address,
code_challenge, and code_challenge_method, an authorization link can be generated.

The sdk will automatically handle the code_challenge process, and return the code verifier.

```java

public void genAuthLink() {
    String redirectURI = System.getenv("COZE_WEB_OAUTH_REDIRECT_URI");
    PKCEAuthParam oauthURL = oauth.genOAuthURL(redirectURI, "state", PKCEOAuthClient.CodeChallengeMethod.S256);
    System.out.println(oauthURL);
}
```

Developers should lead users to open up this authorization link.

When the user consents to the authorization, Coze will redirect with the code to the
callback address configured by the developer, and the developer can obtain this code.

```java
public void getAccessToken() {
    /*
    Open the authorization link in your browser and authorize this OAuth App
    After authorization, you can exchange code_verifier for access token
    */
    String code = "mock code";
    /*
    After obtaining the code after redirection, the interface to exchange the code for a
    token can be invoked to generate the Coze access_token of the authorized user.
    */
    OAuthToken resp = oauth.getAccessToken(code, redirectURI, "");
    System.out.println(resp);
    // Use the access token to init Coze client
    CozeAPI coze = new CozeAPI.Builder().auth(new TokenAuth(resp.getAccessToken())).baseURL(cozeAPIBase).build();
    // When the token expires, you can also refresh and re-obtain the token
    resp = oauth.refreshToken(resp.getRefreshToken());
}
```

#### Device OAuth App

The Device Flow is designed for devices with limited input capabilities or no browser access. This OAuth 2.0 extension enables secure authorization for IoT devices, CLI tools, and other limited-input scenarios.

Firstly, users need to access https://www.coze.com/open/oauth/apps. For the cn environment,
users need to access https://www.coze.cn/open/oauth/apps to create an OAuth App of the type
of TVs/Limited Input devices/Command line programs.

The specific creation process can be referred to in the document:
https://www.coze.com/docs/developer_guides/oauth_device_code. For the cn environment, it can be
accessed at https://www.coze.cn/docs/developer_guides/oauth_device_code.

After the creation is completed, three parameters, namely the client ID can be obtained.

```java
public void initOAuthClient() {
    String clientID = System.getenv("COZE_PKCE_OAUTH_CLIENT_ID");
    /*
     * The default access is api.coze.com, but if you need to access api.coze.cn,
     * please use base_url to configure the api endpoint to access
     */
    String cozeAPIBase = System.getenv("COZE_API_BASE");
    if (cozeAPIBase == null || cozeAPIBase.isEmpty()) {
        cozeAPIBase = Consts.COZE_COM_BASE_URL;
    }
    DeviceOAuthClient oauth = new DeviceOAuthClient.DeviceOAuthBuilder()
        .clientID(clientID)
        .baseURL(cozeAPIBase)
        .build();
}
```

In the device oauth authorization process, developers need to first call the interface
of Coze to generate the device code to obtain the user_code and device_code.

Then generate the authorization link through the user_code, guide the user to open the
link, fill in the user_code, and consent to the authorization.

Developers need to call the interface of Coze to generate the token through the device_code.

When the user has not authorized or rejected the authorization, the interface will throw an
error and return a specific error code.

After the user consents to the authorization, the interface will succeed and return the
access_token.

```java
// First, you need to request the server to obtain the device code required in the device auth flow
DeviceAuthCode codeResp = oauth.getDeviceCode();

// The returned device_code contains an authorization link. Developers need to guide users
// to open up this link.
// open device_code.verification_url
```

The developers then need to use the device_code to poll Coze's interface to obtain the token.

The SDK has encapsulated this part of the code in and handled the different returned error
codes. The developers only need to invoke get_access_token.

```java
try {
    OAuthToken resp = oauth.getAccessToken(codeResp.getDeviceCode());
    System.out.println(resp);

    // Use the access token to init Coze client
    CozeAPI coze = new CozeAPI.Builder().auth(new TokenAuth(resp.getAccessToken())).baseURL(cozeAPIBase).build();
    // When the token expires, you can also refresh and re-obtain the token
    resp = oauth.refreshToken(resp.getRefreshToken());
} catch (CozeAuthException e) {
    switch (e.getCode()) {
        case AccessDenied:
            /*
            The user rejected the authorization.
            Developers need to guide the user to open the authorization link again.
            */
            break;
        case ExpiredToken:
            /*
            The token has expired. Developers need to guide the user to open
            the authorization link again.
            */
        default:
            e.printStackTrace();
            break;
    }
} catch (Exception e) {
    e.printStackTrace();
}
```

### Streaming usage

Bot chat and workflow run support running in streaming mode.

chat streaming example:

```java
String token = System.getenv("COZE_API_TOKEN");
String botID = System.getenv("PUBLISHED_BOT_ID");
String userID = System.getenv("USER_ID");

TokenAuth authCli = new TokenAuth(token);

// Init the Coze client through the access_token.
CozeAPI coze = new CozeAPI.Builder()
        .baseURL(System.getenv("COZE_API_BASE"))
        .auth(authCli)
        .readTimeout(10000)
        .build();;

/*
 * Step one, create chat
 * Call the coze.chat().stream() method to create a chat. The create method is a streaming
 * chat and will return a Flowable ChatEvent. Developers should iterate the iterator to get
 * chat event and handle them.
 * */
ChatReq req = ChatReq.builder()
        .botID(botID)
        .userID(userID)
        .messages(Collections.singletonList(Message.buildUserQuestionText("What can you do?")))
        .build();

Flowable<ChatEvent> resp = coze.chat().stream(req);
resp.blockingForEach(event -> {
    if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
        System.out.print(event.getMessage().getContent());
    }
    if (ChatEventType.CONVERSATION_CHAT_COMPLETED.equals(event.getEvent())) {
        System.out.println("Token usage:" + event.getChat().getUsage().getTokenCount());
    }
});
```

workflow streaming example:

```java
public void streamRun() {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze = new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();

    String workflowID = System.getenv("WORKSPACE_ID");

    // if your workflow need input params, you can send them by map
    Map<String, Object> data = new HashMap<>();
    data.put("param name", "param values");

    RunWorkflowReq.RunWorkflowReqBuilder builder = RunWorkflowReq.builder();
    builder.workflowID(workflowID).parameters(data);

    Flowable<WorkflowEvent> flowable = coze.workflows().runs().stream(builder.build());
    handleEvent(flowable, coze, workflowID);
}

private void handleEvent(Flowable<WorkflowEvent> events, CozeAPI coze, String workflowID) {
    events.subscribe(event -> {
        switch (event.getEvent()) {
            case MESSAGE:
                System.out.println("Got message" + event.getMessage());
                break;
            case ERROR:
                System.out.println("Got error" + event.getError());
                break;
            case INTERRUPT:
                handleEvent(coze.workflows().runs().resume(
                        ResumeRunReq.builder()
                                .workflowID(workflowID)
                                .eventID(event.getInterrupt().getInterruptData().getEventID())
                                .resumeData("your data")
                                .interruptType(event.getInterrupt().getInterruptData().getType())
                                .build()), coze, workflowID);
                break;
            default:
                break;
        }
    }, Throwable::printStackTrace);
}

```

### Paginator Iterator

The result returned by all list interfaces is a paginator, which supports iteration.

Take the example of listing the bots in a space to explain the three ways to use the paginator iterator:

#### 1. Not using iterators

```java
String token = System.getenv("COZE_API_TOKEN");
TokenAuth authCli = new TokenAuth(token);

// Init the Coze client through the access_token.
CozeAPI coze = new CozeAPI.Builder()
        .baseURL(System.getenv("COZE_API_BASE"))
        .auth(authCli)
        .readTimeout(10000)
        .build();;

Integer pageNum = 1;
String workspaceID = System.getenv("WORKSPACE_ID");
ListBotReq listBotReq = ListBotReq.builder()
        .spaceID(workspaceID)
        .pageNum(pageNum)
        .pageSize(10)
        .build();
PageResult<SimpleBot> botList = coze.bots().list(listBotReq);

// the api provides two ways for developers to turn pages for all paging interfaces.
// 1. The first way is to let developers manually call the API to request the next page.
while (botList.getHasMore()) {
    pageNum++;
    listBotReq.setPageNum(pageNum);
    botList = coze.bots().list(listBotReq);
}

// 2. The SDK encapsulates an iterator, which can be used to turn pages backward automatically.

```

#### 2. Iterate over the paginator, getting T

```java
String token = System.getenv("COZE_API_TOKEN");
TokenAuth authCli = new TokenAuth(token);

// Init the Coze client through the access_token.
CozeAPI coze = new CozeAPI.Builder()
        .baseURL(System.getenv("COZE_API_BASE"))
        .auth(authCli)
        .readTimeout(10000)
        .build();;

Integer pageNum = 1;
String workspaceID = System.getenv("WORKSPACE_ID");
ListBotReq listBotReq = ListBotReq.builder()
        .spaceID(workspaceID)
        .pageNum(pageNum)
        .pageSize(10)
        .build();
PageResult<SimpleBot> botList = coze.bots().list(listBotReq);

// the api provides two ways for developers to turn pages for all paging interfaces.
// 1. The first way is to let developers manually call the API to request the next page.
Iterator<SimpleBot> iterator = botList.getIterator();
while (iterator.hasNext()) {
    iterator.forEachRemaining(System.out::println);
}

```


