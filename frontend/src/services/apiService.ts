import axios, { AxiosError, AxiosInstance, AxiosResponse } from "axios";

const url = process.env.API_URL

export const Api: AxiosInstance = axios.create({ baseURL: url });

Api.interceptors.request.use(async (config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.set("Authorization", `Bearer ${token}`);
  }

  return config;
});

Api.interceptors.response.use(
  async (res: AxiosResponse) => res.data,
  async (err: AxiosError) => Promise.reject(err)
);
