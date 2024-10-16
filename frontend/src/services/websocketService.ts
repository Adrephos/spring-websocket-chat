export const SocketService = (
  token: string,
  setSocket: Function,
  receiveMessage: Function,
  deleteMessage: Function,
  editMessage: Function
) => {
  const url = `ws://localhost:8080/ws-chat?token=${token}`
  const socket = new WebSocket(url);
  setSocket(socket);

  socket.onopen = () => {
    if (socket.readyState === WebSocket.OPEN) {
      socket.onmessage = (event: MessageEvent) => {
        console.log('Received message', event.data);
        if (event.data.startsWith('CONNECT:')) {
          return
        }
        if (event.data.startsWith('RECEIVE_MESSAGE:')) {
          const message = event.data.replace('RECEIVE_MESSAGE:', '');
          const messageObj = JSON.parse(message);
          console.log('Received message', messageObj);
          receiveMessage(messageObj);
          return;
        } else if (event.data.startsWith('DELETE_MESSAGE:')) {
          const message = event.data.replace('DELETE_MESSAGE:', '');
          deleteMessage(message);
          return;
        } else if (event.data.startsWith('UPDATE_MESSAGE')) {
          const message = event.data.replace('UPDATE_MESSAGE:', '');
          const messageObj = JSON.parse(message);
          editMessage(messageObj);
          return;
        }
      };
    }
    console.log('WebSocket connection established ', url);
  };

  socket.onerror = (error) => {
    console.error('WebSocket error', error);
  }

  socket.onclose = () => {
    console.log('WebSocket connection closed');
  };
  return () => {
    socket.close();
  };
}
