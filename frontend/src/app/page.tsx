"use client"

import { ChatItem } from "@/components/ChatItem";
import { MessageItem } from "@/components/MessageItem";
import { Notification } from "@/components/Notification";
import ProtectedRoute from "@/components/ProtectedRoute";
import { useAuth } from "@/context/AuthContext";
import { chatsService } from "@/services/chatsService";
import { SocketService } from "@/services/websocketService";
import { Chat } from "@/types/chat";
import { Message } from "@/types/messages";
import { AxiosError } from "axios";
import { useCallback, useEffect, useRef, useState } from "react";

export default function Home() {
  const [chats, setChats] = useState(Array<Chat>);
  const [currentChat, setCurrentChat] = useState<Chat | null>(null);
  const [currentChatId, setCurrentChatId] = useState("none");
  const [messages, setMessages] = useState<Message[]>([]);
  const [inputMessage, setInputMessage] = useState("");
  const messagesRef = useRef<HTMLDivElement>(null);
  const [showNotification, setShowNotification] = useState(false);
  const [socket, setSocket] = useState<WebSocket | null>(null);
  const [from, setFrom] = useState("");
  const [notificationMessage, setNotificationMessage] = useState("");
  const [lastMessage, setLastMessage] = useState<Message | null>(null);
  const { user, token, logout} = useAuth();

  const handleShowNotification = () => {
    console.log("showing notification");
    if (!lastMessage) return;
    setFrom(lastMessage.senderUsername);
    setNotificationMessage(lastMessage.content);
    setShowNotification(true);
  };

  const handleCloseNotification = () => {
    setShowNotification(false);
  };

  const scrollToBottom = () => {
    messagesRef.current?.scrollIntoView({ behavior: 'smooth' });
  }

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const receiveMessage = (message: Message) => {
    if (currentChatId == message.chatId) {
      setMessages((prevMessages) => [...prevMessages, message]);
      setLastMessage(message);
    } else {
      setLastMessage(message);
      handleShowNotification();
    }
  };

  const deleteMessage = (message: Message) => {
    setMessages(messages.filter((m) => m.id !== message.id));
  }

  const editMessage = (message: Message) => {
    setMessages(messages.map((m) => m.id === message.id ? message : m));
  }

  useEffect(() => {
    if (token && currentChatId) {
      const socketCleanup = SocketService(
        token,
        setSocket,
        receiveMessage,
        deleteMessage,
        editMessage
      );

      return () => {
        socketCleanup();
      };
    }
  }, [token, currentChatId]);

  const sendMessage = () => {
    if (socket && inputMessage && currentChat && user) {
      const receiver = currentChat.firstUsername === user.username ?
        currentChat.secondUsername : currentChat?.firstUsername;

      const messageDTO = JSON.stringify({
        content: inputMessage,
        senderUsername: user.username,
        receiverUsername: receiver,
      });

      socket.send(`SEND_MESSAGE:${messageDTO}`);
      setInputMessage('');
    }
  };

  useEffect(() => {
    const getChats = async () => {
      try {
        const res = await chatsService.getChats(user?.username || "");
        setChats(res.chats);
      } catch (error) {
        console.error(error);
      } finally {
        console.log("Chats fetched");
      }
    }
    if (user) {
      getChats()
    }
  }, [user, showNotification]);

  const createChat = useCallback(async (receiver: string) => {
    chatsService.createChat({
      firstUsername: user?.username || "",
      secondUsername: receiver,
    }).then((res) => {
      setCurrentChat(res);
      setCurrentChatId(res.id);
      if (chats.find((chat) => chat.id === res.id)) return;
      setChats((prevChats) => [...prevChats, res]);
    }).catch((err: AxiosError) => {
      if (err.response?.status === 404) {
        alert("User not found");
      } else {
        alert("Could not create chat");
      }
    })
  }, [user]);

  useEffect(() => {
    const getMessages = async () => {
      if (currentChat) {
        const res = await chatsService.getChatMessages(currentChat.id)
        setMessages(res.messages)
      }
    }
    getMessages()
  }, [currentChat]);

  return (
    <ProtectedRoute>
      <Notification message={notificationMessage} from={from} show={showNotification} onClose={handleCloseNotification} />
      <div className="flex flex-row justify-between h-screen p-2">
        {/* Chat list */}
        <div className="flex flex-col gap-3 w-[20%] text-white justify-between">
          <div className="flex flex-col gap-3">
            <div className="self-center">
              {user && <h1 className="text-white text-2xl">Welcome, {user.username}</h1>}
            </div>
            {chats.map((chat, idx) => (
              <ChatItem
                key={idx}
                chat={chat}
                user={user}
                onClick={() => {
                  setCurrentChatId(chat.id);
                  setCurrentChat(chat);
                  setSocket(null);
                }}
              />))}
          </div>
          <div className="flex flex-wrap gap-2 self-center">
            <button
              className="bg-neutral hover:bg-neutral-500 p-2 border border-white rounded"
              onClick={() => {
                const receiver = prompt("Enter the username of the user you want to chat with");
                if (receiver) {
                  createChat(receiver);
                }
              }}
            >Add a new chat</button>
            <button
              className="bg-neutral hover:bg-neutral-500 p-2 border border-white rounded"
              onClick={() => {logout()}}
            >Log out</button>
          </div>
        </div>
        {/* Chat UI*/}
        {currentChat && (
          <div className="flex flex-col gap-3 text-white w-[75%] justify-between">
            {/* Chat title */}
            <div className="h-[5%] border border-white flex flex-row items-center justify-center font-bold w-full">
              {currentChat && (
                currentChat.firstUsername === user?.username ?
                  currentChat.secondUsername :
                  currentChat.firstUsername)
              }
            </div>
            {/* Messages */}
            <div className="flex flex-col gap-2 overflow-y-scroll h-[85%]">
              {currentChat && messages.map((message) => (
                <MessageItem key={message.id} message={message} user={user} />
              ))}
              <div ref={messagesRef} />
            </div>
            {/* Input */}
            <div className="h-[7%] border border-white flex flex-row">
              <input
                type="text"
                className="w-[90%] p-2 bg-black text-white h-full focus:outline-none"
                placeholder="Type a message..."
                value={inputMessage}
                onChange={(e) => setInputMessage(e.target.value)}
                onKeyDown={(e) => {
                  if (e.key === 'Enter') sendMessage();
                }}
              />
              {/* Send button */}
              <button
                onClick={sendMessage}
                className="w-[10%] bg-neutral hover:bg-neutral-500"
              >
                Send</button>
            </div>
          </div>
        )}
      </div>
    </ProtectedRoute>
  );
}
