/** @type {import('next').NextConfig} */
  const nextConfig = {
    output: 'standalone',
    env: {
      WS_URL: 'ws://localhost:8080/ws-chat',
      API_URL: 'http://localhost:8080',
    },
  };

export default nextConfig;
