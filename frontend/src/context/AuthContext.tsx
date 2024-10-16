import { LoginCredentials, SignupCredentials, userService } from "@/services/userService";
import { User } from "@/types/user";
import { PropsWithChildren, createContext, useContext, useEffect, useState } from "react";

type authData = LoginCredentials | SignupCredentials;

interface AuthContextProps {
  isLoggedIn: boolean;
  isLoadingAuth: boolean;
  authenticate: (
    authMode: "login" | "signup",
    data: authData
  ) => Promise<void>;
  logout: VoidFunction;
  user: User | null;
  token: string
}

const AuthContext = createContext({} as AuthContextProps);

export const useAuth = () => {
  return useContext(AuthContext);
}

export const AuthProvider = ({ children }: PropsWithChildren) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isLoadingAuth, setIsLoadingAuth] = useState(false);
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState("");

  useEffect(() => {
    if (user && token) {
      setIsLoggedIn(true);
    } else {
      setIsLoggedIn(false);
    }
  }, [user, token])


  useEffect(() => {
    async function checkIfLoggedIn() {
      const storedToken = localStorage.getItem("token");
      const storedUser = localStorage.getItem("user")

      if (storedToken && storedUser) {
        setUser(JSON.parse(storedUser));
        setToken(storedToken);
        setIsLoggedIn(true);
      } else {
        setIsLoggedIn(false);
      }
    }

    checkIfLoggedIn();
  }, [])

  const authenticate = async (
    authMode: "login" | "signup",
    data: authData
  ): Promise<void> => {
    try {
      setIsLoadingAuth(true);
      let res;
      if (authMode == "login") {
        res = await userService.login(data as LoginCredentials);
      } else {
        res = await userService.signup(data as SignupCredentials);
      }

      if (res) {
        const { token, user } = res;

        localStorage.setItem("token", token);
        localStorage.setItem("user", JSON.stringify(user));
        setUser(user);
        setIsLoggedIn(true);
      }
    } catch (e) {
      setIsLoggedIn(false);
    } finally {
      setIsLoadingAuth(false);
    }
  }

  const logout = async () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setIsLoggedIn(false);
  }

  return (
    <AuthContext.Provider
      value={{
        isLoadingAuth,
        isLoggedIn,
        authenticate,
        logout,
        user,
        token
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
