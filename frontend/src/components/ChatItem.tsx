import { Chat } from "@/types/chat"
import { User } from "@/types/user"

export const ChatItem = ({ chat, user, onClick }: { chat: Chat, user: User | null, onClick: Function }) => {
  return (
    <div className="text-white rounded bg-black border border-white p-2 hover:bg-neutral-500 cursor-pointer"
      onClick={() => onClick()}>
      {chat.firstUsername == user?.username ? chat.secondUsername : chat.firstUsername}
    </div>
  )
}
