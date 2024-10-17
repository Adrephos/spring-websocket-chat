"use client"

import { ChangeEvent, FormEvent, useState } from "react";
import { useAuth } from "@/context/AuthContext";
import Link from "next/link";

export default function Login() {
  const { isLoadingAuth, authenticate, checkIfLoggedIn } = useAuth();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const isButtonDisabled = isLoadingAuth || username === "" || password === "";

  const runAuth = async (e: FormEvent) => {
    e.preventDefault();
    authenticate("login", {
      username: username,
      password: password
    }).then(() => {
      checkIfLoggedIn();
      window.location.href = "/";
    });
  }

  const handleUsername = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setUsername(value.trim());
  }
  const handlePassword = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  }

  return (
    <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-white">
          Sign in to your account
        </h2>
      </div>

      <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form action="#" method="POST" className="space-y-6">
          <div>
            <label htmlFor="username" className="block text-sm font-medium leading-6 text-white">
              Username
            </label>
            <div className="mt-2">
              <input
                id="username"
                name="username"
                type="username"
                placeholder="username"
                required
                autoComplete="username"
                value={username}
                onChange={handleUsername}
                className="block w-full rounded-md border-0 py-1.5 px-2 text-black shadow-sm ring-1 ring-inset ring-white placeholder:text-gray-500 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div>
            <div className="flex items-center justify-between">
              <label htmlFor="password" className="block text-sm font-medium leading-6 text-white">
                Password
              </label>
            </div>
            <div className="mt-2">
              <input
                id="password"
                name="password"
                type="password"
                placeholder="password"
                required
                autoComplete="current-password"
                value={password}
                onChange={handlePassword}
                className="block w-full rounded-md border-0 py-1.5 px-2 text-black shadow-sm ring-1 ring-inset ring-white placeholder:text-gray-500 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div>
            <button
              type="submit"
              disabled={isButtonDisabled}
              onClick={runAuth}
              className="flex w-full justify-center rounded-md bg-indigo-600 disabled:bg-gray-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              Sign in
            </button>
          </div>
        </form>

        <p className="mt-10 text-center text-sm text-white">
          Not a member?{' '}
          <Link href="/signup" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
            Signup here
          </Link>
        </p>
      </div>
    </div>
  );
}
