import { Message } from "@/types/messages";
import { User } from "@/types/user";
import { format } from 'date-fns';

export const MessageItem = ({ message, user }: { message: Message, user: User | null }) => {
  let message_style = "flex flex-col max-w-[50%] p-2 my-2 rounded gap-1";
  if (message.senderUsername === user?.username) {
    message_style += " self-end";
  } else {
    message_style += " self-start";
  }

  let date_style = "flex justify-between w-ful self-end";
  if (message.senderUsername === user?.username) {
    date_style += " self-start";
  } else {
    date_style += " self-end";
  }

  const formattedDate = (messageDate: string) => {
    return format(new Date(messageDate), 'PPpp');
  }
  return (
    <div className={message_style}>
      <div className="p-4 w-fit rounded border border-white overflow-clip">
        <p className="break-all whitespace-normal">{message.content}</p>
      </div>
      <div className={date_style}>
        <div className="text-white text-[10px] self-right">
          {!message.editedAt
            ? formattedDate(message.sentAt.toString())
            : formattedDate(message.editedAt.toString())}
        </div>
        {message.edited &&
          <div className="text-white text-sm">
            Edited
          </div>}
      </div>
    </div>
  )
}
