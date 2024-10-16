"use client"

import { AuthProvider } from "@/context/AuthContext";
import { ReactNode } from "react";

export default function AuthLayout({
  children
}: Readonly<{ children: ReactNode }>) {
  return (
    <AuthProvider>
      {children}
    </AuthProvider>
  );
}
