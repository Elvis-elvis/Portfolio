import axiosInstance from '../Shared/Api/axiosInstance';

export const approveComment = async (commentId: string): Promise<void> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    await axiosInstance.put(`${backendUrl}/api/comments/${commentId}/approve`);
};



