import React, { useState } from 'react';
import './Footer.css';
import { commentRequestModel } from '@/model/commentRequestModel';
import { addComment } from '../axios/addComment';
import { useTranslation } from 'react-i18next';

const Footer: React.FC = () => {
  const { t } = useTranslation();
  const [author, setAuthor] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const [errorContent, setErrorContent] = useState<string>('');
  const [showCommentForm, setShowCommentForm] = useState<boolean>(false);

  const handleCommentSubmit = async () => {
    if (!author || !content) {
      setErrorContent(t('footer.leaveComment.error'));
      return;
    }

    const newComment: commentRequestModel = { author, content };

    try {
      setIsSubmitting(true);
      await addComment(newComment);
      setAuthor('');
      setContent('');
      setErrorContent('');
      alert(t('footer.leaveComment.submitSuccess'));
    } catch (error) {
      console.error('Error submitting comment:', error);
      setErrorContent(t('footer.leaveComment.submitError'));
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
      <footer className="footer">
        <div className="footer-content">
          <div className="footer-section">
            <h3>{t('footer.about.title')}</h3>
            <p>{t('footer.about.description')}</p>
          </div>
          <div className="footer-section">
            <h3>{t('footer.contact.title')}</h3>
            <p>{t('footer.contact.description')}</p>

            <button className="comment-button" onClick={() => setShowCommentForm(!showCommentForm)}>
              {showCommentForm ? t('footer.leaveComment.close') : t('footer.leaveComment.button')}
            </button>

            {showCommentForm && (
                <div className="comment-form">
                  <input
                      type="text"
                      value={author}
                      onChange={(e) => setAuthor(e.target.value)}
                      placeholder={t('footer.leaveComment.namePlaceholder')}
                  />
                  <textarea
                      value={content}
                      onChange={(e) => setContent(e.target.value)}
                      placeholder={t('footer.leaveComment.commentPlaceholder')}
                  />
                  {errorContent && <p className="error-content">{errorContent}</p>}
                  <button className="close-button" onClick={() => setShowCommentForm(false)}>
                    {t('footer.leaveComment.close')}
                  </button>
                  <button className="comment-button" onClick={handleCommentSubmit} disabled={isSubmitting}>
                    {isSubmitting ? t('footer.leaveComment.submitting') : t('footer.leaveComment.button')}
                  </button>
                </div>
            )}

            <br /><br />
            <p>
              <a href="mailto:pj2025@gmail.com" className="email-link">📧 {t('footer.contact.email')}</a>
            </p>
          </div>

          <div className="footer-section">
            <h3>{t('footer.follow.title')}</h3>
            <p>{t('footer.follow.description')}</p>
            <div className="socialLinks">
              <a href="https://www.linkedin.com/in/elvis-ruberwa-a2b7b4328/" target="_blank" rel="noopener noreferrer">
                <i className="fab fa-linkedin"></i>
              </a>
              <a href="https://github.com/Elvis-elvis" target="_blank" rel="noopener noreferrer">
                <i className="fab fa-github"></i>
              </a>
            </div>
          </div>

          <div className="footer-section">
            <h3>{t('footer.resume.title')}</h3>
            <a href="ruberwa_resume.pdf" download="ELVIS_RUBERWA_CV_EN.pdf" className="download-button">
              {t('footer.resume.english')}
            </a>
            &nbsp; &nbsp;
            <a href="CVRuberwaEnFrancais.pdf" download="ELVIS_RUBERWA_CV_FR.pdf" className="download-button">
              {t('footer.resume.french')}
            </a>
          </div>
        </div>
        <div className="footer-bottom">
          <p>&copy; 2025 Elvis Ruberwa. {t('footer.rightsReserved')}</p>
        </div>
      </footer>
  );
};

export default Footer;
