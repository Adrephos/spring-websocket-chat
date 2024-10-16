export type AuthResponse = {
  token: string,
  user: User
};

export type User = {
  username: string
  email: string
}