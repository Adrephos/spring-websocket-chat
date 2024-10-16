export type MessagesResponse = {
  messages: Array<Message>,
};

export type Message = {
  id: string,
  content: string,
  sentAt: Date,
  edited: boolean,
  editedAt: Date | null,
  chatId: string,
  senderUsername: string,
  receiverUsername: string,
}
