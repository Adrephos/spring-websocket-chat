import { Chat, ChatsResponse } from "@/types/chat"
import { Api } from "./apiService"
import { MessagesResponse } from "@/types/messages"

export type CreateChatData = {
  firstUsername: string,
  secondUsername: string
}

const getChats = async (username: string): Promise<ChatsResponse> => {
  return Api.get(`/chats/${username}`);
}
const createChat = async (data: CreateChatData): Promise<Chat> => {
  return Api.post("/chats", data);
}

const getChatMessages = async (chatId: string): Promise<MessagesResponse> => {
  return Api.get(`/chats/${chatId}/messages`);
}

export const chatsService = {
  getChats,
  createChat,
  getChatMessages
}
