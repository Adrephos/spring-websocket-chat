export type ChatsResponse = {
  chats: Chat[]
}

export type Chat = {
  id: string,
  firstUsername: string,
  secondUsername: string
}
