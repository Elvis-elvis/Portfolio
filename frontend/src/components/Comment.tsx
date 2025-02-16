import React, { useState, useEffect } from 'react';
import Navbar from './Navbar';
import './Comment.css';
import {commentResponseModel} from "@/model/commentResponseModel";
import { addComment } from '../axios/addComment';
import { getApprovedComments } from '../axios/getComments';
import { deleteComment } from '../axios/commentActions';
import { useTranslation } from 'react-i18next';
import {commentRequestModel} from "@/model/commentRequestModel";
const Comment: React.FC = (): JSX.Element => {
  const [comments, setComments] = useState<commentResponseModel[]>([]);
    const [author, setAuthor] = useState<string>('');
    const [content, setContent] = useState<string>('');
    const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
    const [errorContent, setErrorContent] = useState<string>('');
    const [showCommentForm, setShowCommentForm] = useState<boolean>(false);
    const [isElvis, setIsElvis] = useState<boolean>(false);
    const { t } = useTranslation();  // Using the translation hook


    useEffect(() => {
        const fetchUserRoles = () => {
            const accessToken = localStorage.getItem('access_token');
            if (!accessToken) return;

            try {
                const base64Url = accessToken.split('.')[1];
                const decodedPayload = JSON.parse(atob(base64Url));
                const roles = decodedPayload['https://portfolio/roles'] || [];
                setIsElvis(roles.includes('Haitham'));
            } catch (err) {
                console.error('Error decoding roles:', err);
            }
        };

        const fetchCommentsData = async () => {
            try {
                const response = await getApprovedComments();
                setComments(response);
            } catch (error) {
                console.error('Error fetching comments:', error);
            }
        };

        fetchUserRoles();
        fetchCommentsData();
    }, []);

    const handleCommentSubmit = async () => {
        if (!author || !content) {
            setErrorContent(t('pleaseFillInFields'));
            return;
        }

        const newComment: commentRequestModel = { author, content };

        try {
            setIsSubmitting(true);
            await addComment(newComment);
            setAuthor('');
            setContent('');
            setErrorContent('');
            alert(t('commentSubmitted'));
        } catch (error) {
            console.error('Error submitting comment:', error);
            setErrorContent(t('errorSubmittingComment'));
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleDelete = async (commentId: string) => {
        try {
            await deleteComment(commentId);
            setComments(comments.filter((c) => c.commentId !== commentId));
            alert(t('commentDeleted'));
        } catch (error) {
            console.error('Error deleting comment:', error);
        }
    };


    return (
        <div className="commentsPage">
            <Navbar />
            <div className="commentsText">
                <h1>{t('comments')}</h1>
            </div>
            <button className="comment-button" onClick={() => setShowCommentForm(!showCommentForm)}>
                {showCommentForm ? t('close') : t('leaveAComment')}
            </button>

            {showCommentForm && (
                <div className="comment-form">
                    <input
                        type="text"
                        value={author}
                        onChange={(e) => setAuthor(e.target.value)}
                        placeholder={t('yourName')}
                    />
                    <textarea
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        placeholder={t('yourComment')}
                    />
                    {errorContent && <p className="error-content">{errorContent}</p>}
                    <button className="comment-button" onClick={handleCommentSubmit} disabled={isSubmitting}>
                        {isSubmitting ? t('submitting') : t('leaveComment')}
                    </button>
                </div>
            )}

            <div className="section-divider"></div>  {/* Divider here */}


            <div className="commentsContainer">
                {comments.length > 0 ? (
                    comments.map((comment) => (
                        <div key={comment.commentId} className="comment">
                            <p>
                                <strong>{comment.author}:</strong> {comment.content}
                            </p>
                            <p className="commentDate">
                                {t('submittedOn')}: {new Date(comment.dateSubmitted).toLocaleString()}
                            </p>

                            {isElvis && (
                                <>
                                    <button className="deleteBtn" onClick={() => handleDelete(comment.commentId)}>
                                        ❌
                                    </button>
                                </>
                            )}
                        </div>
                    ))
                ) : (
                    <p>{t('noCommentsAvailable')}</p>
                )}
            </div>
        </div>
    );
};

export default Comment;
