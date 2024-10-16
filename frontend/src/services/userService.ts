import { AuthResponse } from "../types/user"
import { Api } from "./apiService"

export type LoginCredentials = {
  username: string,
  password: string
}

export type SignupCredentials = {
  username: string,
  email: string,
  password: string,
}

const login = async (credentials: LoginCredentials): Promise<AuthResponse> => {
  return Api.post("/auth/login", credentials);
}
const signup = async (credentials: SignupCredentials): Promise<AuthResponse> => {
  return Api.post("/auth/register", credentials);
}

export const userService = {
  login,
  signup
}
