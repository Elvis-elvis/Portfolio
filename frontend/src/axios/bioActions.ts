import axiosInstance from '../Shared/Api/axiosInstance';
import { BioModel } from '../model/BioModel';

export const getBio = async (): Promise<BioModel> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    const response = await axiosInstance.get(`${backendUrl}/api/bio`);
    return response.data;
};

export const updateBio = async (content: string): Promise<void> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    await axiosInstance.put(`${backendUrl}/api/bio`, { content });
};
