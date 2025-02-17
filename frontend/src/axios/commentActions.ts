import axiosInstance from '../Shared/Api/axiosInstance';
import { commentResponseModel } from '../model/commentResponseModel';

// Fetch approved comments
export const getApprovedComments = async (): Promise<commentResponseModel[]> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    const response = await axiosInstance.get(`${backendUrl}/api/comments/approved`);
    return response.data;
};

// Fetch pending comments
export const getPendingComments = async (): Promise<commentResponseModel[]> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    const response = await axiosInstance.get<commentResponseModel[]>(`${backendUrl}/api/comments/unapproved`);
    return response.data;
};

// Approve comment
export const approveComment = async (commentId: string): Promise<void> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    await axiosInstance.put(`${backendUrl}/api/comments/${commentId}/approve`);
};

// Delete comment
export const deleteComment = async (commentId: string): Promise<void> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    await axiosInstance.delete(`${backendUrl}/api/comments/${commentId}`);
};
