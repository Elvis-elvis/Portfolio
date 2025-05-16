import React, { useState, useEffect } from 'react';
import { commentResponseModel } from '../model/commentResponseModel';
import { getPendingComments, approveComment, deleteComment } from '../axios/commentActions';
import Navbar from './Navbar';
import './AdminDashboard.css';
import { useTranslation } from 'react-i18next';

const AdminDashboard: React.FC = (): JSX.Element => {
  const [comments, setComments] = useState<commentResponseModel[]>([]);
  const [isElvis, setIsElvis] = useState<boolean>(false);
  const { t } = useTranslation();  // Using the translation hook

  useEffect(() => {
    const fetchUserRoles = async () => {
      const accessToken = localStorage.getItem('access_token');
      if (!accessToken) return;

      try {
        const base64Url = accessToken.split('.')[1];
        const decodedPayload = JSON.parse(atob(base64Url));
        const roles = decodedPayload['https://portfolio/roles'] || [];
        setIsElvis(roles.includes('Elvis'));
      } catch (err) {
        console.error('Error decoding roles:', err);
      }
    };

    const fetchComments = async () => {
      try {
        const response = await getPendingComments();
        setComments(response);
      } catch (error) {
        console.error('Error fetching comments:', error);
      }
    };

    fetchUserRoles();
    fetchComments();
  }, []);

  const handleApprove = async (id: string) => {
    try {
      await approveComment(id);
      setComments(comments.map(c => (c.id === id ? { ...c, approved: true } : c)));
      alert(t('commentApproved'));
      window.location.reload(); // optional: for live refresh
    } catch (error) {
      console.error('Error approving comment:', error);
    }
  };

  const handleDelete = async (id: string) => {
    try {
      await deleteComment(id);
      setComments(comments.filter(c => c.id !== id));
      alert(t('commentDeleted'));
    } catch (error) {
      console.error('Error deleting comment:', error);
    }
  };

  if (!isElvis) return <p>{t('accessDenied')}</p>;

  return (
    <div className="adminDashboard">
      <Navbar />
      <div className="commentsText">
        <h1>{t('adminDashboard')}</h1>
      </div>
      <div className="commentsContainer">
        {comments.length === 0 ? (
          <p>{t('noPendingComments')}</p>
        ) : (
          comments.map((comment) => (
            <div key={comment.id} className="comment">
              <p>
                <strong>{comment.author}:</strong> {comment.content}
              </p>
              <p className="commentDate">{t('submittedOn')}: {new Date(comment.dateSubmitted).toLocaleString()}</p>
              <button className="approveBtn" onClick={() => handleApprove(comment.id)}>✔</button>
              <button className="deleteBtn" onClick={() => handleDelete(comment.id)}>❌</button>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default AdminDashboard;
