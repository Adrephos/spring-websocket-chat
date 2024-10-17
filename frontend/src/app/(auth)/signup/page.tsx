"use client"

import { ChangeEvent, FormEvent, useState } from "react";
import { useAuth } from "@/context/AuthContext";
import Link from "next/link";

export default function Signup() {
  const { isLoadingAuth, authenticate, checkIfLoggedIn } = useAuth();
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [emailError, setEmailError] = useState("");

  const runAuth = async (e: FormEvent) => {
    e.preventDefault();
    authenticate("signup", {
      username: username,
      password: password,
      email: email,
    }).then(() => {
      checkIfLoggedIn();
      window.location.href = "/";
    });
  }

  const validateEmail = (email: string): boolean => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const handleUsername = (e: ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value.trim());
  }

  const handlePassword = (e: ChangeEvent<HTMLInputElement>) => {
    var value = e.target.value;
    if (value !== repeatPassword) {
      setPasswordError("Passwords don't match");
    } else {
      setPasswordError("");
    }
    setPassword(value);
  }

  const handleRepeatPassword = (e: ChangeEvent<HTMLInputElement>) => {
    var value = e.target.value;
    if (value !== password) {
      setPasswordError("Passwords don't match");
    } else {
      setPasswordError("");
    }
    setRepeatPassword(value);
  }

  const handleEmail = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    const isValid = validateEmail(value);
    setEmail(value);
    if (!isValid) {
      setEmailError("Invalid email");
    } else {
      setEmailError("");
    }
  }

  const isButtonDisabled =
    isLoadingAuth ||
    username === "" ||
    password === "" ||
    email === "" ||
    repeatPassword === "" ||
    passwordError !== "" ||
    emailError !== "";

  return (
    <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-white">
          Create a new account
        </h2>
      </div>

      {
        passwordError &&
        <div className="p-2 mt-5 bg-red-200 sm:mx-auto sm:w-full sm:max-w-sm rounded">
          <p className="text-red-500">{passwordError}</p>
        </div>
      }

      {
        emailError &&
        <div className="p-2 mt-5 bg-red-200 sm:mx-auto sm:w-full sm:max-w-sm rounded">
          <p className="text-red-500">{emailError}</p>
        </div>
      }

      <div className="mt-5 sm:mx-auto sm:w-full sm:max-w-sm">
        <form action="#" method="POST" className="space-y-6">
          <div>
            <label htmlFor="username" className="block text-sm font-medium leading-6 text-white">
              Username
            </label>
            <div className="mt-2">
              <input
                id="username"
                name="username"
                type="text"
                placeholder="username"
                required
                value={username}
                onChange={handleUsername}
                className="block w-full rounded-md border-0 py-1.5 px-2 text-black shadow-sm ring-1 ring-inset ring-white placeholder:text-gray-500 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div>
            <label htmlFor="email" className="block text-sm font-medium leading-6 text-white">
              Email address
            </label>
            <div className="mt-2">
              <input
                id="email"
                name="email"
                type="email"
                placeholder="email"
                required
                autoComplete="email"
                value={email}
                onChange={handleEmail}
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
            <div className="flex items-center justify-between">
              <label htmlFor="repeat-password" className="block text-sm font-medium leading-6 text-white">
                Repeat Password
              </label>
            </div>
            <div className="mt-2">
              <input
                id="repeat-password"
                name="repeat-password"
                type="password"
                placeholder="password"
                required
                value={repeatPassword}
                onChange={handleRepeatPassword}
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
              Sign up
            </button>
          </div>
        </form>

        <p className="mt-10 text-center text-sm text-white">
          Already have an account?{' '}
          <Link href="/login" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
            Login here
          </Link>
        </p>
      </div>
    </div>
  );
}
