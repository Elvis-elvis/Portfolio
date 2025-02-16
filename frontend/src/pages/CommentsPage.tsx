import Comment from '../components/Comment';
import Footer from '../components/Footer';
import '../components/Welcome.css';


export default function CommentsPage(): JSX.Element {
  return (
    <div>
        <Comment/>
      <Footer/>
    </div>
  );
}

