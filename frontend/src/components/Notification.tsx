//export const Notification = ({ from, content }: { chat: Chat, user: User | null, onClick: Function }) => {

import { useEffect } from "react";

export const Notification = ({
  message, from, show, onClose }: {
    message: string, from: string, show: boolean, onClose: Function
  }) => {
  useEffect(() => {
    if (show) {
      const timer = setTimeout(() => {
        onClose();
      }, 3000);

      return () => clearTimeout(timer); // Cleanup timer on component unmount
    }
  }, [show, onClose]);

  if (!show) return null;

  return (
    <div
      className={`flex flex-row justify-between bg-white text-black px-4 py-3 rounded fixed w-[30%] h-[5rem] bottom-16 left-5 shadow-lg z-50 transition-transform transform ${show ? 'translate-x-0' : 'translate-x-full'
        }`}
      role="alert"
    >
      <div className="flex flex-col overflow-clip">
        <div className="block sm:inline self-start">
          Message from <strong>{from}</strong>
        </div>
        <div className="block sm:inline self-start">{"> "}{message}</div>
      </div>
      <div>
        <button
          onClick={() => onClose}
          className="top-0 bottom-0 left-0 py-3"
        >
          <svg
            className="fill-current h-6 w-6 text-white"
            role="button"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 20 20"
          >
            <path d="M14.348 5.652a.5.5 0 00-.707 0L10 9.293 6.36 5.652a.5.5 0 00-.707.707L9.293 10l-3.64 3.64a.5.5 0 00.707.707L10 10.707l3.64 3.64a.5.5 0 00.707-.707L10.707 10l3.64-3.64a.5.5 0 000-.707z" />
          </svg>
        </button>
      </div>
    </div>
  );
};

