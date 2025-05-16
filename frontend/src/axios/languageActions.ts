import axiosInstance from "../Shared/Api/axiosInstance";
import { LanguageModel } from "../model/LanguageModel";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

export const getAllLanguages = async (): Promise<LanguageModel[]> => {
  const res = await axiosInstance.get(`${backendUrl}/api/languages`);
  return res.data;
};

export const addLanguage = async (language: LanguageModel): Promise<void> => {
  await axiosInstance.post(`${backendUrl}/api/languages`, language);
};

export const updateLanguage = async (id: string, language: LanguageModel): Promise<void> => {
  await axiosInstance.put(`${backendUrl}/api/languages/${id}`, language);
};

export const deleteLanguage = async (id: string): Promise<void> => {
  await axiosInstance.delete(`${backendUrl}/api/languages/${id}`);
};
