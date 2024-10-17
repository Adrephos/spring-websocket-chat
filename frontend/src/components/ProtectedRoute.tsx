"use client"

import { useAuth } from "@/context/AuthContext";
import { redirect } from "next/navigation";
import { ReactNode, useEffect, useState } from "react";

export default function ProtectedRoute({ children }: { children: ReactNode }) {
  const [isClient, setIsClient] = useState(false);
  const { isLoggedIn, isLoadingAuth } = useAuth();

  useEffect(() => setIsClient(true), [])

  console.log("ProtectedRoute", isLoggedIn);

  if (isLoggedIn) {
    return children;
  }

  if (isLoadingAuth) {
    return <div>Loading...</div>
  }

  if (isClient) {
    if (
      !isLoggedIn &&
      !(
        window.location.pathname == "/login" ||
        window.location.pathname == "/signup"
      )
    ) {
      redirect("/login");
    }
  }
}
