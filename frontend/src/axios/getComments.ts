import axiosInstance from '../Shared/Api/axiosInstance';
import {commentResponseModel} from "@/model/commentResponseModel";

export const getComments = async (): Promise<commentResponseModel[]> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    const response = await axiosInstance.get<commentResponseModel[]>(`${backendUrl}/api/comments`);
    return response.data;
};

    export const getApprovedComments = async (): Promise<commentResponseModel[]> => {
        const backendUrl = process.env.REACT_APP_BACKEND_URL;
        const response = await axiosInstance.get<commentResponseModel[]>(`${backendUrl}/api/comments/approved`);
        return response.data;
    };

