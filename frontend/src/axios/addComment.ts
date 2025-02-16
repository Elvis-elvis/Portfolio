import axiosInstance from '../Shared/Api/axiosInstance';
import {commentRequestModel} from "@/model/commentRequestModel";

export const addComment = async (comment: commentRequestModel): Promise<void> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    await axiosInstance.post(`${backendUrl}/api/comments`, comment);
};
