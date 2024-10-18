-- Create Users table
CREATE TABLE USERS (
    "username" VARCHAR(50) PRIMARY KEY,
    "email" VARCHAR(100) NOT NULL,
    "password" VARCHAR(100) NOT NULL
);

-- Create Chats table
CREATE TABLE CHATS (
    "chat_id" UUID PRIMARY KEY,
    "first_username" VARCHAR(50) NOT NULL,
    "second_username" VARCHAR(50) NOT NULL,
    CONSTRAINT "fk_first_user" FOREIGN KEY ("first_username") REFERENCES USERS (username),
    CONSTRAINT "fk_second_user" FOREIGN KEY ("second_username") REFERENCES USERS (username),
    CONSTRAINT "unique_users" UNIQUE ("first_username", "second_username") -- Ensures no duplicate chats between users
);

-- Create Messages table
CREATE TABLE MESSAGES (
    "message_id" UUID PRIMARY KEY,
    "content" TEXT NOT NULL,
    "sent_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "edited" BOOLEAN NOT NULL DEFAULT FALSE,
    "edited_at" TIMESTAMP DEFAULT NULL,
	"chat_id" UUID NOT NULL,
    "sender_username" VARCHAR(50) NOT NULL,
    "receiver_username" VARCHAR(50) NOT NULL,
    CONSTRAINT "fk_sender" FOREIGN KEY ("sender_username") REFERENCES USERS ("username"),
    CONSTRAINT "fk_receiver" FOREIGN KEY ("receiver_username") REFERENCES USERS ("username"),
    CONSTRAINT "fk_chat" FOREIGN KEY ("chat_id") REFERENCES CHATS ("chat_id")
);
