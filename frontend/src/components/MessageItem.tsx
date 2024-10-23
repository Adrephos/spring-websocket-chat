import { useState } from "react";
import { Message } from "@/types/messages";
import { User } from "@/types/user";
import { format } from 'date-fns';
import { CheckIcon, TrashIcon, EditIcon, XIcon } from "../components/Icons";

export const MessageItem = (
  { message,
    user,
    updateMessage,
    deleteMessage }:
    {
      message: Message,
      user: User | null,
      updateMessage: (id: string, content: string) => void
      deleteMessage: (id: string) => void
    }) => {

  const [isEditing, setIsEditing] = useState(false); // Track edit mode
  const [editedContent, setEditedContent] = useState(message.content); // Track the edited message content

  let message_style = "flex flex-col max-w-[50%] p-2 my-2 rounded gap-1";
  let message_align = " self-start";
  if (message.senderUsername === user?.username) {
    message_align = " self-end";
  }

  let date_style = "flex justify-between w-ful";
  if (message.senderUsername === user?.username) {
    date_style += " self-end";
  } else {
    date_style += " self-start";
  }

  const formattedDate = (messageDate: string) => {
    return format(new Date(messageDate), 'PPpp');
  }

  const handleSave = () => {
    if (!editedContent) return;
    updateMessage(message.id, editedContent);
    setIsEditing(false);
  }

  // Handle Cancel action
  const handleCancel = () => {
    setEditedContent(message.content);
    setIsEditing(false);
  }

  return (
    <div className={message_style + message_align}>
      <div className={"p-4 w-fit max-w-full rounded border border-white" + message_align}>
        {isEditing ? (
          <div className="flex flex-col">
            <textarea
              className="w-full p-2 border border-gray-300 rounded text-black"
              value={editedContent}
              onChange={(e) => setEditedContent(e.target.value)}
            />
            <div className="flex justify-center gap-2 mt-2 items-center" >
              <button
                className="px-2 py-1 text-white rounded hover:bg-neutral-500"
                onClick={handleSave}
              >
                <CheckIcon />
              </button>
              <button
                className="px-2 py-1 text-white rounded hover:bg-neutral-500"
                onClick={handleCancel}
              >
                <XIcon />
              </button>
            </div>
          </div>
        ) : (
          <p className="break-words">{message.content}</p>
        )}
      </div>

      <div className={date_style}>
        <div className="text-white text-[10px] self-right">
          {message.editedAt
            ? formattedDate(message.editedAt.toString())
            : formattedDate(message.sentAt.toString())}
          {message.edited &&
            " - Edited"}
        </div>
      </div>

      {/* Show Edit and Delete buttons if the message belongs to the current user */}
      {message.senderUsername === user?.username && !isEditing && (
        <div className="flex flex-row gap-1 self-end">
          <button
            className="text-xs text-blue-500 mt-1 underline hover:text-blue-300"
            onClick={() => setIsEditing(true)}
          >
            <EditIcon />
          </button>
          <button
            className="text-xs text-red-500 mt-1 underline hover:text-red-300"
            onClick={() => deleteMessage(message.id)}
          >
            <TrashIcon />
          </button>
        </div>
      )}
    </div>
  );
}

